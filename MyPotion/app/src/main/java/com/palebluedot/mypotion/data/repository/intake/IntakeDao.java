package com.palebluedot.mypotion.data.repository.intake;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.palebluedot.mypotion.data.model.Intake;

import java.util.List;

@Dao
public interface IntakeDao {
    @Query("SELECT * FROM intake_calendar WHERE potion_id = :potionId ORDER BY rowid DESC LIMIT 1")
    List<Intake> getLastById(int potionId);

    @Insert
    void insert(Intake intake);

    @Query("UPDATE intake_calendar " +
            "SET intake_time = :time, total_times = :totalTimes, when_flag = :whenFlag " +
            "WHERE rowid = :intakeId")
    void update(int intakeId, String time, int totalTimes, int whenFlag);
}
