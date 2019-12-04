package br.com.frotch.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.frotch.R;
import br.com.frotch.adapter.VeiculosAdapter;
import br.com.frotch.domain.Veiculo;
import br.com.frotch.service.ApiSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VeiculosActivity extends AppCompatActivity {

    private static final String TAG = VeiculosActivity.class.getSimpleName();

    public static final String EXTRA_USUARIO = "VeiculosActivity.usuario";

    private RecyclerView rvVeiculos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculos);

        rvVeiculos = findViewById(R.id.rvVeiculos);
        rvVeiculos.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvVeiculos.setLayoutManager(layoutManager);


        carregarVeiculos();
    }

    private void carregarVeiculos() {
        ApiSingleton.get().getApiService().listarVeiculos().enqueue(new Callback<List<Veiculo>>() {
            @Override
            public void onResponse(Call<List<Veiculo>> call, Response<List<Veiculo>> response) {
                if (response.isSuccessful()) {
                    final List<Veiculo> veiculos = response.body();

                    VeiculosAdapter adapter = new VeiculosAdapter(veiculos) {
                        @Override
                        public void onVeiculoClick(Veiculo veiculo) {
                            Log.d(TAG, String.format("Clicou no veiculo %s", veiculo.getId()));
                        }
                    };
                    rvVeiculos.setAdapter(adapter);
                } else {
                    // tratar erro //
                }
            }

            @Override
            public void onFailure(Call<List<Veiculo>> call, Throwable t) {
                // tratar erro //
            }
        });
    }
}

