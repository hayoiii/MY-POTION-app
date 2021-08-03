package com.palebluedot.mypotion.data.repository.mypotion;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.palebluedot.mypotion.data.model.MyPotion;

@Database(entities = {MyPotion.class}, version = 1)
public abstract class MyPotionDatabase extends RoomDatabase {
    private static MyPotionDatabase instance = null;

    public abstract MyPotionDao myPotionDao();

    // Singleton
    public static MyPotionDatabase getInstance(final Context context) {
        if(instance == null) {
            synchronized (MyPotionDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), MyPotionDatabase.class, "my_potion")
                        .fallbackToDestructiveMigration()   // 데이터베이스 갱신 시 기존의 테이블 버리고 새로 사용
                        .build();
            }
        }
        return instance;
    }
}

/*
    생성한 데이터베이스 인스턴스 가져오기
    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
    AppDatabase.class, "database-name").build();
 */