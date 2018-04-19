package com.example.lieberson.instagramclone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lieberson.instagramclone.R;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UsuariosAdapter extends ArrayAdapter<ParseUser>{

    private Context context;
    private ArrayList<ParseUser> usuarios;


    public UsuariosAdapter(@NonNull Context c, @NonNull ArrayList<ParseUser> objects) {
        super(c, 0, objects);
        this.context = c;
        this.usuarios = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View view = convertView;

        //Verifica se nao existe objeto view criado, pois, a view utilizada é armazenada no cache do android e fica variavel convert
        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //monta a view a partir do xml
            view = inflater.inflate(R.layout.lista_usuarios, parent, false);
        }

        //Recuperando elementos para exibiçao
        TextView username = view.findViewById(R.id.text_username);

        //Configurando o textView para exibir os usuarios
        ParseUser parseUser = usuarios.get(position);
        username.setText(parseUser.getUsername());

        return view;
    }
}
