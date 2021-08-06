package com.palebluedot.mypotion.feature.produce;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.palebluedot.mypotion.R;

import ernestoyaquello.com.verticalstepperform.Step;

public class AliasStep extends Step<String> {
    private static final int MIN_CHARACTERS_ALARM_NAME = 1;

    private TextInputEditText nameEditText;
    private String minCharacterErrorString;
    private String duplicationErrorString;
    private String old = null;
    private boolean isNew = true;

    public AliasStep(String title) {
        super(title);
    }

    public AliasStep(String title, String old) {
        super(title);
        this.old = old;
        this.isNew = false;
    }

    @NonNull
    @Override
    protected View createStepContentLayout() {
        // We create this step view programmatically
        nameEditText = new TextInputEditText(getContext());

        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "font/r_nanumbarunpen.ttf");
        nameEditText.setTypeface(typeFace);

        if(!isNew)
            nameEditText.setText(old);

        nameEditText.setHint(R.string.form_hint_name);
        nameEditText.setSingleLine(true);

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                markAsCompletedOrUncompleted(true);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        nameEditText.setOnEditorActionListener((v, actionId, event) -> {
            getFormView().goToNextStep(true);
            return false;
        });

        duplicationErrorString = getContext().getResources().getString(R.string.form_error_custom_name_duplication);

        return nameEditText;
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
    public String getStepData() {
        Editable text = nameEditText.getText();
        if (text != null) {
            return text.toString();
        }
        return "";
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String name = getStepData();
        return name == null || name.isEmpty()
                ? getContext().getString(R.string.form_empty_field)
                : name;
    }

    @Override
    public void restoreStepData(String data) {
        if (nameEditText != null) {
            nameEditText.setText(data);
        }
    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        if (stepData.length() < MIN_CHARACTERS_ALARM_NAME) {
            String titleError = String.format(minCharacterErrorString, MIN_CHARACTERS_ALARM_NAME);
            return new IsDataValid(false, titleError);
        } else {
            if(!isNew && old.equals(stepData))
                return new IsDataValid(true);
            //TODO: 중복 확인
//            Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT " + DbContract.MyElixirEntry.COLUMN_NAME_ALIAS + " FROM " + DbContract.MyElixirEntry.TABLE_NAME +
//                    " WHERE " + DbContract.MyElixirEntry.COLUMN_NAME_ALIAS + "='" + stepData +"'", null);
//            if(c.moveToFirst()){
//                return new IsDataValid(false, duplicationErrorString);
//            }
        }
        return new IsDataValid(true);
    }


}
