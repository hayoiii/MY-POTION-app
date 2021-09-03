package com.palebluedot.mypotion.feature.produce;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.repository.RepositoryCallback;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionRepository;

import ernestoyaquello.com.verticalstepperform.Step;

public class OptionalStep extends Step<String> {
    private MyPotionRepository repository;

    private TextInputEditText editText;
    public final static int FORM_TYPE_MEMO = 0;
    public final static int FORM_TYPE_FACTORY = 1;
    public final static int FORM_TYPE_ALIAS = 2;
    private InputMethodManager imm;
    private String duplicationErrorString;

    private int type=-1;
    private boolean EDIT_MODE = false;
    private String old = null;
    private String data = null;
    boolean isDuplicated = false;

    public OptionalStep(String title, int type){
        super(title);
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }
    public void setOld(@NonNull String old){
        this.old = old;
        this.EDIT_MODE = true;
    }

    @NonNull
    @Override
    protected View createStepContentLayout() {
        repository = new MyPotionRepository(getContext());
        // We create this step view programmatically
        editText = new TextInputEditText(getContext());
//        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "font/r_nanumbarunpen.ttf");
//        editText.setTypeface(typeFace);
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if(EDIT_MODE)
            editText.setText(old);

        if(type == FORM_TYPE_MEMO) {
            editText.setHint(R.string.form_hint_memo);
            editText.setSingleLine(false);
        }
        else if(type == FORM_TYPE_FACTORY){
            editText.setHint(R.string.form_hint_factory);
            editText.setSingleLine(true);
        }
        else if(type == FORM_TYPE_ALIAS){
            editText.setHint(R.string.form_hint_alias);
            editText.setSingleLine(true);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    markAsCompletedOrUncompleted(true);
                }
                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        editText.setOnEditorActionListener((v, actionId, event) -> {
            getFormView().goToNextStep(true);
            return false;
        });
        duplicationErrorString = getContext().getResources().getString(R.string.form_error_potion_duplication);

        return editText;
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
        Editable text = editText.getText();
        if (text != null) {
            return text.toString();
        }
        return "";
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String description = getStepData();
        if(type == FORM_TYPE_ALIAS){
            return description.equals("")
                    ? data
                    : description;
        }

        return description.equals("")
                ? getContext().getString(R.string.form_empty_field)
                : description;
    }

    @Override
    public void restoreStepData(String data) {
        if (editText != null) {
            editText.setText(data);
        }
    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        if(type == FORM_TYPE_ALIAS){
            if(EDIT_MODE) {
                if(old != null && old.equals(stepData))
                    return new IsDataValid(true);
            }
            else if(stepData.equals("") && data != null)
                stepData = data;
                // 중복 확인
                if(repository.isDuplicatedAlias(stepData)){
                    return new IsDataValid(false, duplicationErrorString);
                }
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