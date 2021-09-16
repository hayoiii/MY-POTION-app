package com.palebluedot.mypotion.ui.storage;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.palebluedot.mypotion.data.model.Like;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.repository.like.LikeRepository;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionRepository;

import java.util.List;

public class StorageViewModel extends AndroidViewModel {
    private MyPotionRepository potionRepository;
    private LikeRepository likeRepository;
    public List<MyPotion> mPotionList;
    public List<Like> mLikeList;

    public StorageViewModel(Application application) {
        super(application);
        this.potionRepository = new MyPotionRepository(application.getApplicationContext());
        this.likeRepository = new LikeRepository(application);
        this.mPotionList = potionRepository.getFinishedList();
        this.mLikeList = likeRepository.getLikeList();
    }
}