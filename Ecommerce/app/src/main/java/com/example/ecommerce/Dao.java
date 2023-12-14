package com.example.ecommerce;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Query("SELECT * FROM UserEntity")
    List<UserEntity> getAllUsers();

    @Insert
    void insertUser(UserEntity user);

    @Query("SELECT * FROM UserEntity WHERE userName = :username AND password = :password")
    UserEntity getUserByUsernameAndPassword(String username, String password);


    @Query("SELECT * FROM UserEntity WHERE userName = :username")
    UserEntity getUserByUsername(String username);


    @Insert
    void insertProduct(Product product);

    @Query("SELECT * FROM Product")
    List<Product> getAllProducts();

    @Query("SELECT * FROM Cart")
    List<Cart> getAllCarts();

    @Insert
    void insertCart(Cart cart);

    @Update
    void updateProduct(Product product);

}

