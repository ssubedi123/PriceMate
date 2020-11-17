package com.example.pricemate.pricecompare;

//will most likely be using browse api from ebay

//https://developer.ebay.com/DevZone/building-blocks/eBB_eBayValueItems.pdf
public class product {
    private String itemTitle;
    private String imgURL;
    private String itemLink;
    private int idNumber;
    private String vendor;
    private double price;
    private double rating;
    public product(int idNumber, String vendor, double price, double rating){
        this.idNumber = idNumber;
        this.vendor = vendor;
        this.price = price;
        this.rating = rating;
    }
    public String getVendor(){
        return this.vendor;
    }

}
