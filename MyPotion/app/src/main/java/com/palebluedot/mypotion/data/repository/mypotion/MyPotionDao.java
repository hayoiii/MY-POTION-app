package com.palebluedot.mypotion.data.repository.mypotion;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.model.MyPotionId;

import java.util.List;

// https://developer.android.com/training/data-storage/room/accessing-data?hl=ko
@Dao
public interface MyPotionDao {
    @Query("SELECT * FROM my_potion")
    List<MyPotion> getAll();

    @Query("SELECT * FROM my_potion WHERE finish_date is null")
    List<MyPotion> getAllExceptFinsihed();

    @Query("SELECT * FROM my_potion WHERE finish_date is not null")
    List<MyPotion> getAllFisnished();

    @Query("SELECT * FROM my_potion WHERE rowid = :id")
    MyPotion getPotionById(int id);

    @Query("SELECT rowid FROM my_potion WHERE alias = :alias LIMIT 1")
    MyPotionId[] findDuplicatedAliasId(String alias);

    @Insert
    void insert(@NonNull MyPotion potion);

    @Update
    void update(MyPotion potion);

    @Delete
    void delete(MyPotion potion);
}
