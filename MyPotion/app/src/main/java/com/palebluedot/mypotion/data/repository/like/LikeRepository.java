package com.palebluedot.mypotion.data.repository.like;

import android.app.Application;

import androidx.annotation.NonNull;

import com.palebluedot.mypotion.data.model.Like;
import com.palebluedot.mypotion.data.repository.RepositoryCallback;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionDatabase;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LikeRepository {
    private LikeDatabase database;
    private LikeDao dao;

    public LikeRepository(Application application){
        this.database = LikeDatabase.getInstance(application);
        this.dao = database.likeDao();
    }

    public void isLiked(final @NonNull String serialNo, final @NonNull RepositoryCallback<Boolean> callback){
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                Like result = dao.findBySerialNo(serialNo);
                callback.onComplete(result != null);
            }
        };
        Executor diskIO= Executors.newSingleThreadExecutor();
        diskIO.execute(runnable);
    }
}
