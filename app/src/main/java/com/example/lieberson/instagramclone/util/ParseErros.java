package com.example.lieberson.instagramclone.util;

import java.util.HashMap;

public class ParseErros {

    private HashMap<Integer, String> erros;

    public ParseErros() {
        this.erros = new HashMap<>();
        this.erros.put(101, "Usuário inválido. Tente outro usuário.");
        this.erros.put(125, "E-mail inválido!");
        this.erros.put(200, "Por favor, preencha o Usuário.");
        this.erros.put(201, "Por favor ,preencha o campo senha!");
        this.erros.put(202, "Usuário Existente. Por favor verifique o nome do usuário!");
        this.erros.put(204, "Por favor Preencha o campo e-mail.");

    }

    public String getErro(int codErro){

        return this.erros.get(codErro);
    }
}
