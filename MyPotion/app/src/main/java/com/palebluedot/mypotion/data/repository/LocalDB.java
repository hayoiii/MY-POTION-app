package com.palebluedot.mypotion.data.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionDao;

@Database(entities = {MyPotion.class}, version = 1)
public abstract class LocalDB extends RoomDatabase {
    public abstract MyPotionDao myPotionDao();
}

/*
    생성한 데이터베이스 인스턴스 가져오기
    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
    AppDatabase.class, "database-name").build();
 */