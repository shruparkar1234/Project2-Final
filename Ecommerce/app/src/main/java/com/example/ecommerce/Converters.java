package com.example.ecommerce;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    @TypeConverter
    public static String fromList(List<Integer> list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        return gson.toJson(list, type);
    }

    @TypeConverter
    public static List<Integer> toList(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();

        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            // If the data is not a JSON array, try to parse it as a single integer
            try {
                List<Integer> list = new ArrayList<>();
                list.add(gson.fromJson(json, Integer.class));
                return list;
            } catch (JsonSyntaxException innerException) {
                // Handle the exception or return an empty list as needed
                return new ArrayList<>();
            }
        }
    }

}
