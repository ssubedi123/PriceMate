package com.example.pricemate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ShoppingCart extends AppCompatActivity {
    //Display User cart information
    //Populate information from firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
    }
}