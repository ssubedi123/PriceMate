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
    private double rating = 0;
    private String condition;

    public product(String condition, String itemTitle, double price, double rating){
        this.condition = condition;
        this.itemTitle = itemTitle;
        this.price = Math.floor(price * 100);
        this.rating = rating;
    }
    public String getVendor(){
        return this.vendor;
    }
    public String getItemTitle(){
        return this.itemTitle;
    }
    public int getIdNumber(){
        return this.idNumber;
    }
    public double getPrice(){
        return this.price;
    }
    public String getCondition(){
        return this.condition;
    }
}
