package com.example.ecommerce;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
     int productId;
     int quantity;
     int price;
     String description;
}
