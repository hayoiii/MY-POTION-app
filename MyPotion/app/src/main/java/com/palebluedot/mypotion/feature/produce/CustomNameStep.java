package com.palebluedot.mypotion.feature.produce;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.repository.RepositoryCallback;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionRepository;

import ernestoyaquello.com.verticalstepperform.Step;

public class CustomNameStep extends Step<String> {
    private static final int MIN_CHARACTERS_ALARM_NAME = 2;
    private MyPotionRepository repository;

    private TextInputEditText nameEditText;
    private String minCharacterErrorString;
    private String duplicationErrorString;
    private String old = null;
    private boolean EDIT_MODE = false;
    boolean isDuplicated = false;

    public CustomNameStep(String title) {
        super(title);
    }

    public void setOld(String old) {
        this.old = old;
        EDIT_MODE = true;
    }

    @NonNull
    @Override
    protected View createStepContentLayout() {
        repository = new MyPotionRepository(getContext());
        // We create this step view programmatically
        nameEditText = new TextInputEditText(getContext());

//        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "font/r_nanumbarunpen.ttf");
//        nameEditText.setTypeface(typeFace);

        if(EDIT_MODE)
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

        minCharacterErrorString = getContext().getResources().getString(R.string.form_error_min_characters);
        duplicationErrorString = getContext().getResources().getString(R.string.form_error_potion_duplication);

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
        return name.equals("")
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
            if(EDIT_MODE && old.equals(stepData))
                return new IsDataValid(true);
            if(repository.isDuplicatedAlias(getStepData()))
                return new IsDataValid(false, duplicationErrorString);
        }
        return new IsDataValid(true);
    }
    RepositoryCallback<Boolean> callback = new RepositoryCallback<Boolean>() {
        @Override
        public void onComplete(Boolean result) {
            isDuplicated = result;
            isStepDataValid(getStepData());
        }
    };

}
