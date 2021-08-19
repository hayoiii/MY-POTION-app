package com.palebluedot.mypotion.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "intake_calendar")
public class Intake {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="rowid")
    public int id;

    @ColumnInfo(name="intake_date")
    public String date;

    @ColumnInfo(name = "intake_time")
    public String time;

    @ColumnInfo(name = "total_times")
    public int totalTimes;

    @ColumnInfo(name = "when_flag")
    public int whenFlag;

    @ColumnInfo(name = "potion_id")
    public int potionId;

    public Intake(String date, String time, int totalTimes, int whenFlag, int potionId) {
        this.date = date;
        this.time = time;
        this.totalTimes = totalTimes;
        this.whenFlag = whenFlag;
        this.potionId = potionId;
    }
}
