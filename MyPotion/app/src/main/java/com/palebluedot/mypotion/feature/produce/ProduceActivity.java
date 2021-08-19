package com.palebluedot.mypotion.feature.produce;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.data.model.MyPotion;
import com.palebluedot.mypotion.data.repository.mypotion.MyPotionRepository;
import com.palebluedot.mypotion.data.repository.results.SearchResultsRepository;
import com.palebluedot.mypotion.util.Constant;
import com.palebluedot.mypotion.util.MyCode;
import com.palebluedot.mypotion.util.MyUtil;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;

public class ProduceActivity extends AppCompatActivity implements StepperFormListener {
    private MyPotionRepository repository;

    private VerticalStepperFormView verticalStepperForm;
    private final String[] steps = {"포션 이름", "메모", "효과 태그", "시작 날짜", "복용 주기"};
    private final String[] mySteps = {"포션 이름", "제조사", "메모", "효과 태그", "시작 날짜", "복용 주기"};  //for customizing

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
        repository = new MyPotionRepository(this);
        setContentView(R.layout.activity_produce);
//        Toolbar toolbar = findViewById(R.id.main_toolbar);
//        setSupportActionBar(toolbar);
        verticalStepperForm = findViewById(R.id.stepper_form);

        Intent intent = getIntent();
        EDIT_MODE = intent.getBooleanExtra("EDIT_MODE", false);
        CUSTOM_MODE = intent.getBooleanExtra("CUSTOM_MODE", false);

        /* null when CUSTOM_MODE */
        id = intent.getIntExtra("id", -1);
        name = intent.getStringExtra("name");
        factory = intent.getStringExtra("factory");
        effect = intent.getStringExtra("effect");
        serialNo = intent.getStringExtra("serialNo");

        if(EDIT_MODE){
            // TODO: get old data
        }

        aliasStep = new OptionalStep(steps[0], OptionalStep.FORM_TYPE_ALIAS);
        aliasStep.setName(name);
        memoStep = new OptionalStep(steps[1], OptionalStep.FORM_TYPE_MEMO);
        tagsStep = new TagsStep(steps[2]);

        if(!CUSTOM_MODE)
            tagsStep.initTags(effect);
        beginDateStep = new BeginDateStep(steps[3]);
        periodStep = new PeriodStep(steps[4]);

        if(CUSTOM_MODE) {
            //aliasStep 대신 customNameStep 사용
            //factoryStep 추가
            customNameStep = new CustomNameStep(mySteps[0]);
            factoryStep = new OptionalStep(mySteps[1], OptionalStep.FORM_TYPE_FACTORY);

            verticalStepperForm
                    .setup(this, customNameStep, factoryStep, aliasStep, memoStep, tagsStep, beginDateStep, periodStep)
                    .init();
        }
        else {
            verticalStepperForm
                    .setup(this, aliasStep, memoStep, tagsStep, beginDateStep, periodStep)
                    .init();
        }
    }

    @Override
    public void onCompletedForm() {
        String alias = aliasStep.getStepData();
        if(CUSTOM_MODE) {
            name = customNameStep.getStepData();
            factory = factoryStep.getStepData();
        }

        if (alias.equals(""))
            alias = name;

        String memo = memoStep.getStepData();
        List<String> tags = tagsStep.getStepData();

        int mDate[] = beginDateStep.getStepData();
        GregorianCalendar calendar = new GregorianCalendar(mDate[0], mDate[1], mDate[2]);
        String dateStr = Constant.DATE_FORMAT.format(calendar.getTime());
        PeriodStep.PeriodHolder periodHolder = periodStep.getStepData();

        int days = periodHolder.days;
        int times = periodHolder.times;
        int whenFlag = periodHolder.whenFlag;


        MyPotion potion = new MyPotion(serialNo, alias, name, factory, dateStr, null, tags, memo, days, times, whenFlag);
        repository.insert(potion);

        finishActivity(MyCode.PRODUCE_COMPLETE);
    }

    // TODO : sweet alert
    @Override
    public void onCancelledForm() {
//        SweetAlertDialog cancleSAD = AlertUtil.createCancleSAD(this, null);
//        cancleSAD.show();
//        AlertUtil.setSAD(this, cancleSAD, R.color.warning_stroke_color);

        finishActivity(MyCode.PRODUCE_CANCEL);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onCancelledForm();
    }
}

