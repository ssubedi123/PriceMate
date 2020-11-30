package com.example.pricemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pricemate.pricecompare.product;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    Button buy, buy1;
    SearchView itemSearchView;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        itemSearchView = (SearchView) findViewById(R.id.searchBar);
        itemSearchView.setOnQueryTextListener(this);
        buy = findViewById(R.id.buy);
        buy1 = findViewById(R.id.buy1);


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onclick of an item being bought, it needs to be appended to current user's firestore document
                //firebaseFirestore.collection("Users").document(user_id).set(data, {merge:true}) will probably look something like this
                //require data but can use spoofed item ids to test.
                Toast.makeText(MainActivity.this,"Item Bought",Toast.LENGTH_SHORT).show();
            }
        });
        buy1 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Item Bought",Toast.LENGTH_SHORT).show();
            }
        });
    }


    //may have to use this instead https://www.geeksforgeeks.org/android-searchview-with-example/
    @Override
    protected void onStart() {
        super.onStart();
        //there is a login loop because it happens whenever it starts but never leaves, learn more
        //about Android life cycle to break this.

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            createSignInIntent();
        }

    }
    @Override
    public boolean onQueryTextSubmit(String query){
        //searchForItem(query);
        //Can include the API call functions here
        product SearchResult = new product(0,query, 0.0,0.0);
        Toast.makeText(this, "Searching for " + SearchResult.getVendor() , Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //could be used to string match from top 100 most searched items.
        return false;
    }

    public void addToCart (View view){
        //link this method to "add to cart" button on list view card
        //This method should make a firebase call to currentUser to add to collection
    }


    //could just change to public static and make a class that has all the FireAuth methods in one place...
    private void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers, Facebook and Twitter and special additional requirements
        // in gradle so don't forget to add
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.AnonymousBuilder().build());
        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.mates)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
    }


    public void shoppingCart(View view){
        startActivity(new Intent(getApplicationContext(),ShoppingCart.class));
    }
    //need to create methods that push from "buy" to the user's shopping cart on firestore,
    // identifier for products?
    //method needs to changed to product list List<product>
    //also might need a helper method to correct strings so that exact matches are not required

    public void signOut(View view){

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        createSignInIntent();
                    }
                });
    }

}

