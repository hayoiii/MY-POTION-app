package com.palebluedot.mypotion.util;

import java.util.ArrayList;
import java.util.List;

public class TagManager {
    private static TagManager instance = null;

    private TagManager() {}
    public static TagManager getInstance() {
        if(instance == null){
            instance = new TagManager();
        }
        return instance;
    }

    //'의' 지우기
    final String[] keywords = {
            "기억력",
            "혈행",
            "간건강", "간 건강",
            "체지방",
            "갱년기",
            "혈당",
            "눈",
            "면역기능", "면역 기능",
            "관절", "뼈",
            "전립선",
            "피로",
            "피부 건강", "피부건강",
            "콜레스테롤",
            "혈압",
            "긴장",
            "장건강", "장 건강",
            "배변",
            "칼슘흡수", "칼슘 흡수",
            "요로",
            "소화",
            "항산화",
            "중성지방", "중성 지방",
            "인지",
            "운동", "지구력",
            "치아",
            "배뇨",
            "과민반응","과민 반응",
            "월경",
            "정자 운동", "정자",
            "질건강", "질 건강",
            "키",
            "골다공증",
            "충치"
    };

    public List<String> extract(String effect){
        List<String> tags = new ArrayList<>();
        for(int i =0; i<keywords.length; i++){
            if(effect.contains(keywords[i]) && !tags.contains(keywords[i])){
                tags.add(Constant.TAGS_FOR_EXTRACT[i]);
            }
        }
        return tags;
    }

    public String toTagStyle(List<String> extractedTags) {
        String ret="";
        for(String tag : extractedTags) {
            ret+="#" + tag + " ";
        }
        return ret.trim();
    }
}
