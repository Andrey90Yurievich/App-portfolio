package com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.Domain;

import java.io.Serializable;

public class PopularDomain implements Serializable {
    //Нам нужен класс, который представляет данные одного элемента RecyclerView.
    //класс PopularDomain содержит поля для хранения заголовка, описания, ссылки на ресурс изображения
    // , счета, количества в корзине, цены
    //разметка для вывода одного объекта PopularDomain
    private String title; //заголовок
    private String description; //описание
    private String picUrl; //ссылка на картинку
    private int review; //обзор - число
    private double score; //счет -формат чисел с плавающей запятой, за исключением того, что он имеет 11-разрядную экспоненту
    private int numberInCart; //количество в корзине - в формате типа числа целого
    private double price; //цена -формат чисел с плавающей запятой, за исключением того, что он имеет 11-разрядную экспоненту

    public PopularDomain(String title, String description, String picUrl, int review, double score, double price) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.review = review;
        this.score = score;
        this.price = price;
    }

    public double getPrice() { //get - получит цену
        return price;
    }

    public void setPrice(double price) { //указать(ввести) цену
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNumberinCart() {
        return numberInCart;
    }

    public void setNumberinCart(int numberinCart) {
        this.numberInCart = numberinCart;
    }
}
