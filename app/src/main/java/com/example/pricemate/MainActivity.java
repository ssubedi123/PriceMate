package com.example.pricemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button buy, buy1;
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        buy = findViewById(R.id.buy);
        buy1 = findViewById(R.id.buy2);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                .setLogo(R.drawable.pricemate)
                .build();

        startActivityForResult(intent, RC_SIGN_IN);
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
    public void shoppingCart(View view){
        startActivity(new Intent(getApplicationContext(),ShoppingCart.class));
    }
}