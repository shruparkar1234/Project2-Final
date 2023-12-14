package com.example.ecommerce;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    int Totalprice;
    String date;
    List<Integer> productId = new ArrayList<>();
    List<Integer> quantity = new ArrayList<>();
}
