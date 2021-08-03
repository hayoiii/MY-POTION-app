package com.palebluedot.mypotion.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.sql.Time;

@Entity(tableName = "intake_calendar",
        foreignKeys = @ForeignKey(
                entity=MyPotion.class,
                parentColumns= "rowid",
                childColumns="potion_id")
)
public class Intake {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="rowid")
    public int id;

    @ColumnInfo(name="intake_date")
    public Date date;

    @ColumnInfo(name = "intake_time")
    public Time time;

    @ColumnInfo(name = "total_times")
    public int totalTimes;

    @ColumnInfo(name = "when_flag")
    public int whenFlag;

    @ColumnInfo(name = "potion_id")
    public int potionId;
}
