package com.intactaengenharia.intacta.service.model;

import java.util.ArrayList;
import java.util.List;

public class Obra {

    private String obra, user, data, key, local;
    private int valor, entry;
    private Long progress;
    private List<String> endservices;

    public Obra(String obra, String user, String data, String key, int valor, Long progress, int entry, String local) {
        this.obra = obra;
        this.user = user;
        this.data = data;
        this.key = key;
        this.valor = valor;
        this.progress = progress;
        this.entry = entry;
        this.local = local;
    }


    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public Long getProgress() {
        return progress;
    }

    public void setProgress(Long progress) {
        this.progress = progress;
    }


    public Obra() {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public List<String> getEndservices() {
        return endservices;
    }

    public void setEndservices(List<String> endservices) {
        this.endservices = endservices;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

}
