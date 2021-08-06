package com.palebluedot.mypotion.data.repository;

import com.palebluedot.mypotion.data.repository.detail.DetailApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static RetrofitUtil mRetrofitUtil = new RetrofitUtil();
    private DetailApi detailApi;

    //singleton
    public static RetrofitUtil getInstance() {
        return mRetrofitUtil;
    }
    private RetrofitUtil(){
        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(DetailApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        detailApi = mRetrofit.create(DetailApi.class);
    }
    public DetailApi getDetailApi(){
        return detailApi;
    }
}
