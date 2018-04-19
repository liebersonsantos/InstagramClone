package com.example.lieberson.instagramclone.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lieberson.instagramclone.R;
import com.example.lieberson.instagramclone.adapter.HomeAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedUsuariosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String userName;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> postagens;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_usuarios);

        //Recupera os dados da intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("username");

        //Configurando a toolbar
        toolbar = findViewById(R.id.toolbar_feed_usuario);
        toolbar.setTitle(userName);
        toolbar.setTitleTextColor(R.color.preto);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);

        //Configurando ListView e Adapter
        postagens = new ArrayList<>();
        listView = findViewById(R.id.list_feed_usuarios);
        adapter = new HomeAdapter(getApplicationContext(), postagens);
        listView.setAdapter(adapter);

        //Recuperando as postagens
        getPostagens();

    }

    private void getPostagens() {

        //Recupera as imagens das postagens
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Imagem");
        query.whereEqualTo("objectIdUser", userName);
        query.orderByDescending("createAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null){ //sucesso

                    //verifica se existe postagens
                    if (objects.size() > 0){

                        postagens.clear();
                        for (ParseObject parseObject : objects){
                            postagens.add(parseObject);
                        }

                        adapter.notifyDataSetChanged();
                    }

                }else { //erro
                    Toast.makeText(getApplicationContext(), "Erro ao recuperar o feed", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

}
