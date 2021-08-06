package com.palebluedot.mypotion.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

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
    public ArrayList<String> effectTags;


    public Like(String name, String factory, String serialNo, ArrayList<String> effectTags) {
        this.name = name;
        this.factory = factory;
        this.serialNo = serialNo;
        this.effectTags = effectTags;
    }
}
