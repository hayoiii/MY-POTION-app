package com.palebluedot.mypotion.data.repository.like;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.palebluedot.mypotion.data.model.Like;

import java.util.List;

@Dao
public interface LikeDao {
    @Query("SELECT * FROM `like`")
    LiveData<List<Like>> getAll();

    @NonNull
    @Query("SELECT rowid FROM `like` WHERE serial_no = :serialNo LIMIT 1")
    Like findBySerialNo(@NonNull String serialNo);

    @Insert
    void insert(Like like);

    @Query("DELETE FROM `like` WHERE serial_no = :serialNo")
    void delete(String serialNo);
}
