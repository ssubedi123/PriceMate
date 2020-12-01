package com.example.pricemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.pricemate.pricecompare.product;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView itemSearchView;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;


    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Item> itemList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        itemSearchView = (SearchView) findViewById(R.id.searchBar);
        itemSearchView.setOnQueryTextListener(this);

        mList = findViewById(R.id.main_list);

        itemList = new ArrayList<>();
        adapter = new CustomAdapter(getApplicationContext(), itemList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.ebay.com/buy/browse/v1/item_summary/search?q=iphone 12&limit=3";

        JSONObject apiResponse = new JSONObject();

        // Request a string response from the provided URL.
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("key", "value");
        } catch (Exception e) {
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, parameters,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.i("onResponse", response.toString());

                try {
                    for (int i = 0; i < response.getJSONArray("itemSummaries").length(); i++) {

                        JSONObject jsonObject = response.getJSONArray("itemSummaries").getJSONObject(i);

                        Item item = new Item();
                        item.setTitle(jsonObject.getString("title"));
                        item.setCondition(jsonObject.getString("condition"));
                        item.setPrice(jsonObject.getJSONObject("price").getString("value"));

                        itemList.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + "v^1.1#i^1#f^0#p^1#I^3#r^0#t^H4sIAAAAAAAAAOVYb2wURRTvttdToJWoqEiIHIsQ+XN7s7f3d+0dXP/RQ9o7uLbBIuLs7mxZurd77MzRHiGx1lBoDEZSokZMKAY0ol/4QDVNiEYS/CDB+EFEFIMSgiGEYDQiSIy7d0e5VtIivWATL5tc5s17b37v996bmV3QbZ+yqLeh92oldV/pQDfoLqUodhqYYi9f/EBZ6azyElCgQA10P9lt6yn7uQrDpJriVyOc0jWMHF1JVcN8Vhii04bG6xArmNdgEmGeiHwi0riSdzOATxk60UVdpR3R2hDtljx+rwf5JYE1H0EwpdpNn816iPZBzssJHCuL/qCPDUrmPMZpFNUwgRox7YEbOFm3E7DNwM2zgPdwjNcD2mhHKzKwomumCgPocBYun7U1CrCODRVijAxiOqHD0Uh9IhaJ1tY1NVe5CnyF8zwkCCRpPHJUo0vI0QrVNBp7GZzV5hNpUUQY065wboWRTvnITTB3AT9LtQxlH+cXA5CTvSxkfUWhsl43kpCMjcOSKJJTzqrySCMKyYzHqMmGsBGJJD9qMl1Eax3W36o0VBVZQUaIrquOPBuJx+lwNdI2wqSiOeOGIqJGSJzx1bVOUfYHJCkgIqfPD2QOAJRfKOctT/OolWp0TVIs0rCjSSfVyESNRnMDCrgxlWJazIjIxEJUqOe5ySHnb7OSmstimmzQrLyipEmEIzscPwPD1oQYipAmaNjD6IksRSEaplKKRI+ezNZivny6cIjeQEiKd7k6OzuZTo7RjXaXGwDWtaZxZULcgJKQNnWtXs/pK+MbOJVsKCIyLbHCk0zKxNJl1qoJQGunwx53IBAI5nkfCSs8WvoPQUHMrpEdUawOkQQouwXJHUBeBN1SoBgdEs4XqcvCgQSYcSah0YFISoVmYYpmnaWTyFAknvPKbi4gI6fkC8pOT1CWnYJX8jlZGSGAkCCIwcD/qVHutNQTSDQQKUqtF63OV0TAlo0NNU3VrUhcXp+o97SrddUNm+VYpyezpR0JWgOS0544aEi0hO60G24fvKinUFxXFTFTBAasXi8iC5whxaFBMgmkqqZgQoFiK9DJlWTLHpsOYEphrMZmRD3p0qG5o1ui9VnEE4o5kkpFk8k0gYKKosXZzf+jnfy24SnmXWdSxWTmL5dIRcpdUphsNhm8WWQMhPW0Yd7PmJh1ZjfrHUgzd0Bi6KqKjFZ2wom+1/m1en0cPv7lYXF3sRfvpjKZaltUFbOE1k+2yO5JRhU4yU5j1gd8/kDA5+EmFFdNNqfNmcl2DjXomCBprNBsy+/yWu0a+ZIfLsn+2B7qMOihDpVSFHCB+ew8MNde1mIrq5iFFYIYBcoMVto1893VQEwHyqSgYpTaqVQLvDi/4LPCwDowc/jDwpQydlrBVwYw+9ZMOTv9sUo3YM0HuFng4drAvFuzNvZR2wzb/dPJpo8P4gufX7mx/6EbzxzYfvUcqBxWoqjyElsPVUJ/cpR5vP6kJxj7DC3/68KDKxdn7FvWL7FVnZzznK3/0MHKtdeXHj1SMfOlHRcrdu/7evWx964urBvw9kVee2tfxdO7t62pevf41ugyP197wLXii/4/rtT9fvhIy6Jr9odf/+H9mUO7Ts9eNCRde+T5vdsWTDXOviC1HYMfndk+9OZc+RcU/fPUT6cO3lj7YXyQxByvDiZn9O/sjbNKxeVV31/cu07Zef74Ox0tgwdq7N9dl2e//UrjwrOXjrk+ndX/YskTcqWw3X35198W7Pjxm96lh6d+oO96ijo/Z+umuqE9R0/01uDQ+diaoUu1X+3vnz64RHrjy6lnzlHfnug6PdjSJ+5fZszv21MR1l9uy6Xvb8dK+CPwEQAA");
                return headers;
            }
        };
        queue.add(request);


        //Initializes with no items, will be using cardview/listview to populate a scrollable page    }

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
        //transforming the view so that I can display a bunch of cards
        product SearchResult = new product(0,query, 0.0,0.0);
        //instead of Toast we now populate the listview

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //could be used to string match from top 100 most searched items.
        return false;
    }

    public void addToCart (View view){
        Toast.makeText(this, "Adding to Cart" , Toast.LENGTH_LONG).show();

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
                .setTheme(R.style.LoginTheme)
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

