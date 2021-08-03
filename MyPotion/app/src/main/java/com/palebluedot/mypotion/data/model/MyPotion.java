package com.palebluedot.mypotion.data.model;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.Arrays;

public class MyPotion {
    private String alias = "";
    private String name;
    private String factory;
    private LocalDate startDate = null;
    private String[] effects = null;
    private String memo = "";

    @NonNull
    @Override
    public String toString() {
        return "Elixir{" +
                "alias='" + alias + '\'' +
                ", name='" + name + '\'' +
                ", factory='" + factory + '\'' +
                ", startDate=" + startDate +
                ", effects=" + Arrays.toString(effects) +
                ", memo='" + memo + '\'' +
                '}';
    }

    public MyPotion(String alias, String name, String factory, LocalDate startDate, String[] effects, String memo) {
        this.alias = alias;
        this.name = name;
        this.factory = factory;
        this.startDate = startDate;
        this.effects = effects;
        this.memo = memo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String[] getEffects() {
        return effects;
    }

    public void setEffects(String[] effects) {
        this.effects = effects;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
