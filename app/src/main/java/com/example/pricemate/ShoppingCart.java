package com.example.pricemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {
    //Display User cart information
    //Populate information from firebase
    private static final int RC_SIGN_IN = 123;
    private String shoppingCartDisplayString;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        TextView userDisplayText = (TextView) findViewById(R.id.userDisplayName);

        userDisplayText.setText(getCurrentUserUsername());

    }
    //createSignInIntent will be moved to a db class that will have these for them
    //Also have to implement display name
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
    public void backToMainActivity(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
    //A method that calls collection user to find document corresponding with current user id,
    private List<String> getcurrentUserShoppingCart(){
        //current user shopping cart should be appended whenever user clicks "buy" although buy should also be changed to add
        List<String> currentUserShoppingCart = null;
//        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//        db.collection("users").document(currentFirebaseUser.getUid()).set(currentUserShoppingCart);
        return currentUserShoppingCart;
    }
    private String getCurrentUserUsername(){
        String currentUserUsername = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        shoppingCartDisplayString = currentUserUsername + "'s Shopping Cart";
        if(currentUserUsername.isEmpty()){
            currentUserUsername = "Guest";
        }else{

        }

        shoppingCartDisplayString = currentUserUsername + "'s Shopping Cart";
        return shoppingCartDisplayString;

    }

}