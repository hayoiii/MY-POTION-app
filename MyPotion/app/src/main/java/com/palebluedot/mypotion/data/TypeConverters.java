package com.palebluedot.mypotion.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TypeConverters {

    @TypeConverter
    public static String fromList(List<String> value) {
        return new Gson().toJson(value);
    }

    @TypeConverter
    public static List<String> stringToList(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
}
