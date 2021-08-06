package com.palebluedot.mypotion.data.repository.detail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("MSG")
    @Expose
    private String MSG;
    @SerializedName("CODE")
    @Expose
    private String CODE;

    @Override
    public String toString() {
        return "Result{" +
                "MSG='" + MSG + '\'' +
                ", CODE='" + CODE + '\'' +
                '}';
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public Result(String MSG, String CODE) {
        this.MSG = MSG;
        this.CODE = CODE;
    }
}
