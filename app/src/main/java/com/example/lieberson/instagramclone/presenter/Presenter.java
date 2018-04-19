package com.example.lieberson.instagramclone.presenter;

import android.content.Intent;
import android.view.View;

import com.example.lieberson.instagramclone.contract.Contract;
import com.example.lieberson.instagramclone.view.CadastroActivity;
import com.example.lieberson.instagramclone.view.LoginActivity;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Contract.Model model;

    public Presenter(Contract.View view, Contract.Model model) {
        this.view = view;
        this.model = model;
    }

    public Presenter(LoginActivity loginActivity) {

    }
}

