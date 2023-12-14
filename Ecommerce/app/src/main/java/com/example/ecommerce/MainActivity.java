package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText textInputLayout;
    TextInputEditText passwordEditText;

    Dao dao;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textInputLayout = findViewById(R.id.ProductName);
    passwordEditText = findViewById(R.id.passwordEditText);
    user = Room.databaseBuilder(this, User.class, "app-database").build();
    dao = user.userDao();
    }

    public void logIn(View view) {
        final String username = textInputLayout.getText().toString();
        final String password = passwordEditText.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {
            if(username.equals("admin") && password.equals("admin")){
                Intent intent = new Intent(getApplicationContext(), AuthenticatedScreen.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("id","");
                intent.putExtra("isAdmin",1);
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", "admin");
                editor.putInt("userID", 123);
                editor.putInt("isAdmin", 1);
                editor.apply();
                startActivity(intent);
            }else{
                new Thread(new Runnable() {
                @Override
                public void run() {
                    UserEntity user = dao.getUserByUsernameAndPassword(username, password);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (user != null) {
                                Intent intent = new Intent(getApplicationContext(), AuthenticatedScreen.class);
                                intent.putExtra("username",user.userName);
                                intent.putExtra("password",password);
                                intent.putExtra("id",user.id);
                                intent.putExtra("isAdmin",0);

                                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("username", user.userName);
                                editor.putInt("userID", user.id);
                                editor.putInt("isAdmin", 0);
                                editor.apply();
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "User Authenticated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }).start();}
        }
    }

    public void signUp(View view) {
    Intent intent = new Intent(this, SignUp.class);
    startActivity(intent);
    }
}