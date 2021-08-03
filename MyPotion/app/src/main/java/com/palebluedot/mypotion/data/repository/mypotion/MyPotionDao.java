package com.palebluedot.mypotion.data.repository.mypotion;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.palebluedot.mypotion.data.model.MyPotion;

import java.util.List;

@Dao
public interface MyPotionDao {
    @Query("SELECT * FROM my_potion")
    LiveData<List<MyPotion>> getAll();
}
