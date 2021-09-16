package com.palebluedot.mypotion.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyUtil {
    public static ArrayList<String> splitByComma(String raw) {
        ArrayList<String> materials = new ArrayList<>();

        int depth = 0;
        int startIndex = 0;
        int curr = 0;

        for (char c : raw.toCharArray()) {
            if (curr == raw.length()-1) {
                materials.add(raw.substring(startIndex).trim());
                break;
            }
            if (c == ',' && depth == 0) {
                materials.add(raw.substring(startIndex, curr).trim());
                startIndex = curr + 1;
            }
            else if (c == '(') {
                depth++;
            }
            else if (c == ')') {
                depth--;
            }
            curr++;
        }

        return materials;
    }

    public static Date getFormattedToday(){
        Date today = new Date();
        String str = Constant.DATE_FORMAT.format(today);
        try {
            today = Constant.DATE_FORMAT.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return today;
    }

    public static String dateToString(Date date) {
        String dateStr = Constant.DATE_FORMAT.format(date);
        return dateStr;
    }

    public static String dateToTimeString(Date datetime){
        return Constant.TIME_FORMAT.format(datetime);
    }

    public static Date stringToDate(String formattedStr){
        Date date = null;
        try {
            date = Constant.DATE_FORMAT.parse(formattedStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    //dp를 px로 변환 (dp를 입력받아 px을 리턴)
    public static float dpToPx(int dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    //px을 dp로 변환 (px을 입력받아 dp를 리턴)
    public static float pxToDp(int px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
