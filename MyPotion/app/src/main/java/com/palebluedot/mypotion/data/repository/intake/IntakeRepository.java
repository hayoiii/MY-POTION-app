package com.palebluedot.mypotion.data.repository.intake;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.palebluedot.mypotion.data.model.Intake;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class IntakeRepository {
    private IntakeDatabase database;
    IntakeDao dao;
    MutableLiveData<List<Intake>> listData;

    public IntakeRepository(Context context) {
        this.database = IntakeDatabase.getInstance(context);
        this.dao = database.intakeDao();
        listData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Intake>> getListData() {
        return listData;
    }

    @Nullable
    public Intake getLastIntake(int potionId) {
        LastDataService service = new LastDataService(potionId);
        try {
            return service.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void intake(Intake intake, boolean update){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(!update)
                    dao.insert(intake);
                else
                    dao.update(intake.id, intake.time, intake.totalTimes, intake.whenFlag);
            }
        };
        Executor diskIO = Executors.newSingleThreadExecutor();
        diskIO.execute(runnable);
    }

    private class LastDataService extends AsyncTask<Void, Void, Intake> {
        private final int potionId;
        LastDataService(int potionId){
            this.potionId = potionId;
        }
        @Override
        protected Intake doInBackground(Void... voids) {
            List<Intake> result = dao.getLastById(potionId);
            Intake ret = null;
            if(!result.isEmpty()) {
                ret = result.get(0);
            }
            return ret;
        }
    }
}
