package com.example.pricemate.pricecompare;

//will most likely be using browse api from ebay
//https://developer.ebay.com/api-docs/buy/browse/static/overview.html

public class product {
    private int idNumber;
    private String vendor;
    private double price;
    private double rating;
    product(int idNumber, String vendor, double price, double rating){
        this.idNumber = idNumber;
        this.vendor = vendor;
        this.price = price;
        this.rating = rating;
    }

}
