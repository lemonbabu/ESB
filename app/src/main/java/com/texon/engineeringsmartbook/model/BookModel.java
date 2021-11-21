package com.texon.engineeringsmartbook.model;

public class BookModel {
    private String title,  price;
    public BookModel() {
    }
    public BookModel(String title, String price) {
        this.title = title;
        this.price = price;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String name) {
        this.title = name;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
}