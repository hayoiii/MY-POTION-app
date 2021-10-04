package com.palebluedot.mypotion.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "my_potion")
public class MyPotion implements Parcelable {
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
    public String beginDate;

    @ColumnInfo(name="finish_date")
    public String finishDate;

    @ColumnInfo(name="effect_tag")
    public List<String> effectTags;

    @ColumnInfo(name="memo")
    public String memo;

    @ColumnInfo(name="days")
    public int day;

    @ColumnInfo(name="times")
    public int times;

    @ColumnInfo(name="when_flag")
    public int whenFlag;

    @Nullable
    @ColumnInfo(name="serial_no")
    public String serialNo;


    public MyPotion(String serialNo, String alias, String name, String factory, String beginDate, String finishDate, List<String> effectTags, String memo, int day, int times, int whenFlag) {
        this.serialNo = serialNo;
        this.alias = alias;
        this.name = name;
        this.factory = factory;
        this.beginDate = beginDate;
        this.finishDate = finishDate;
        this.effectTags = effectTags;
        this.memo = memo;
        this.day = day;
        this.times = times;
        this.whenFlag = whenFlag;
    }

    protected MyPotion(Parcel in) {
        id = in.readInt();
        alias = in.readString();
        name = in.readString();
        factory = in.readString();
        beginDate = in.readString();
        finishDate = in.readString();
        effectTags = in.createStringArrayList();
        memo = in.readString();
        day = in.readInt();
        times = in.readInt();
        whenFlag = in.readInt();
        serialNo = in.readString();
    }

    public static final Creator<MyPotion> CREATOR = new Creator<MyPotion>() {
        @Override
        public MyPotion createFromParcel(Parcel in) {
            return new MyPotion(in);
        }

        @Override
        public MyPotion[] newArray(int size) {
            return new MyPotion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(alias);
        parcel.writeString(name);
        parcel.writeString(factory);
        parcel.writeString(beginDate);
        parcel.writeString(finishDate);
        parcel.writeStringList(effectTags);
        parcel.writeString(memo);
        parcel.writeInt(day);
        parcel.writeInt(times);
        parcel.writeInt(whenFlag);
        parcel.writeString(serialNo);
    }
}