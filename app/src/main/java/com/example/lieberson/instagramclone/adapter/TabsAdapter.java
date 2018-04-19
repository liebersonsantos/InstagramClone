package com.example.lieberson.instagramclone.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

import com.example.lieberson.instagramclone.R;
import com.example.lieberson.instagramclone.view.fragments.HomeFragment;
import com.example.lieberson.instagramclone.view.fragments.UsuariosFragment;

import java.util.HashMap;

public class TabsAdapter extends FragmentStatePagerAdapter {

    private Context context;
    // private String[] abas = new String[]{"HOME", "USUÁRIOS"};
    private int[] icones = new int[]{R.drawable.ic_action_home, R.drawable.ic_people};
    private int tamanhoIcone;
    private HashMap<Integer, Fragment> fragmentosUtilizados;



    public TabsAdapter(FragmentManager fm, Context c) { //objeto responsavel por gerenciar os fragmentos que serao retornados pelo metodo getItem
        super(fm);
        this.context = c;
        this.fragmentosUtilizados = new HashMap<>();

        double escala = this.context.getResources().getDisplayMetrics().density;
        tamanhoIcone = (int) (36 * escala);


    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){

            case 0 :
                fragment = new HomeFragment();
                fragmentosUtilizados.put(position, fragment);
                break;
            case 1 :
                fragment = new UsuariosFragment();
                fragmentosUtilizados.put(position, fragment);
                break;

        }

        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragmentosUtilizados.remove(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //Recuperando o icone de acordo com a posição
        Drawable drawable = ContextCompat.getDrawable(this.context, icones[position]);
        drawable.setBounds(0, 0, tamanhoIcone, tamanhoIcone);  //esquerda, parte de cima, largura, altura

        //Permite colocar a imagem dentro de um texto
        ImageSpan imageSpan = new ImageSpan(drawable);

        //Classe utilizada para retornar CharSequence
        SpannableString spannableString = new SpannableString( " " );
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

    public Fragment getFragment(Integer indice){

        return fragmentosUtilizados.get(indice);
    }

    @Override
    public int getCount() { //retorna as abas

        return icones.length;
    }
}
