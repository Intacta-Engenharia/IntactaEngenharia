package com.intactaengenharia.intacta.service.model;

public class Contact {
    String nome,telefone;

    public Contact(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public Contact() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
