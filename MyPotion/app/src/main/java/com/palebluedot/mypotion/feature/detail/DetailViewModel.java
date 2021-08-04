package com.palebluedot.mypotion.feature.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.palebluedot.mypotion.data.model.PotionDetail;
import com.palebluedot.mypotion.data.repository.RepositoryCallback;
import com.palebluedot.mypotion.data.repository.detail.DetailRepository;
import com.palebluedot.mypotion.data.repository.like.LikeRepository;

public class DetailViewModel extends AndroidViewModel {
    private DetailRepository detailRepository;
    private LikeRepository likeRepository;
    private MutableLiveData<PotionDetail> mData = new MutableLiveData<>();
    private String serialNo;
    private boolean like;

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

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

    public LiveData<PotionDetail> getDetail() {
        return mData;
    }

    private RepositoryCallback<Boolean> isLikedCallback = new RepositoryCallback() {
        @Override
        public void onComplete(Object result) {
            like = (boolean) result;
        }
    };

    private RepositoryCallback<LiveData<PotionDetail>> detailCallback = new RepositoryCallback() {
        @Override
        public void onComplete(Object result) {
            mData.setValue((PotionDetail) ((LiveData<?>) result).getValue());
        }
    };
}
