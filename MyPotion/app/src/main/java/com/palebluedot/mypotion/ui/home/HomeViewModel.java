package com.palebluedot.mypotion.ui.home;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.model.MyPotionItem;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private LiveData<List<MyPotion>> mList;
    private MyPotionRepository repository;
    private MutableLiveData<Integer> pos;

    public HomeViewModel(Context context) {
        repository = new MyPotionRepository(context);
        mList = repository.getData();
        mList.observe((LifecycleOwner) context, new Observer<List<MyPotion>>() {
            @Override
            public void onChanged(List<MyPotion> myPotions) {

            }
        });
        pos.observe((LifecycleOwner) context, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });
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