package com.example.datalekage;

public class UploadPDF {

    private String name,url,date;


    public UploadPDF() {
    }

    public UploadPDF(String name, String url, String date) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
