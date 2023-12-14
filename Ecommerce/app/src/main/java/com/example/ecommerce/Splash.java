package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");
        int userID = sharedPreferences.getInt("userID", -1);
        int isAdminn = sharedPreferences.getInt("isAdmin", 0);

        if(!username.isEmpty() && userID != -1){
            Intent intent = new Intent(getApplicationContext(), AuthenticatedScreen.class);
            intent.putExtra("username",username);
            intent.putExtra("id",userID);
            intent.putExtra("isAdmin",isAdminn);
            startActivity(intent);
            finish();

        }else{
           Intent i = new Intent(this,MainActivity.class);
           startActivity(i);
           finish();
        }
    }
}