package com.palebluedot.mypotion.feature.produce;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.util.WhenManager;

import java.util.ArrayList;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.Step;

public class PeriodStep extends Step<PeriodStep.PeriodHolder> {


    private NumberPicker timesPicker;
    private NumberPicker daysPicker;
    private ChipGroup whenChips;
    private PeriodHolder mData;
    private int old_days, old_times, old_when;
    private boolean EDIT_MODE = false;

    public PeriodStep(String title){
        super(title);
    }
    public void setOld(int old_days, int old_times, int old_whenFlag) {
        this.old_days = old_days;
        this.old_times = old_times;
        this.old_when = old_whenFlag;
        EDIT_MODE = true;
    }


    @Override
    protected View createStepContentLayout() {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.form_period, null, false);
        timesPicker = view.findViewById(R.id.add_times_picker);
        daysPicker = view.findViewById(R.id.add_days_picker);
        whenChips = view.findViewById(R.id.when_chip_group);

        daysPicker.setMinValue(1);
        daysPicker.setMaxValue(100);
        daysPicker.setWrapSelectorWheel(false);
        daysPicker.setOnValueChangedListener(((picker, oldVal, newVal) -> mData.days = newVal));

        timesPicker.setMinValue(1);
        timesPicker.setMaxValue(100);
        timesPicker.setWrapSelectorWheel(false);
        timesPicker.setOnValueChangedListener(((picker, oldVal, newVal) -> mData.times = newVal));

        if(EDIT_MODE){
            daysPicker.setValue(old_days);
            timesPicker.setValue(old_times);
            for(int i = 0; i< WhenManager.WHEN_FLAGS.length; i++){
                if( (old_when & WhenManager.WHEN_FLAGS[i]) == WhenManager.WHEN_FLAGS[i]) {
                    Chip chip = (Chip) whenChips.getChildAt(i);
                    chip.setChecked(true);
                }
            }
        }
        mData = new PeriodHolder(daysPicker.getValue(), timesPicker.getValue(), whenChips);

        return view;
    }


    @Override
    public PeriodHolder getStepData() {
        return new PeriodHolder(daysPicker.getValue(), timesPicker.getValue(), whenChips);
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        List<String> whenList = new ArrayList<>();
        List<Integer> checkedIds = whenChips.getCheckedChipIds();
        for(int id : checkedIds){
            Chip chip = whenChips.findViewById(id);
            whenList.add(chip.getText().toString());
        }

        return mData.days+"일 마다"+ "하루 "+ mData.times +" 회씩\n"
                +TextUtils.join(", ",whenList);
    }

    @Override
    public void restoreStepData(PeriodHolder data) {
        mData = data;
        daysPicker.setValue(mData.days);
        timesPicker.setValue(mData.times);
        for(int i = 0; i< WhenManager.WHEN_FLAGS.length; i++){
            if( (mData.setAndGetFlag() & WhenManager.WHEN_FLAGS[i]) == WhenManager.WHEN_FLAGS[i]) {
                Chip chip = (Chip) whenChips.getChildAt(i);
                chip.setChecked(true);
            }
        }
    }

    @Override
    protected IsDataValid isStepDataValid(PeriodHolder stepData) {
        return new IsDataValid(true);
    }


    @Override
    protected void onStepOpened(boolean animated) {

    }

    @Override
    protected void onStepClosed(boolean animated) {

    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {

    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {

    }

    public class PeriodHolder {
        public int days, times, whenFlag =0x00;
        public ChipGroup whenChips;

        public PeriodHolder(int days, int times, ChipGroup whenChips) {
            this.days = days;
            this.times = times;
            this.whenChips = whenChips;
            setAndGetFlag();
        }
        public int setAndGetFlag(){
            List<Integer> checkedIds = whenChips.getCheckedChipIds();
            for(int id : checkedIds){
                whenFlag |= Integer.parseInt(whenChips.findViewById(id).getTag().toString());
            }
            return whenFlag;
        }
    }
}
