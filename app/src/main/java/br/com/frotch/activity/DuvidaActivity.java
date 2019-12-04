package br.com.frotch.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;

import br.com.frotch.R;


public class DuvidaActivity extends AppCompatActivity {


    private Button btn_tel;
    private Button btn_web;
    private Button btn_email;
    private Button btn_facebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duvida);

        btn_tel = (Button) findViewById(R.id.btntelefone);

        btn_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionar_tel();
            }
        });

        btn_web = (Button) findViewById(R.id.btnweb);

        btn_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionar_web();
            }
        });

        btn_email = (Button) findViewById(R.id.btnemail);

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionar_email();
            }
        });

        btn_facebook = (Button) findViewById(R.id.btnfacebook);

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionar_facebook();
            }
        });
    }

    private void redirecionar_tel() {

        String telefone="00000000";
        Uri uri = Uri.parse("tel:"+telefone);
        Intent intent = new Intent(Intent.ACTION_DIAL,uri);

        startActivity(intent);
    }

    private void redirecionar_web() {
        String url = "https://www.frotch.com.br";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    private void redirecionar_email() {
        final Intent intent = ShareCompat.IntentBuilder.from(this)
                .setType("message/rfc822")
                .addEmailTo("frotch@frotch.com")
                .setSubject("Dúvidas")
                .setText("Olá...")
                .setChooserTitle("E-mail")
                .createChooserIntent();

        startActivity(intent);
    }
    private void redirecionar_facebook() {
        String url = "https://www.facebook.com/Frotch";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}