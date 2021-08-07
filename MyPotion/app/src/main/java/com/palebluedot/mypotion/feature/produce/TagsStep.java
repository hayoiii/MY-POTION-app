package com.palebluedot.mypotion.feature.produce;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.palebluedot.mypotion.R;
import com.palebluedot.mypotion.util.Constant;
import com.palebluedot.mypotion.util.TagManager;

import java.util.Arrays;
import java.util.LinkedList;

import ernestoyaquello.com.verticalstepperform.Step;

public class TagsStep extends Step<LinkedList<String>> {

    private LinkedList<String> tags;
    private View tagsStepContent;

    private MaterialAutoCompleteTextView autoCompleteText;
    ChipGroup checkedChips;
    private ChipGroup allChips;

    private boolean EDIT_MODE = false;


    public TagsStep(String title) {
        super(title);
        tags = new LinkedList<>();
    }

    public TagsStep(String title, String old) {
        super(title);
        EDIT_MODE = true;
        String[] tagsStrArray = old.split("#");
        // 공백 제거
        for(int i =1; i< tagsStrArray.length; i++){
            tagsStrArray[i] = tagsStrArray[i].trim();
        }
        tags = new LinkedList<>(Arrays.asList(tagsStrArray));
        tags.remove(0);
    }
    public void initTags(String effect){
        if(tags!=null)
            tags = new LinkedList<String>(TagManager.getInstance().extract(effect));
    }

    @NonNull
    @Override
    protected View createStepContentLayout() {

        // We create this step view by inflating an XML layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        tagsStepContent = inflater.inflate(R.layout.form_effect_tags, null, false);
        autoCompleteText = tagsStepContent.findViewById(R.id.add_tag_auto_text_view);
        checkedChips = tagsStepContent.findViewById(R.id.add_selected_chips);
        allChips = tagsStepContent.findViewById(R.id.add_chips_list);

        /*자동완성 검색창 초기화*/
        AutoCompleteAdapter adapter = new AutoCompleteAdapter();
        autoCompleteText.setAdapter(adapter);

        autoCompleteText.setThreshold(1);
        autoCompleteText.setOnItemClickListener((adapterView, view1, i, l) -> {
            String selectedTag = (String) adapterView.getItemAtPosition(i);
            if(selectedTag.charAt(0) == '#'){
                addCustomizedTag(selectedTag, false);
            }
            else
                addToCheckedChips(selectedTag);
            autoCompleteText.setText("");
        });

        /* 전체 태그 리스트 초기화 */
        int count = tags.size();
        for(int i = 0; i< Constant.TAGS.length; i++){
            Chip chip = new Chip(getContext());
            chip.setText(Constant.TAGS[i]);
            chip.setCloseIconVisible(false);
            chip.setTag(Constant.TAGS[i]);
            chip.setCheckable(true);
            chip.setChecked(false);

            //allChips에서 아이템 클릭 시
            chip.setOnClickListener(v -> {
                    addToCheckedChips(v.getTag().toString());
            });

            allChips.addView(chip);
            if(tags.contains(Constant.TAGS[i])) {
                addToCheckedChips(Constant.TAGS[i]);
                count--;
            }
        }
        //customized tag가 있을 때
        if(EDIT_MODE && count>0){
            int size = tags.size();
            for(int i =0; i<size; i++) {
                addCustomizedTag(tags.get(i), true);
            }
        }
        return tagsStepContent;
    }

    private void addCustomizedTag(String rawTag, boolean init) {
        String customizedTag = rawTag;
        if(!init)
            customizedTag = rawTag.substring(rawTag.indexOf("'")+1, rawTag.lastIndexOf("'"));

        Chip existingChip = ((Chip) allChips.findViewWithTag(customizedTag));
        if(existingChip != null)
            return;

        Chip newChip = new Chip(getContext());

        newChip.setCloseIconVisible(true);
        newChip.setText(customizedTag);
        newChip.setChipBackgroundColorResource(R.color.secondary_light);
        newChip.setTag(customizedTag);
        newChip.setCheckable(false);
        newChip.setOnCloseIconClickListener(v -> {
            Object chipTag = newChip.getTag();
            if(checkedChips.findViewWithTag(chipTag) !=null) {
                checkedChips.removeView(newChip);
                tags.remove((String)chipTag);
            }
        });

        checkedChips.addView(newChip, 0);
        if(!init)
            tags.add(customizedTag);
    }
    private void addToCheckedChips(String tag){
        Chip existingChip = ((Chip) checkedChips.findViewWithTag(tag));
        if(existingChip != null) {
            //이미 체크되어있는 칩이면 체크 취소
            removeFromCheckedChips(tag);
            return;
        }
        Chip newChip = new Chip(getContext());

        newChip.setCloseIconVisible(true);
        newChip.setText(tag);
        newChip.setTag(tag);
        newChip.setChipBackgroundColorResource(R.color.secondary_light);
        newChip.setCheckable(false);
        newChip.setOnCloseIconClickListener(v -> removeFromCheckedChips(newChip.getTag().toString()));
        checkedChips.addView(newChip, 0);

        //allChips에서 해당 chip을 checked 상태로 만들기
        //TODO: checked ui 변경
        ((Chip) allChips.findViewWithTag(tag)).setChecked(true);
        if(!tags.contains(tag))
            tags.add(tag);
    }
    void removeFromCheckedChips(String tag){
        Chip target = checkedChips.findViewWithTag(tag);
        checkedChips.removeView(target);

        //allChips에서 해당 chip을 unchecked 상태로 만들기
        ((Chip) allChips.findViewWithTag(tag)).setChecked(false);
        tags.remove(tag);
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
    public LinkedList<String> getStepData() {
        return tags;
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        int num = tags.size();
        if(num == 0)
            return getContext().getString(R.string.form_empty_field);

        String checkedTags= TagManager.getInstance().toTagStyle(tags);
        return checkedTags;
    }

    @Override
    public void restoreStepData(LinkedList<String> data) {
        tags = new LinkedList<>();
        for(int i = 0; i< Constant.TAGS.length; i++) {
            if (data.contains(Constant.TAGS[i])) {
                addToCheckedChips(Constant.TAGS[i]);
                data.remove(Constant.TAGS[i]);
            }
        }
        int num = data.size();
        for(int i=0; i< num; i++){
            addCustomizedTag(data.pop(), false);
        }
    }

    @Override
    protected IsDataValid isStepDataValid(LinkedList<String> stepData) {
        return new IsDataValid(true);
    }
}