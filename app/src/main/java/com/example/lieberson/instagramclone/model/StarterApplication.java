package com.example.lieberson.instagramclone.model;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Habilitando armazenamento local
        Parse.enableLocalDatastore(this);


        //Código de Configuração  do App
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("Tne6614qDQVSml5kCgGvDloKVsaxtDpXaH3Sxe7C")  // ---> ID do App no Back4app
                .clientKey("W1nPncltmWedOhLZt5GTPkgnm1MIXMpYvHyeVFG1")
                .server("https://parseapi.back4app.com/")
                .build()
        );


//      // Teste de configuração do App
//      ParseObject pontuacao = new ParseObject("Pontuacao");
//      pontuacao.put("pontos", 100);
//      pontuacao.saveInBackground(new SaveCallback() {
//          public void done(ParseException e) {
//              if (e == null) {
//                  Log.i("TesteExecucao", "Salvo com sucesso!!!");
//              } else {
//                  Log.i("TesteExecucao", "Falha ao salvar os dados!!!");
//              }
//          }
//      });

     //   ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        defaultACL.setPublicReadAccess(true);
        // ParseACL.setDefaultACL(defaultACL, true);

    }
}
