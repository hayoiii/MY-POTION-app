package com.palebluedot.mypotion.data.repository.intake;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.palebluedot.mypotion.data.model.Intake;

@Database(entities = {Intake.class}, version = 1)
public abstract class IntakeDatabase extends RoomDatabase {
    private static IntakeDatabase instance = null;
    public abstract IntakeDao intakeDao();

    public static IntakeDatabase getInstance(final Context context) {
        if(instance == null) {
            synchronized (IntakeDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), IntakeDatabase.class, "intake_calendar")
                        .fallbackToDestructiveMigration()   // 데이터베이스 갱신 시 기존의 테이블 버리고 새로 사용
                        .build();
            }
        }
        return instance;
    }
}
