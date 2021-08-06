package com.palebluedot.mypotion.data.repository.detail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Detail {
    @SerializedName("RESULT")
    @Expose
    private Result RESULT;
    @SerializedName("row")
    @Expose
    private List<Row> row;
    @SerializedName("total_count")
    @Expose
    private String total_count;

    @Override
    public String toString() {
        return "PotionDetail{" +
                "RESULT=" + RESULT +
                ", row=" + row +
                ", total_count='" + total_count + '\'' +
                '}';
    }

    public Result getRESULT() {
        return RESULT;
    }

    public void setRESULT(Result RESULT) {
        this.RESULT = RESULT;
    }

    public List<Row> getRow() {
        return row;
    }

    public void setRow(List<Row> row) {
        this.row = row;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public Detail(Result RESULT, List<Row> row, String total_count) {
        this.RESULT = RESULT;
        this.row = row;
        this.total_count = total_count;
    }
}