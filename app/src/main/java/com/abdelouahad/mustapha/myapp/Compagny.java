package com.abdelouahad.mustapha.myapp;

import android.widget.ImageView;

public class Compagny {
    private String price;
    private String classe;
    private String start_date;
    private String return_date;
    private ImageView imageView;
    private String ImageBase64;

    public Compagny(String price, String classe, String start_date, String return_date, ImageView imageView, String imageBase64) {
        this.price = price;
        this.classe = classe;
        this.start_date = start_date;
        this.return_date = return_date;
        this.imageView = imageView;
        ImageBase64 = imageBase64;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getImageBase64() {
        return ImageBase64;
    }

    public void setImageBase64(String imageBase64) {
        ImageBase64 = imageBase64;
    }
}
