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
import com.example.lieberson.instagramclone.contract.Contract;
import com.example.lieberson.instagramclone.presenter.Presenter;
import com.example.lieberson.instagramclone.util.ParseErros;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements Contract.View{

    private EditText loginUsuario;
    private EditText senhaUsuario;
    private Button botaoLoginUsuario;
    private TextView novoCadastro;

    private Contract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new Presenter(this);

        initViews();

        abrirLoginCadastrar();

        verificarUsuarioLogado();

        botaoLoginUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = loginUsuario.getText().toString();
                String senha = senhaUsuario.getText().toString();

                verificarLogin(usuario, senha);

            }
        });



    }

    private void verificarLogin(String usuario, String senha) {

        ParseUser.logInInBackground(usuario, senha, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (e == null){ //sucesso ao fazer login

                    Toast.makeText(LoginActivity.this, "Sucesso ao fazer Login", Toast.LENGTH_SHORT).show();
                    abrirAreaPrincipal();

                }else { //erro ao fazer login

                    ParseErros parseErros = new ParseErros();
                    String erro = parseErros.getErro(e.getCode());
                    Toast.makeText(LoginActivity.this, erro, Toast.LENGTH_SHORT).show();
                   // Toast.makeText(LoginActivity.this, erro + e.getCode(), Toast.LENGTH_SHORT).show(); ---> dessa forma eu pego o codigo do erro para poder emitir a msg de erro

                }

            }
        });
    }

    private void verificarUsuarioLogado(){

        if (ParseUser.getCurrentUser() != null){ //Se usuario for diferente de nulo(usuario logado)
                //enviar usuario para a tela principal
                abrirAreaPrincipal();
        }
    }

    private void abrirAreaPrincipal(){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirLoginCadastrar() {

        novoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void initViews() {

        loginUsuario = findViewById(R.id.editNomeId);
        senhaUsuario = findViewById(R.id.editSenhaId);
        botaoLoginUsuario =  findViewById(R.id.botaoLogar);
        novoCadastro = findViewById(R.id.text_tela_cadastro_id);
    }


}



