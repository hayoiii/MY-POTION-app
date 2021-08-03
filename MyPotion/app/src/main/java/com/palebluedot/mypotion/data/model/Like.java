package com.palebluedot.mypotion.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "like")
public class Like {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="rowid")
    public int id;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="factory")
    public String factory;

    @ColumnInfo(name="serial_no")
    public String serialNo;

    @ColumnInfo(name="effect_tag")
    public String[] effectTags;
}
