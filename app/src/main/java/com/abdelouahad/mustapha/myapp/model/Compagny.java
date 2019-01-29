package com.abdelouahad.mustapha.myapp.model;

import android.widget.ImageView;

public class Compagny {
    private String price;
    private String destination;
    private String start_date;
    private String return_date;
    private ImageView imageView;
    private String ImageBase64;
    private String name;

    public Compagny(String name,String price, String destination, String start_date, String return_date, ImageView imageView, String imageBase64) {
        this.name = name;
        this.price = price;
        this.destination = destination;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
