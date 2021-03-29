package com.intactaengenharia.intacta.service.model;

public class User {
    public User(String name, String cnpj, String password) {
        this.name = name;
        this.cnpj = cnpj;
        this.password = password;
    }

    private String name,cnpj,password;

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
