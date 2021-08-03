package com.palebluedot.mypotion.data.repository.mypotion;

import android.app.Application;
import android.content.Context;

import com.palebluedot.mypotion.data.model.MyPotion;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyPotionRepository {
    private MyPotionDatabase database;
    private MyPotionDao dao;

    public MyPotionRepository(Application application) {
        this.database = MyPotionDatabase.getInstance(application);
        this.dao = database.myPotionDao();
    }

    public void insert(MyPotion potion) {
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                dao.insert(potion);
            }
        };
        Executor diskIO= Executors.newSingleThreadExecutor();
        diskIO.execute(runnable);
    }
}
