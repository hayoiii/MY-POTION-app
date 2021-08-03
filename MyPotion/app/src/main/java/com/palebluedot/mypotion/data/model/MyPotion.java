package com.palebluedot.mypotion.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.sql.Date;

@Entity(tableName = "my_potion")
public class MyPotion {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="rowid")
    public int id;

    @ColumnInfo(name="alias")
    public String alias;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="factory")
    public String factory;

    @ColumnInfo(name="begin_date")
    public Date beginDate;

    @ColumnInfo(name="finish_date")
    public Date finishDate;

    @ColumnInfo(name="effect_tag")
    public String[] effectTags;

    @ColumnInfo(name="memo")
    public String memo;

    @ColumnInfo(name="days")
    public int day;

    @ColumnInfo(name="times")
    public int times;

    @ColumnInfo(name="when_flag")
    public int whenFlag;
}