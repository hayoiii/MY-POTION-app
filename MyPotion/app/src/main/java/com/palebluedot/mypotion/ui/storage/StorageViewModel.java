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
import com.palebluedot.mypotion.util.MyUtil;
import com.palebluedot.mypotion.util.TagManager;

import java.util.Date;
import java.util.List;

public class StorageViewModel extends AndroidViewModel {
    private MyPotionRepository potionRepository;
    private LikeRepository likeRepository;
    public MutableLiveData<List<MyPotion>> mPotionList;
    public MutableLiveData<List<Like>> mLikeList;
    public MutableLiveData<MyPotion> mPotion;

    public StorageViewModel(Application application) {
        super(application);
        this.potionRepository = new MyPotionRepository(application.getApplicationContext());
        this.likeRepository = new LikeRepository(application);
        this.mPotionList = new MutableLiveData<>();
        this.mLikeList = new MutableLiveData<>();
        this.mPotionList.postValue(potionRepository.getFinishedList());
        this.mLikeList.postValue(likeRepository.getLikeList());
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
        long msDiff = today.getTime() - beginDate.getTime();
        long dayDiff = Math.abs(msDiff) / (24 * 60 * 60 * 1000);
        return "총 "+ dayDiff +"일";
    }
}