package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import java.util.List;

public class OrdersActivity extends AppCompatActivity {


    Dao dao;

    User user;

    List<Cart> c;
    RecyclerView rc;

    OrdersAdapter ac;

    int isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        rc = findViewById(R.id.rcc);
        isAdmin = getIntent().getIntExtra("isAdmin",0);
        user = Room.databaseBuilder(this, User.class, "app-database").build();
        dao = user.userDao();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                c = dao.getAllCarts();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ac = new OrdersAdapter(getApplicationContext(), c, isAdmin);
        rc.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rc.setAdapter(ac);
    }
}