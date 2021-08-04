package com.palebluedot.mypotion.data.repository.detail;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.palebluedot.mypotion.data.model.PotionDetail;
import com.palebluedot.mypotion.data.repository.RetrofitUtil;
import com.palebluedot.mypotion.data.repository.detail.model.DetailVo;
import com.palebluedot.mypotion.data.repository.results.SearchResultsRepository;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailRepository {
    private static DetailRepository instance = null;
    private RetrofitUtil mRetrofitUtil;
    private DetailApi api;
    private MutableLiveData<PotionDetail> data = new MutableLiveData<>();

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

    public LiveData<PotionDetail> getDetail(String serialNo) {
        //TODO: error handling
        Call<DetailVo> res = api.getDetail(serialNo);
        try {
            DetailVo detailVo = (DetailVo) res.execute().body();
            if (detailVo.getC003().getRESULT().getCODE().equals("INFO-000")) {
                String name = detailVo.getC003().getRow().get(0).getPRDLST_NM();
                String factory = detailVo.getC003().getRow().get(0).getBSSH_NM();
                String shape = detailVo.getC003().getRow().get(0).getDISPOS();
                String takeWay = detailVo.getC003().getRow().get(0).getNTK_MTHD();
                String effect = detailVo.getC003().getRow().get(0).getPRIMARY_FNCLTY();
                String caution = detailVo.getC003().getRow().get(0).getIFTKN_ATNT_MATR_CN();
                String storeWay = detailVo.getC003().getRow().get(0).getCSTDY_MTHD();
                String rawMaterials = detailVo.getC003().getRow().get(0).getRAWMTRL_NM();

                data.setValue(new PotionDetail(takeWay, name, rawMaterials, effect, factory, caution, storeWay, shape));
            } else {
                //TODO: 오류 코드 시 처리
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}

