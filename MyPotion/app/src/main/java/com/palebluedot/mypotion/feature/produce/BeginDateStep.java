package com.palebluedot.mypotion.feature.produce;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.util.Constant;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ernestoyaquello.com.verticalstepperform.Step;

public class BeginDateStep extends Step<int[]> {

    private static final int DEFAULT_HOURS = 8;
    private static final int DEFAULT_MINUTES = 30;

    private DatePicker datePicker;
    private int mDate[] = new int[3];
    private boolean EDIT_MODE = false;
    private String old = null;

    public BeginDateStep(String title) {
        super(title);
    }

    public BeginDateStep(String title, String old) {
        super(title);
        this.old = old;
        this.EDIT_MODE = true;
    }

    @NonNull
    @Override
    protected View createStepContentLayout() {

        // We create this step view by inflating an XML layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View beginDateStepContent = inflater.inflate(R.layout.form_date_picker, null, false);
        datePicker = beginDateStepContent.findViewById(R.id.form_date_picker);
        Calendar today = new GregorianCalendar();

        if(!EDIT_MODE) {
            mDate[0] = today.get(Calendar.YEAR);
            mDate[1] = today.get(Calendar.MONTH);
            mDate[2] = today.get(Calendar.DAY_OF_MONTH);
        }
        else{
            try {
                Date beginDate = Constant.DATE_FORMAT.parse(old);
                Calendar beginCal = new GregorianCalendar();
                beginCal.setTimeInMillis(beginDate.getTime());
                mDate[0] = beginCal.get(Calendar.YEAR);
                mDate[1] = beginCal.get(Calendar.MONTH);
                mDate[2] = beginCal.get(Calendar.DAY_OF_MONTH);
                datePicker.init(mDate[0], mDate[1], mDate[2], mOnDateChangedListener);
            } catch (ParseException e) {
                e.printStackTrace();
                Calendar calendar = new GregorianCalendar();
                mDate[0] = calendar.get(Calendar.YEAR);
                mDate[1] = calendar.get(Calendar.MONTH);
                mDate[2] = calendar.get(Calendar.DAY_OF_MONTH);
            }
        }

        Calendar minDate = new GregorianCalendar();
        minDate.add(Calendar.YEAR, -1);
        datePicker.setMinDate(minDate.getTimeInMillis());
        datePicker.setOnDateChangedListener(mOnDateChangedListener);

        return beginDateStepContent;
    }

    @Override
    protected void onStepOpened(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepClosed(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {
        // No need to do anything here
    }

    @Override
    public int[] getStepData() {
        return mDate;
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        GregorianCalendar calendar = new GregorianCalendar(mDate[0], mDate[1], mDate[2]);
        String dateStr = Constant.DATE_FORMAT.format(calendar.getTime());
        return dateStr;
    }

    @Override
    public void restoreStepData(int[] data) {
        mDate = data;
        datePicker.init(mDate[0], mDate[1], mDate[2], mOnDateChangedListener);

    }

    @Override
    protected IsDataValid isStepDataValid(int[] stepData) {
        return new IsDataValid(true);
    }

    DatePicker.OnDateChangedListener mOnDateChangedListener = (datePicker, yy, mm, dd) -> {
        mDate[0] = yy;
        mDate[1] = mm;
        mDate[2] = dd;
    };
}