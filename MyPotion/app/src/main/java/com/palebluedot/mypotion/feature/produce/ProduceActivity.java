package com.palebluedot.mypotion.feature.produce;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.palebluedot.mypotion.R;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;

public class ProduceActivity extends AppCompatActivity implements StepperFormListener {

    public static final int DB_SUCCESS = 213124;
    public static final int DB_CANCLED = 45945;

    private VerticalStepperFormView verticalStepperForm;
    private final String[] steps = {"별칭", "메모", "효과 태그", "시작 날짜", "복용 주기"};
    private final String[] mySteps = {"이름", "제조사", "메모", "효과 태그", "시작 날짜", "복용 주기"};  //for customizing

    private CustomNameStep customNameStep;
    private OptionalStep factoryStep;
    private PeriodStep periodStep;
    private BeginDateStep beginDateStep;
    private OptionalStep aliasStep;
    private OptionalStep memoStep;
    private TagsStep tagsStep;

    private int id;
    private String name;
    private String factory;
    private String effect;
    private String serialNo;

    private boolean EDIT_MODE = false;
    private boolean CUSTOM_MODE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produce);
//        Toolbar toolbar = findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        EDIT_MODE = intent.getBooleanExtra("EDIT_MODE", false);
        id = intent.getIntExtra("id", -1);

        name = intent.getStringExtra("product");
        factory = intent.getStringExtra("factory");
        effect = intent.getStringExtra("effect");
        serialNo = intent.getStringExtra("serialNo");

        if(EDIT_MODE){
            // TODO: get old data
        }
        if(!CUSTOM_MODE) {
            aliasStep = new OptionalStep(steps[0], OptionalStep.FORM_TYPE_ALIAS);
            aliasStep.setName(name);
            memoStep = new OptionalStep(steps[1], OptionalStep.FORM_TYPE_MEMO);
            tagsStep = new TagsStep(steps[2]);
            tagsStep.initTags(effect);
            beginDateStep = new BeginDateStep(steps[3]);
            periodStep = new PeriodStep(steps[4]);
        }
        else {
            customNameStep = new CustomNameStep(mySteps[0]);
            factoryStep = new OptionalStep(mySteps[1], OptionalStep.FORM_TYPE_FACTORY);
            memoStep = new OptionalStep(mySteps[2], OptionalStep.FORM_TYPE_MEMO);
            tagsStep = new TagsStep(mySteps[3]);
            beginDateStep = new BeginDateStep(mySteps[4]);
            periodStep = new PeriodStep(mySteps[5]);
        }

        verticalStepperForm = findViewById(R.id.stepper_form);
        verticalStepperForm
                .setup(this, aliasStep, memoStep, tagsStep, beginDateStep, periodStep)
                .init();
    }



//    @Override
//    public void onCompletedForm() {
//        String alias = aliasStep.getStepData();
//        if(alias.equals(""))
//            alias = name;
//        String memo = memoStep.getStepData();
//        String tags = tagsStep.getStepDataAsHumanReadableString();
//        if(tags.equals(getString(R.string.form_empty_field)))
//            tags = "";
//
//        int mDate[] = beginDateStep.getStepData();
//        GregorianCalendar calendar = new GregorianCalendar(mDate[0], mDate[1], mDate[2]);
//        String dateStr = DbContract.DATE_FORMAT.format(calendar.getTime());
//        PeriodStep.PeriodHolder periodHolder = periodStep.getStepData();
//
//        int days = periodHolder.days;
//        int times = periodHolder.times;
//        int whenFlag = periodHolder.whenFlag;
//
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DbContract.MyElixirEntry.COLUMN_NAME_PRODUCT, name);
//        values.put(DbContract.MyElixirEntry.COLUMN_NAME_FACTORY, factory);
//        values.put(DbContract.MyElixirEntry.COLUMN_NAME_SERIALNO, serialNo);
//
//        values.put(DbContract.MyElixirEntry.COLUMN_NAME_ALIAS, alias);
//        values.put(DbContract.MyElixirEntry.COLUMN_NAME_MEMO, memo);
//        values.put(DbContract.MyElixirEntry.COLUMN_NAME_TAGS, tags);
//        values.put(DbContract.MyElixirEntry.COLUMN_NAME_BEGIN, dateStr);
//        values.put(DbContract.MyElixirEntry.COLUMN_NAME_DAYS, days);
//        values.put(DbContract.MyElixirEntry.COLUMN_NAME_TIMES, times);
//        values.put(DbContract.MyElixirEntry.COLUMN_NAME_WHEN, whenFlag);
//
//        if(EDIT_MODE) {
//            long newRowId = db.insert(DbContract.MyElixirEntry.TABLE_NAME, null, values);
//            if(newRowId>0) {
//                showResultSAD(this, true, "새로운 엘릭서를 추가하였습니다.");
//                setResult(DB_SUCCESS);
//            }
//            else{
//                showResultSAD(this, false, "저장");
//                setResult(DB_CANCLED);
//                db.close();
//                return;
//            }
//        }
//        //update
//        else{
//            int num = db.update(DbContract.MyElixirEntry.TABLE_NAME, values, DbContract.MyElixirEntry._ID+"="+ id, null);
//            if(num>0) {
//                showResultSAD(this, true, "엘릭서를 수하였습니다.");
//                setResult(DB_SUCCESS);
//            }
//            else{
//                showResultSAD(this, false, "저장");
//                setResult(DB_CANCLED);
//                db.close();
//                return;
//            }
//        }
//        db.close();
//        finish();
//    }

    @Override
    public void onCompletedForm() {

    }

    // TODO : sweet alert
    @Override
    public void onCancelledForm() {
//        SweetAlertDialog cancleSAD =AlertUtil.createCancleSAD(this, null);
//        cancleSAD.show();
//        AlertUtil.setSAD(this, cancleSAD, R.color.warning_stroke_color);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onCancelledForm();
    }
}

