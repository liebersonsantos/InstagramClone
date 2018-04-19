package com.example.lieberson.instagramclone.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lieberson.instagramclone.R;
import com.example.lieberson.instagramclone.adapter.HomeAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ListView listView;
    private ArrayList<ParseObject> postagens;
    private ArrayAdapter<ParseObject> adapter;
    private ParseQuery<ParseObject> query;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        //Montando ListView e Adapter
        postagens = new ArrayList<>();
        listView =  view.findViewById(R.id.list_postagem_home);
        adapter = new HomeAdapter(getActivity(), postagens);
        listView.setAdapter(adapter);

        //Recuperando as postagens
        getPostagens();

        return view;

    }

    private void getPostagens() {

        //Recuperando as imagens
        query = ParseQuery.getQuery("Imagem");
        query.whereEqualTo("objectIdUser", ParseUser.getCurrentUser().getObjectId());
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null){ //sucesso

                    if (objects.size() > 0){

                        postagens.clear();

                        for (ParseObject parseObject : objects){

                            postagens.add(parseObject);
                        }

                        adapter.notifyDataSetChanged();
                    }


                }else {

                    e.printStackTrace();
                }

            }
        });

    }

    public void atualizaPostagens(){

        getPostagens();
    }

}
