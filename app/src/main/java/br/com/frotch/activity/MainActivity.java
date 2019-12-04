package br.com.frotch.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.TextView;

import org.parceler.Parcels;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.frotch.R;
import br.com.frotch.domain.Usuario;

public class MainActivity extends AppCompatActivity {

    private Button btnListar;
    private Button btnCalendario;
    private Button btnInfo;
    private Button btnDuvidas;
    private TextView tvnome;
    private TextView tvdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        btnListar = (Button) findViewById(R.id.btn_listarVeiculos);
        btnCalendario = (Button) findViewById(R.id.btn_calendario);
        btnInfo = (Button) findViewById(R.id.btn_info);
        btnDuvidas = (Button) findViewById(R.id.btn_duvidas);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionarlistar();
            }
        });
        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionarcalendario();
            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionarinfo();
            }
        });
        btnDuvidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionarduvidas();
            }
        });

        Usuario usuario = Parcels.unwrap(getIntent().getParcelableExtra("usuario"));
        String nome =String.format(usuario.getNome());

        tvnome = (TextView)findViewById(R.id.tv_nome);
        tvnome.setText(nome);

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        tvdata = (TextView)findViewById(R.id.tv_data);
        tvdata.setText(dataFormatada);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.btn_sair) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sair");
            builder.setMessage("Deseja realmente sair do seu login?");

            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sairLogin();
                }
            });
            builder.setNegativeButton("Cancelar", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void redirecionarlistar() {
        Intent home = new Intent(this, VeiculosActivity.class);
        startActivity(home);
    }

    private void redirecionarcalendario() {
        Intent calendario = new Intent(this, CalendarioActivity.class);
        startActivity(calendario);

    }

    private void redirecionarinfo() {
        Intent info = new Intent(this, InfoActivity.class);
        startActivity(info);
    }

    private void redirecionarduvidas() {
        Intent duvidas = new Intent(this, DuvidaActivity.class);
        startActivity(duvidas);
    }

    private void sairLogin(){
        Intent sair = new Intent(this, LoginActivity.class);
        sair.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(sair);
    }
}

