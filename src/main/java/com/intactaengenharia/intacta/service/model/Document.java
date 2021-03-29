package com.intactaengenharia.intacta.service.model;

public class Document {
    String key,title,url;

    public Document(String key,String title, String url) {
        this.title = title;
        this.url = url;
    }
    public Document(){}
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
