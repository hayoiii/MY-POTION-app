package com.palebluedot.mypotion.data.repository.mypotion;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionDao;

@Database(entities = {MyPotion.class}, version = 1)
public abstract class MyPotionDb extends RoomDatabase {
    private MyPotionDb instance = null;

    public abstract MyPotionDao myPotionDao();

    // Singleton
    public MyPotionDb getInstance(Context context) {
        if(instance == null) {
            synchronized (MyPotionDb.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), MyPotionDb.class, "my_potion")
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