package com.palebluedot.mypotion.ui.home;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionRepository;
import com.palebluedot.mypotion.util.TagManager;

import java.util.List;

public class HomeViewModel extends ViewModel {
    public LiveData<List<MyPotion>> mList;
    public LiveData<MyPotion> mPotion;
    private MyPotionRepository repository;
    private MutableLiveData<Integer> pos;

    public HomeViewModel(Context context) {
        repository = new MyPotionRepository(context);
        mPotion = repository.getPotionData();
        mList = repository.getHomeList();
        pos = new MutableLiveData<Integer>();
    }

    public String getAlias() {
        return mList.getValue().get(pos.getValue()).alias;
    }
    public String getFactory() {
        return mList.getValue().get(pos.getValue()).factory;
    }
    public String getDday() {
        //TODO: calendar repo 연결
        String ret = "TODAY";
        return ret;
    }
}