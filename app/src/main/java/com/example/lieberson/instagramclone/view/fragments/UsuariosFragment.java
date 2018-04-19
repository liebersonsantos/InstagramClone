package com.example.lieberson.instagramclone.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lieberson.instagramclone.R;
import com.example.lieberson.instagramclone.adapter.UsuariosAdapter;
import com.example.lieberson.instagramclone.view.FeedUsuariosActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<ParseUser> adapter;
    private ArrayList<ParseUser> usuarios;
    private ParseQuery<ParseUser> query;

    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        //Montando ListView e adapter
        usuarios = new ArrayList<>();
        listView = view.findViewById(R.id.list_usuarios);
        adapter = new UsuariosAdapter(getActivity(), usuarios);
        listView.setAdapter(adapter);

        //Recupera usuarios
        getUsuarios();

        //Evento de click no item da lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Recupera dados a serem passados
                ParseUser parseUser = usuarios.get(position);

                //enviar dados para feed usuario
                Intent intent = new Intent(getActivity(), FeedUsuariosActivity.class);
                intent.putExtra("username", parseUser.getUsername());
                startActivity(intent);

            }
        });

        return view;
    }

    private void getUsuarios() {

        //Recupera lista de usuarios
        query = ParseUser.getQuery();
        query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        query.orderByAscending("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

            if (e == null){ //sucesso

                if (objects.size() > 0){ //verifica se tem usuario
                    usuarios.clear();
                    for (ParseUser parseUser : objects){
                        usuarios.add(parseUser);
                    }
                    adapter.notifyDataSetChanged();
                }

            }else { //erro

                e.printStackTrace();
            }

            }
        });

    }

}
