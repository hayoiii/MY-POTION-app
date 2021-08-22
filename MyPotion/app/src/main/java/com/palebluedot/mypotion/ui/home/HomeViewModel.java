package com.palebluedot.mypotion.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.palebluedot.mypotion.data.model.Intake;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.repository.intake.IntakeRepository;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionRepository;
import com.palebluedot.mypotion.util.MyUtil;
import com.palebluedot.mypotion.util.TagManager;
import com.palebluedot.mypotion.util.WhenManager;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeViewModel extends AndroidViewModel {
    public LiveData<List<MyPotion>> mPotionList;
    public MutableLiveData<MyPotion> mPotion;
    public MutableLiveData<Intake> mTodayIntake;

    private MyPotionRepository potionRepository;
    private IntakeRepository intakeRepository;
    public HomeViewModel(Application application) {
        super(application);
        potionRepository = new MyPotionRepository(application.getApplicationContext());
        intakeRepository = new IntakeRepository(application.getApplicationContext());
        mPotion = new MutableLiveData<MyPotion>();
        mPotionList = potionRepository.getHomeList();
        mTodayIntake = new MutableLiveData<>();
    }

    public void intake() {
        if(mPotion.getValue() == null) return;
        Intake todayIntake = mTodayIntake.getValue();
        boolean update = todayIntake != null;
        Intake intake;
        String date = MyUtil.dateToString(new Date());
        String time = MyUtil.dateToTimeString(new Date());
        int totalTimes = 1;
        int whenFlag = WhenManager.now(getApplication().getSharedPreferences(WhenManager.SP_NAME, Context.MODE_PRIVATE));
        if(update) {
            totalTimes = todayIntake.totalTimes + 1;
            whenFlag |= todayIntake.whenFlag;
            time = todayIntake.time + time;
            intake = new Intake(todayIntake.id, date, time, totalTimes, whenFlag, mPotion.getValue().id);
        }
        else {
            intake = new Intake(date, time, totalTimes, whenFlag, mPotion.getValue().id);
        }
        intakeRepository.intake(intake, update);
        mTodayIntake.setValue(intake);
    }

    /* potion card data */
    public String getFactory() {
        return mPotion.getValue()!=null ? mPotion.getValue().factory : null;
    }
    public String getEffect() {
        if(mPotion.getValue() != null)
            return TagManager.getInstance().listToString(mPotion.getValue().effectTags);
        else
            return null;
    }
    public String getDday(MyPotion potion) {
        if(potion == null)  return null;

        Date beginDate = MyUtil.stringToDate(potion.beginDate);
        Date today = MyUtil.getFormattedToday();

        // 아직 시작 전인 포션
        if(today.before(beginDate)){
            long msDiff = beginDate.getTime() - today.getTime();
            long dayDiff = msDiff / (24 * 60 * 60 * 1000);
            return dayDiff + "일 후";
        }

        // intake_calendar - 마지막 복용일로부터 몇 일 후 복용해야 하는 지 계산
        Intake last = intakeRepository.getLastIntake(potion.id);
        //복용 데이터 없을 때
        if (last == null){
            return "TODAY";
        }

        Date lastDate = MyUtil.stringToDate(last.date);
        long lastDayMsDiff = today.getTime() - lastDate.getTime(); //항상 양수
        long lastDayDiff = lastDayMsDiff / (24 * 60 * 60 * 1000);

        String dday = "";
        int day = potion.day;
        //마지막 기록이 오늘일 때
        if (lastDayDiff == 0) {
            int totalTimes = last.totalTimes;
            if (totalTimes >= potion.times)
                dday = day + "일 후";
            else
                // 아직 하루 복용 횟수를 다 못채웠을 때
                dday = "TODAY";
        }
        // 먹어야될 주기이거나 지났을 때
        else if (lastDayDiff >= day) dday = "TODAY";

        // 먹어야 될 주기가 아직 되지 않았을 때
        else dday = (day - lastDayDiff) + "일 후";

        return dday;
    }
    public String getDiffFromLast() {
        if(mPotion.getValue() == null)
            return null;
        // intake_calendar - 마지막 복용일로부터 지난 일 수 계산
        Intake last = intakeRepository.getLastIntake(mPotion.getValue().id);
        if (last == null){
            return "첫 기록을 해보세요";
        }
        Date lastDate = MyUtil.stringToDate(last.date);
        Date today = MyUtil.getFormattedToday();

        long lastDayMsDiff = today.getTime() - lastDate.getTime(); //항상 양수
        long lastDayDiff = lastDayMsDiff / (24 * 60 * 60 * 1000);
        if(lastDayDiff == 0)
            return "오늘";

        return lastDayDiff + "일 전";
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

        // 아직 시작 전인 포션
        if(today.before(beginDate)){
            return dayDiff + "일 후 시작";
        }

        return dayDiff +"일 째 진행중";
    }
    public int getTotalTimes() {
        if(mPotion.getValue() == null || mTodayIntake.getValue() == null)
            return 0;

        return mTodayIntake.getValue().totalTimes;
    }
    /* -- potion card data --*/

    //initiate the potion card
    public void onItemClick(int pos) {
        if(pos > -1 && mPotionList.getValue().size() > pos) {
            mPotion.setValue(mPotionList.getValue().get(pos));
            Intake todayIntake = intakeRepository.getTodayData(mPotion.getValue().id);
            mTodayIntake.setValue(todayIntake);
        }
    }
}