package com.example.lieberson.instagramclone.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lieberson.instagramclone.R;
import com.example.lieberson.instagramclone.util.ParseErros;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class CadastroActivity extends AppCompatActivity {

    private EditText usuarioCadastro;
    private EditText emailCadastro;
    private EditText senhaCadastro;
    private Button botaoCadastrarUsuario;
    private TextView telaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initViews();
        abrirLoginUsuario();

        botaoCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cadastrarUsuario();

            }
        });


    }

    private void cadastrarUsuario() {

        //Criando objeto usuario
        ParseUser user = new ParseUser();
        user.setUsername(usuarioCadastro.getText().toString());
        user.setEmail(emailCadastro.getText().toString());
        user.setPassword(senhaCadastro.getText().toString());

        //Salvando os dados do usuario
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null){ // Sucesso ao salvar os dados

                    Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    abrirParaLogar();

                }else { // Erro ao salvar os dados

                    ParseErros parseErros = new ParseErros();
                    String erro = parseErros.getErro(e.getCode());
                    Toast.makeText(CadastroActivity.this, erro, Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void abrirParaLogar(){

        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirLoginUsuario() {

        telaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirParaLogar();

            }
        });
    }

    private void initViews() {

        usuarioCadastro = findViewById(R.id.text_usuario_cadastro_id);
        emailCadastro = findViewById(R.id.text_email_cadastro_id);
        senhaCadastro = findViewById(R.id.text_senha_cadastro_id);
        botaoCadastrarUsuario = findViewById(R.id.bota_cadastro_id);
        telaLogin = findViewById(R.id.txt_cadastro_login_id);
    }


}
