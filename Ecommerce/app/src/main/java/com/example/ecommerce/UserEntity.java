package com.example.ecommerce;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String userName;
    public String password;

    public boolean isAdmin;
}
