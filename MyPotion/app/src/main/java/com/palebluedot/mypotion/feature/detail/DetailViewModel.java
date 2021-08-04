package com.palebluedot.mypotion.feature.detail;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.palebluedot.mypotion.data.model.Like;
import com.palebluedot.mypotion.data.model.PotionDetail;
import com.palebluedot.mypotion.data.repository.RepositoryCallback;
import com.palebluedot.mypotion.data.repository.detail.DetailRepository;
import com.palebluedot.mypotion.data.repository.like.LikeRepository;
import com.palebluedot.mypotion.util.TagManager;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.ArrayList;

public class DetailViewModel extends AndroidViewModel implements ShineButton.OnCheckedChangeListener {
    private DetailRepository detailRepository;
    private LikeRepository likeRepository;
    private MutableLiveData<PotionDetail> mData = new MutableLiveData<>();
    private MutableLiveData<Boolean> like = new MutableLiveData<>(false);
    private Like likeItem = null;
    private String serialNo;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        detailRepository = DetailRepository.getInstance();
        likeRepository = new LikeRepository(application);
    }

    public void build(String serialNo) {
        this.serialNo = serialNo;
        detailRepository.fetchDetail(serialNo, detailCallback);
        likeRepository.isLiked(serialNo, isLikedCallback);
    }

    public LiveData<PotionDetail> getDetail() { return mData; }
    public LiveData<Boolean> getLike() { return like; }

    private RepositoryCallback<Boolean> isLikedCallback = new RepositoryCallback() {
        @Override
        public void onComplete(Object result) {
            like.setValue((Boolean) result);
        }
    };

    private RepositoryCallback<LiveData<PotionDetail>> detailCallback = new RepositoryCallback() {
        @Override
        public void onComplete(Object result) {
            mData.setValue((PotionDetail) ((LiveData<?>) result).getValue());
        }
    };

    @Override
    public void onCheckedChanged(View view, boolean checked) {
        if(mData.getValue() == null)    return;
        if (checked) {
            //like 데이터 생성
            if(likeItem == null){
                PotionDetail potionDetail = mData.getValue();
                ArrayList<String> tags = TagManager.getInstance().extract(potionDetail.getEffect());
                likeItem = new Like(potionDetail.getName(), potionDetail.getFactory(), serialNo, tags);
            }
            likeRepository.like(likeItem);
        }
        else {
            likeRepository.dislike(serialNo);
        }
        like.setValue(checked);
    }
}
