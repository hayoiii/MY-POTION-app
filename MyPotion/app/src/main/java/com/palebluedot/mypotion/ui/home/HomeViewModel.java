package com.palebluedot.mypotion.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.palebluedot.mypotion.data.model.Intake;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.repository.intake.IntakeRepository;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionRepository;
import com.palebluedot.mypotion.util.MyUtil;
import com.palebluedot.mypotion.util.TagManager;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeViewModel extends AndroidViewModel {
    public LiveData<List<MyPotion>> mPotionList;
    public MutableLiveData<MyPotion> mPotion;
    public MutableLiveData<Intake> mLastIntake;

    private MyPotionRepository potionRepository;
    private IntakeRepository intakeRepository;
    public HomeViewModel(Application application) {
        super(application);
        potionRepository = new MyPotionRepository(application.getApplicationContext());
        intakeRepository = new IntakeRepository(application.getApplicationContext());
        mPotion = new MutableLiveData<MyPotion>();
        mPotionList = potionRepository.getHomeList();
        mLastIntake = new MutableLiveData<>();
    }

    /* potion card data */
    public String getAlias() {
        return mPotion.getValue()!=null ? mPotion.getValue().alias : null;
    }
    public String getFactory() {
        return mPotion.getValue()!=null ? mPotion.getValue().factory : null;
    }
    public String getDday(int potionId) {
        //TODO: calendar repo 연결 후 dday 계산
        String ret = "TODAY";

        return ret;
    }
    public String getDiffFromLast() {
        if(mPotion.getValue() == null)
            return "null";
        // intake_calendar - 마지막 복용일로부터 지난 일 수 계산
        Intake last = intakeRepository.getLastIntake(mPotion.getValue().id);
        if (last == null){
            return "없음";
        }
        Date lastDate = MyUtil.stringToDate(last.date);
        Date today = MyUtil.getFormattedToday();

        long lastDayMsDiff = today.getTime() - lastDate.getTime(); //항상 양수
        long lastDayDiff = lastDayMsDiff / (24 * 60 * 60 * 1000);

        return lastDayDiff + "일 전";
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
        long dayDiff = msDiff / (24 * 60 * 60 * 1000) + 1;
        return String.valueOf(dayDiff)+"일 째 진행중";
    }

    public void onItemClickListener(int pos) {
        if(pos > -1)
            mPotion.setValue(mPotionList.getValue().get(pos));
    }
}