package com.intactaengenharia.intacta.Beans;

import java.util.ArrayList;

public class Obra {

   private String obra,user,password,contracturl,data,art,serviceorder,cronogram,key,artexecution;
   private int valor,entry;
   private Long progress;
   private ArrayList<String> endservices;

    public Obra(String obra, String user, String password, String contracturl, String data, String art, String serviceorder, String cronogram, String key, String artexecution, int valor, Long progress, int entry) {
        this.obra = obra;
        this.user = user;
        this.password = password;
        this.contracturl = contracturl;
        this.data = data;
        this.art = art;
        this.serviceorder = serviceorder;
        this.cronogram = cronogram;
        this.key = key;
        this.artexecution = artexecution;
        this.valor = valor;
        this.progress = progress;
        this.entry = entry;
    }


    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getServiceorder() {
        return serviceorder;
    }

    public void setServiceorder(String serviceorder) {
        this.serviceorder = serviceorder;
    }





    public int getEntry() {
        return entry;
    }

    public void setEntry(int entry) {
        this.entry = entry;
    }



    public String getCronogram() {
        return cronogram;
    }

    public void setCronogram(String cronogram) {
        this.cronogram = cronogram;
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

    public String getContracturl() {
        return contracturl;
    }

    public void setContracturl(String contracturl) {
        this.contracturl = contracturl;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getArtexecution() {
        return artexecution;
    }

    public void setArtexecution(String artexecution) {
        this.artexecution = artexecution;
    }

    public ArrayList<String> getEndservices() {
        return endservices;
    }

    public void setEndservices(ArrayList<String> endservices) {
        this.endservices = endservices;
    }
}
