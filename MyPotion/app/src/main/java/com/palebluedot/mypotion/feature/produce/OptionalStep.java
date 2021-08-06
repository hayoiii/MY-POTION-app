package com.palebluedot.mypotion.feature.produce;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.palebluedot.mypotion.R;

import ernestoyaquello.com.verticalstepperform.Step;

public class OptionalStep extends Step<String> {

    private TextInputEditText editText;
    public final static int FORM_TYPE_MEMO = 0;
    public final static int FORM_TYPE_FACTORY = 1;
    public final static int FORM_TYPE_ALIAS = 2;
    private InputMethodManager imm;
    private String duplicationErrorString;

    private int type=-1;
    private boolean isNew = true;
    private String old = null;
    private String product = null;

    public OptionalStep(String title, String old, int type) {
        super(title);
        this.type = type;
        this.old = old;
        this.isNew = false;
    }
    public OptionalStep(String title, int type){
        super(title);
        this.type = type;
    }
    public void setName(String product){
        this.product = product;
    }

    @NonNull
    @Override
    protected View createStepContentLayout() {
        // We create this step view programmatically
        editText = new TextInputEditText(getContext());
//        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "font/r_nanumbarunpen.ttf");
//        editText.setTypeface(typeFace);
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if(!isNew)
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
        duplicationErrorString = getContext().getResources().getString(R.string.form_error_custom_name_duplication);

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
            return description == null || description.isEmpty()
                    ? product
                    : description;
        }

        return description == null || description.isEmpty()
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
            if(!isNew && old.equals(stepData))
                return new IsDataValid(true);

            if(stepData.equals("") && product!=null)
                stepData = product;
                //TODO: 중복 확인
//                Cursor c = dbHelper.getReadableDatabase().rawQuery("SELECT " + DbContract.MyElixirEntry.COLUMN_NAME_ALIAS + " FROM " + DbContract.MyElixirEntry.TABLE_NAME +
//                        " WHERE " + DbContract.MyElixirEntry.COLUMN_NAME_ALIAS + "='" + stepData +"'", null);
//                if(c.moveToFirst()){
//                    return new IsDataValid(false, duplicationErrorString);
//                }
            }
        return new IsDataValid(true);
    }
}