package com.example.pricemate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pricemate.pricecompare.apiCalls;
import com.ebay.api.client.auth.oauth2.OAuthService;
import com.ebay.api.client.auth.oauth2.model.ApiConfiguration;
import com.ebay.api.client.auth.oauth2.model.ApiEnvironment;
import com.ebay.api.client.auth.oauth2.model.ApiSessionConfiguration;
import com.example.pricemate.pricecompare.product;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    Button buy, buy1;
    SearchView itemSearchView;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private RequestQueue queue;
    apiCalls apiCallForMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        itemSearchView = findViewById(R.id.searchBar);
        itemSearchView.setOnQueryTextListener(this);
        buy = findViewById(R.id.buy);
        buy1 = findViewById(R.id.buy2);
        queue = Volley.newRequestQueue(this);
        apiCallForMain = new apiCalls(MainActivity.this);

        //This is where we initialize everything for the ebay OAuth
        //ApiSessionConfiguration.Companion.initialize()
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

        //Can include the API call functions here
        //StringRequest stringRequest = apiCallForMain.searchNameStringRequestForEbay(query,"Benjamin-PriceMat-SBX-47cf12021-a56ab0f6","Benjamin_Kim-Benjamin-PriceM-nmmgne","https://api.ebay.com/oauth/api_scope","https://auth.sandbox.ebay.com/oauth2/authorize?client_id=");
        StringRequest weatherStringRequest = apiCallForMain.searchNameStringRequestForOpenWeather(query, getResources().getString(R.string.open_weather_api_key));
        //product SearchResult = new product(0,query, 0.0,0.0);

        //queue does not currently work with ebay
        //queue.add(stringRequest);
        //weather example
        queue.add(weatherStringRequest);
        Toast.makeText(this, "Searching for " + query + "'s temperature" , Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //could be used to string match from top 100 most searched items.
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    public void goToEbay(View View){
        startActivity(new Intent(getApplicationContext(),OauthRedirectActivity.class));
    }


}
