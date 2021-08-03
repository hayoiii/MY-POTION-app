package com.palebluedot.mypotion.data.repository.detail;

import com.palebluedot.mypotion.data.model.PotionDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DetailApi {
    String BASE_URL = "https://openapi.foodsafetykorea.go.kr/api/";
    //PRDLST_REPORT_NO=200400200021640
    @GET("915bdf37e06e4760ac88/C003/json/1/1/PRDLST_REPORT_NO={serialNo}")
    Call<PotionDetail> getDetail(@Path("serialNo") String SerialNo);
}
