package com.palebluedot.mypotion.data.repository.intake;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.palebluedot.mypotion.data.model.Intake;

import java.util.List;

@Dao
public interface IntakeDao {
    @Query("SELECT * FROM intake_calendar WHERE potion_id = :potionId ORDER BY rowid DESC LIMIT 1")
    List<Intake> getLastById(int potionId);

    @Insert
    void insert(Intake intake);
}
