package com.example.pricemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pricemate.pricecompare.CustomAdapter;
import com.example.pricemate.pricecompare.product;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView itemSearchView;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<product> productList= new ArrayList<>();
    private CustomAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mList;
    private DividerItemDecoration dividerItemDecoration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mList = findViewById(R.id.main_list);

        itemSearchView = (SearchView) findViewById(R.id.searchBar);
        itemSearchView.setOnQueryTextListener(this);
        adapter = new CustomAdapter(getApplicationContext(), productList, mAuth,db);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(), linearLayoutManager.getOrientation());

        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        mList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            createSignInIntent();
        }

    }
    @Override
    public boolean onQueryTextSubmit(String query){
        //searchForItem(query);
        //Can include the API call functions here
        //transforming the view so that I can display a bunch of cards
        product SearchResult = new product("Used", query,Math.random(),Math.random());
        productList.add(SearchResult);
        //instead of Toast we now populate the listview

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //could be used to string match from top 100 most searched items.
        return false;
    }

    public void addToCart (View view){
        //add User to Firebase before adding item to cart.
        Map<String, String> data = new HashMap<>();
        data.put("email", mAuth.getCurrentUser().getEmail());
        db.collection("users").document(mAuth.getCurrentUser().getUid()).set(data,SetOptions.merge());
        //add Item to cart that belongs to User.
        //db.collection("users").document(mAuth.getCurrentUser().getUid()).collection("cart").document(product.getItemTitle());
        Toast.makeText(this, "Adding to Cart" , Toast.LENGTH_LONG).show();

        //This method should make a firebase call to currentUser to add to collection
    }



    private void createSignInIntent() {

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

