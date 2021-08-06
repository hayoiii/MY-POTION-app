package com.palebluedot.mypotion.data.repository.mypotion;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.palebluedot.mypotion.data.model.MyPotion;

import java.util.List;

// https://developer.android.com/training/data-storage/room/accessing-data?hl=ko
@Dao
public interface MyPotionDao {
    @Query("SELECT * FROM my_potion")
    LiveData<List<MyPotion>> getAll();

    @Insert
    void insert(MyPotion potion);
}
