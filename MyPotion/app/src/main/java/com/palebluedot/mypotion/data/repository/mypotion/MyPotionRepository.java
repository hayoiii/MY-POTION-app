package com.palebluedot.mypotion.data.repository.mypotion;

import android.content.Context;
import android.os.AsyncTask;

import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.model.MyPotionId;
import com.palebluedot.mypotion.data.model.SearchResults;
import com.palebluedot.mypotion.data.repository.RepositoryCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyPotionRepository {
    private MyPotionDatabase database;
    MyPotionDao dao;
    boolean isDuplicated;

    public MyPotionRepository(Context context) {
        this.database = MyPotionDatabase.getInstance(context);
        this.dao = database.myPotionDao();
    }

    public void insert(MyPotion potion) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dao.insert(potion);
            }
        };
        Executor diskIO = Executors.newSingleThreadExecutor();
        diskIO.execute(runnable);
    }

    public boolean isDuplicatedAlias(String alias) {
        DuplicatedAliasService service = new DuplicatedAliasService(alias);
        try {
            return service.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private class DuplicatedAliasService extends AsyncTask<Void, Void, Boolean> {
        private String alias;

        public DuplicatedAliasService(String alias) {
            this.alias = alias;
        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            MyPotionId[] result = dao.findDuplicatedAliasId(alias);
            return result.length != 0;
        }
    }
}
