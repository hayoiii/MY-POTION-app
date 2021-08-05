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
    private final DetailRepository detailRepository;
    private final LikeRepository likeRepository;
    private final MutableLiveData<PotionDetail> mData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> like = new MutableLiveData<>(false);
    private Like likeItem = null;
    private String serialNo;

    ArrayList<String> tags;
    private String name;
    private String factory;

    public void setName(String name) {
        this.name = name;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTagStyleString() {
        return TagManager.getInstance().toTagStyle(tags);
    }


    public DetailViewModel(@NonNull Application application) {
        super(application);
        detailRepository = DetailRepository.getInstance();
        likeRepository = new LikeRepository(application);
    }

    public void build(String serialNo) {
        this.serialNo = serialNo;
        detailRepository.fetchDetail(serialNo, detailCallback);
        likeRepository.isLiked(serialNo, likeCallback);
    }

    public LiveData<PotionDetail> getDetail() { return mData; }
    public LiveData<Boolean> getLike() { return like; }

    private RepositoryCallback<Boolean> likeCallback = (RepositoryCallback) result -> like.postValue((Boolean) result);

    private final RepositoryCallback<PotionDetail> detailCallback = new RepositoryCallback<PotionDetail>() {
        @Override
        public void onComplete(PotionDetail result) {
            if(result == null) {
                //api result code == 'code-200' no matched data
                result = new PotionDetail(name, factory);
            }
            else tags = TagManager.getInstance().extract(result.getEffect());
            mData.postValue(result);
        }
    };

    @Override
    public void onCheckedChanged(View view, boolean checked) {
        if(mData.getValue() == null)    return;
        if (checked) {
            //like 데이터 생성
            if(likeItem == null){
                PotionDetail potionDetail = mData.getValue();
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
