package com.palebluedot.mypotion.data.repository.mypotion;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.model.MyPotionId;

import java.util.List;

// https://developer.android.com/training/data-storage/room/accessing-data?hl=ko
@Dao
public interface MyPotionDao {
    @Query("SELECT * FROM my_potion")
    LiveData<List<MyPotion>> getAll();

    @Query("SELECT * FROM my_potion WHERE finish_date = '' || finish_date is null")
    LiveData<List<MyPotion>> getAllInProgress();

    @Insert
    void insert(@NonNull MyPotion potion);

    @Query("SELECT rowid FROM my_potion WHERE alias = :alias LIMIT 1")
    MyPotionId[] findDuplicatedAliasId(String alias);
}
