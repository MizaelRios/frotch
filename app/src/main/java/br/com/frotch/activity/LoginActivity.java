package br.com.frotch.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.material.snackbar.Snackbar;
import org.parceler.Parcels;
import br.com.frotch.R;
import br.com.frotch.domain.Usuario;
import br.com.frotch.service.ApiService;
import br.com.frotch.service.ApiSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HTTP;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText etUsername;
    private EditText etSenha;
    private Button btEntrar;
    private Button btLinkSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etSenha = findViewById(R.id.etSenha);
        btEntrar = findViewById(R.id.btEntrar);
        btLinkSenha = findViewById(R.id.btn_LinkSenha);
        

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario credenciais = new Usuario();
                if (isValidEmail(etUsername.getText().toString())) {
                    credenciais.setEmail(etUsername.getText().toString());
                } else {
                    credenciais.setCpf(etUsername.getText().toString());
                }
                credenciais.setSenha(etSenha.getText().toString());

                final ApiService api = ApiSingleton.get().getApiService();
                api.logar(credenciais).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful()) {
                            final Usuario usuario = response.body();
                            String msg = String.format("Bem vindo %s!", usuario.getNome());
                            mostrarMensagem(msg);
                            redirecionarParaHome(usuario);
                        } else {
                            mostrarMensagem(ApiSingleton.get().getMensagemErro(response.errorBody()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        mostrarMensagem(t.getMessage());
                    }
                });
            }
        });

        btLinkSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionar_email();
            }
        });
    }

    private void redirecionar_email() {
        final Intent intent = ShareCompat.IntentBuilder.from(this)
                .setType("message/rfc822")
                .addEmailTo("frotch@frotch.com")
                .setSubject("Redefinir a senha")
                .setText("Olá,...")
                .setChooserTitle("E-mail")
                .createChooserIntent();

        startActivity(intent);
    }

    private void redirecionarParaHome(Usuario usuario) {
        Intent home = new Intent(this, MainActivity.class);
        home.putExtra(VeiculosActivity.EXTRA_USUARIO, Parcels.wrap(usuario));
        home.putExtra("usuario",Parcels.wrap(usuario));
        startActivity(home);
    }

    private void mostrarMensagem(String msg) {
        Snackbar.make(btEntrar, msg, Snackbar.LENGTH_LONG).show();
    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
