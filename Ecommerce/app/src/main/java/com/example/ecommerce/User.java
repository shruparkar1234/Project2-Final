package com.example.ecommerce;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {UserEntity.class, Product.class, Cart.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class User extends RoomDatabase {
    public abstract Dao userDao();
}