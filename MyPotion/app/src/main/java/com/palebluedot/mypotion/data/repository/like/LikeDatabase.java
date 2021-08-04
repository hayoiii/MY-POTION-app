package com.palebluedot.mypotion.data.repository.like;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.palebluedot.mypotion.data.model.Like;

@Database(entities = {Like.class}, version = 1)
@TypeConverters({com.palebluedot.mypotion.data.TypeConverters.class})
public abstract class LikeDatabase extends RoomDatabase {
    private static LikeDatabase instance = null;

    public abstract LikeDao likeDao();

    // Singleton
    public static LikeDatabase getInstance(final Context context) {
        if(instance == null) {
            synchronized (LikeDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), LikeDatabase.class, "like")
                        .fallbackToDestructiveMigration()   // 데이터베이스 갱신 시 기존의 테이블 버리고 새로 사용
                        .build();
            }
        }
        return instance;
    }
}
