package com.example.capstone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.capstone.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //TODO: Check if user is connected to the internet

       new Handler().postDelayed(() -> {

           FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
           if (user != null) {
               // User is signed in
               Intent intent = new Intent(SplashScreen.this, MainActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
               startActivity(intent);
               finish();
           } else {
               // User is signed out
               Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
               startActivity(intent);
               finish();
           }
       }, 3000);
    }
}
