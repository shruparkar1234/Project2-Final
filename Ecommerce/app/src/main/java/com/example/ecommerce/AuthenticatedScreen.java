package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AuthenticatedScreen extends AppCompatActivity {
    String name;
    String id;
    int isAdmin;

    TextView welcome;

    TextView logout;
    CardView myChart;
    CardView allProducts;

    CardView addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_authenticated_screen);
       welcome = findViewById(R.id.welcome);
       myChart = findViewById(R.id.mycart);
       logout = findViewById(R.id.logout);
       allProducts = findViewById(R.id.allProducts);
       addProduct = findViewById(R.id.addProducts);
       Intent intent = getIntent();

        name =  intent.getStringExtra("username");
        id =  intent.getStringExtra("id");
       isAdmin =  intent.getIntExtra("isAdmin",0);


        welcome.setText("Welcome"+" "+name);
       if(isAdmin==1){
       myChart.setVisibility(View.GONE);
       }else{
       addProduct.setVisibility(View.INVISIBLE);
       myChart.setVisibility(View.GONE);
       }
       listeners();
    }

    public void listeners(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.remove("userID");
                editor.remove("isAdmin");
                editor.clear();
                editor.apply();
                Toast.makeText(AuthenticatedScreen.this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
        myChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        allProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Products.class);
            intent.putExtra("isAdmin",isAdmin);
            intent.putExtra("name",name);
            startActivity(intent);
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OrdersActivity.class);
                i.putExtra("isAdmin",isAdmin);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
}




