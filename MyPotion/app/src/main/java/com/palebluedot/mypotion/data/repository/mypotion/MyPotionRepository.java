package com.palebluedot.mypotion.data.repository.mypotion;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.model.MyPotionId;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyPotionRepository {
    private MyPotionDatabase database;
    MyPotionDao dao;
    MutableLiveData<List<MyPotion>> listData;

    public MyPotionRepository(Context context) {
        this.database = MyPotionDatabase.getInstance(context);
        this.dao = database.myPotionDao();
        listData = new MutableLiveData<>();
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

    public void update(MyPotion potion) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dao.update(potion);
            }
        };
        Executor diskIO = Executors.newSingleThreadExecutor();
        diskIO.execute(runnable);
    }

    public void delete(MyPotion potion) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dao.delete(potion);
            }
        };
        Executor diskIO = Executors.newSingleThreadExecutor();
        diskIO.execute(runnable);
    }

    public LiveData<List<MyPotion>> getHomeList(){
        HomeListService service = new HomeListService();
        try {
            service.execute().get();
            return listData;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<MyPotion> getFinishedList() {
        FinishedListService service = new FinishedListService();
        try {
            return service.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class HomeListService extends AsyncTask<Void, Void, LiveData<List<MyPotion>>> {
        @Override
        protected LiveData<List<MyPotion>> doInBackground(Void... voids) {
            List<MyPotion> result = dao.getAllExceptFinsihed();
            listData.postValue(result);
            return listData;
        }
    }

    private class FinishedListService extends AsyncTask<Void, Void, List<MyPotion>> {
        @Override
        protected List<MyPotion> doInBackground(Void... voids) {
            List<MyPotion> result = dao.getAllFisnished();
            return result;
        }
    }

    public boolean isDuplicatedAlias(String alias) {
        DuplicatedAliasService service = new DuplicatedAliasService(alias);
        try {
            return service.execute().get();
        } catch (ExecutionException | InterruptedException e) {
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
