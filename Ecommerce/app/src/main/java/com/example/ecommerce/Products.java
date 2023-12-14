package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Products extends AppCompatActivity implements BottomSheet.UpdateView {
    public static boolean isEdit = false;

    public static Cart cart = new Cart();



    public static int count = 0;

    public static int pos = 0;
    RecyclerView rc;
    ProductAdapter adapter;

    List<Product> products;

    Dao dao;

    User user;

    TextView adProduct;

    int isAdmin;

    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        isAdmin = getIntent().getIntExtra("isAdmin",0);
        name = getIntent().getStringExtra("name");
        rc = findViewById(R.id.rc);
        adProduct = findViewById(R.id.ad);
        user = Room.databaseBuilder(this, User.class, "app-database").build();
        dao = user.userDao();
        Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
        products = dao.getAllProducts();
        }
        });
        thread.start();
        try {
        thread.join();
        } catch (InterruptedException e) {
        throw new RuntimeException(e);
        }

        adapter = new ProductAdapter(this, products, dao,new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        update();
        }
        }, isAdmin, adProduct, name);

        rc.setLayoutManager(new GridLayoutManager(this,2));
        rc.setAdapter(adapter);
        if(isAdmin==0){
        adProduct.setVisibility(View.INVISIBLE);
        }
        adProduct.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        if(isAdmin==1){
        BottomSheet customBottomSheet = new BottomSheet(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        isEdit=false;
        update();
        }
        });
        customBottomSheet.show(getSupportFragmentManager(), "BottomSheet");
        }else{
        cart.date = LocalDate.now().toString();

            Map<Integer, Integer> elementCount = new HashMap<>();
            List<Integer> uniqueElements = new ArrayList<>();

            // Count the occurrences of each element and store unique elements
            for (int element : cart.productId) {
                elementCount.put(element, elementCount.getOrDefault(element, 0) + 1);
                if (!uniqueElements.contains(element)) {
                    uniqueElements.add(element);
                }
            }

            List<Integer> uniqueList = new ArrayList<>();
            List<Integer> repetitionList = new ArrayList<>();

            for (int element : uniqueElements) {
                uniqueList.add(element);
                repetitionList.add(elementCount.get(element));
            }

        cart.productId=uniqueList;
        cart.quantity=repetitionList;
        cart.name = name;
          //  cart.quantity = repetitionArray;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    dao.insertCart(cart);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                        Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_SHORT).show();
                        count=0;
                        cart = new Cart();
                        finish();
                        }
                    });
                }
            }).start();
        }



        }
        });
    }



    @Override
    public void update() {
        Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
        adapter.productList = dao.getAllProducts();
        }
        });
        thread.start();
        try {
        thread.join();
        } catch (InterruptedException e) {
        throw new RuntimeException(e);
        }
        adapter.notifyDataSetChanged();
    }


}