package com.example.ecommerce;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends AppCompatActivity {
    TextInputEditText userName;
    TextInputEditText password;
    TextInputEditText confirmPassword;

    Dao dao;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    userName = findViewById(R.id.ProductName);
    password = findViewById(R.id.passwordEditText);
    confirmPassword = findViewById(R.id.cpasswordEditText);
    user = Room.databaseBuilder(this, User.class, "app-database").build();
    dao = user.userDao();


    }

    public void signUp(View view) {
        String name = userName.getText().toString();
        String pass = password.getText().toString();
        String confirmPass = confirmPassword.getText().toString();

        final boolean[] b = {true};

        if (!name.isEmpty() && !pass.isEmpty() && confirmPass.equals(pass)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                   UserEntity e = dao.getUserByUsername(name);
                   if(e!=null){
                   b[0]=false;
                   }
                    if (b[0]) {

                        UserEntity user = new UserEntity();
                        user.userName = name;
                        user.password = pass;
                        user.isAdmin = false;


                        dao.insertUser(user);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Signed up successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                       // Toast.makeText(getApplicationContext(),"Signed up successfully", Toast.LENGTH_SHORT).show();
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        });
                    }else{
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Username has already been taken", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }
            }).start();
        } else if (!name.isEmpty() && !pass.isEmpty() && !confirmPass.equals(pass)) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fields required", Toast.LENGTH_SHORT).show();
        }
    }

}