package com.example.lieberson.instagramclone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.lieberson.instagramclone.R;
import com.parse.ParseObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends ArrayAdapter<ParseObject>{

    private Context context;
    private ArrayList<ParseObject> postagens;


    public HomeAdapter(@NonNull Context c, @NonNull ArrayList<ParseObject> objects) {
        super(c, 0, objects);
        this.context = c;
        this.postagens = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        //Verifica se nao existe objeto view criado, pois, a view utilizada é armazenada no cache do android e fica variavel convert
        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta a view a partir do xml
            view = inflater.inflate(R.layout.lista_postagem, parent, false);

        }

        //verifca se existe postagem
        if (postagens.size() > 0){

            //Recupera componente da tela
            ImageView imagemPostagem = view.findViewById(R.id.image_lista_postagem);

            ParseObject parseObject = postagens.get(position);
//            parseObject.getParseFile("imagem");

           Picasso.get().load(parseObject
                   .getParseFile("imagem")
                   .getUrl())
                   .fit()
                   .into(imagemPostagem);


        }

        return view;
    }
}
