package com.palebluedot.mypotion.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionRepository;
import com.palebluedot.mypotion.util.Constant;
import com.palebluedot.mypotion.util.MyUtil;
import com.palebluedot.mypotion.util.TagManager;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    public LiveData<List<MyPotion>> mList;
    public MutableLiveData<MyPotion> mPotion;
    private MyPotionRepository repository;
//TODO: Caused by: java.lang.InstantiationException: java.lang.Class<com.palebluedot.mypotion.ui.home.HomeViewModel> has no zero argument constructor
    public HomeViewModel(Application application) {
        super(application);
        repository = new MyPotionRepository(application.getApplicationContext());
        mPotion = new MutableLiveData<MyPotion>();
        mList = repository.getHomeList();
    }

    /* potion card data */
    public String getAlias() {
        return mPotion.getValue()!=null ? mPotion.getValue().alias : null;
    }
    public String getFactory() {
        return mPotion.getValue()!=null ? mPotion.getValue().factory : null;
    }
    public String getDday() {
        //TODO: calendar repo 연결 후 dday 계산
        String ret = "TODAY";
        return ret;
    }
    public String getDiffFromLast() {
        //TODO: calendar repo 연결 후 마지막 복용일로부터 지난 일 수 계산
        String ret = "1일 전";
        return ret;
    }
    public String getEffect() {
        if(mPotion.getValue() != null)
            return TagManager.getInstance().listToString(mPotion.getValue().effectTags);
        else
            return null;
    }
    public String getIngDays() {
        if(mPotion.getValue() == null){
            return null;
        }
        String beginStr = mPotion.getValue().beginDate;
        Date today = MyUtil.getFormattedToday();
        Date beginDate = MyUtil.stringToDate(beginStr);
        long msDiff = beginDate.getTime() - today.getTime();
        long dayDiff = msDiff / (24 * 60 * 60 * 1000);
        return String.valueOf(dayDiff)+"일 째 진행중";
    }

    public void onItemClickListener(int pos) {
        if(pos > -1)
            mPotion.setValue(mList.getValue().get(pos));
    }
}