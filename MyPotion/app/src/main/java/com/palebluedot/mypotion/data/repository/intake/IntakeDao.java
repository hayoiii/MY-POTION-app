package com.palebluedot.mypotion.data.repository.intake;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.palebluedot.mypotion.data.model.Intake;

import java.util.List;

@Dao
public interface IntakeDao {
    @Query("SELECT * FROM intake_calendar WHERE potion_id = :potionId ORDER BY intake_date DESC LIMIT 1")
    List<Intake> getLastById(int potionId);

    @Query("SELECT * FROM intake_calendar WHERE potion_id = :potionId AND intake_date = date('now', 'localtime')")
    List<Intake> getTodayDataById(int potionId);

    @Insert
    void insert(Intake intake);

    @Update
    int update(Intake... intakes);
}
