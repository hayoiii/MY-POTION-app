package com.palebluedot.mypotion.data.repository.detail;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.palebluedot.mypotion.data.model.PotionDetail;
import com.palebluedot.mypotion.data.repository.RepositoryCallback;
import com.palebluedot.mypotion.data.repository.RetrofitUtil;
import com.palebluedot.mypotion.data.repository.detail.model.Detail;
import com.palebluedot.mypotion.data.repository.detail.model.DetailVo;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailRepository {
    private static DetailRepository instance = null;
    private RetrofitUtil mRetrofitUtil;
    private DetailApi api;

    private DetailRepository() {
        this.mRetrofitUtil = RetrofitUtil.getInstance();
        this.api = mRetrofitUtil.getDetailApi();
    }

    public static DetailRepository getInstance() {
        if(instance == null) {
            synchronized (DetailRepository.class) {
                if(instance == null) {
                    instance = new DetailRepository();
                }
            }
        }
        return instance;
    }

    public void fetchDetail(@NonNull String serialNo, @NonNull RepositoryCallback<PotionDetail> callback) {
        //TODO: error handling
        Call<DetailVo> res = api.getDetail(serialNo);
        res.enqueue(new Callback<DetailVo>() {
            @Override
            public void onResponse(Call<DetailVo> call, Response<DetailVo> response) {
                if (response.body() != null) {
                    DetailVo detailVo = (DetailVo) response.body();
                    if (detailVo.getC003().getRESULT().getCODE().equals("INFO-000")) {
                        String name = detailVo.getC003().getRow().get(0).getPRDLST_NM();
                        String factory = detailVo.getC003().getRow().get(0).getBSSH_NM();
                        String shape = detailVo.getC003().getRow().get(0).getDISPOS();
                        String takeWay = detailVo.getC003().getRow().get(0).getNTK_MTHD();
                        String effect = detailVo.getC003().getRow().get(0).getPRIMARY_FNCLTY();
                        String caution = detailVo.getC003().getRow().get(0).getIFTKN_ATNT_MATR_CN();
                        String storeWay = detailVo.getC003().getRow().get(0).getCSTDY_MTHD();
                        String rawMaterials = detailVo.getC003().getRow().get(0).getRAWMTRL_NM();
                        String expiration = detailVo.getC003().getRow().get(0).getPOG_DAYCNT();

                        PotionDetail data = new PotionDetail(takeWay, name, rawMaterials, expiration, effect, factory, caution, storeWay, shape);
                        callback.onComplete(data);
                    } else {
                        //TODO: 오류 코드 시 처리
                    }
                }
            }
            @Override
            public void onFailure(Call<DetailVo> call, Throwable t) {
                Log.e("Err", t.getMessage());
            }
        });
    }
}

