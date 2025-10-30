package com.intactaengenharia.intacta.service.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Diary{
    private String data,key,fiscal;
    private boolean operavel;
    private ArrayList<String> workers, services, photos;

    public Date getDia() {
        return dia;
    }

    public void setDia(String dia) {
        Date date= null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dia);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.dia = date;
    }

    private Date dia;

    public boolean isOperavel() {
        return operavel;
    }

    public void setOperavel(boolean operavel) {
        this.operavel = operavel;
    }

    public Diary() {
    }

    public Diary(String data, String key, String fiscal) {
        this.data = data;
        this.key = key;
        this.fiscal = fiscal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFiscal() {
        return fiscal;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }

    public void setWorkers(ArrayList<String> workers) {
        this.workers = workers;
    }

    public ArrayList<String> getWorkers() {
        return workers;
    }

    public ArrayList<String> getServices() {
        return services;
    }

    public void setServices(ArrayList<String> services) {
        this.services = services;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }


}
