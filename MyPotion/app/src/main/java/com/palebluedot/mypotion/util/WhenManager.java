package com.palebluedot.mypotion.util;

import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class WhenManager {
    public static final int WHEN_1_BREAKFAST = 0x01;
    public static final int WHEN_2_AM = 0x02;
    public static final int WHEN_3_LUNCH = 0x04;
    public static final int WHEN_4_PM = 0x08;
    public static final int WHEN_5_DINNER = 0x10;
    public static final int WHEN_6_NIGHT = 0x20;

    public static final String BREAKFAST_BEGIN = "breakfast_begin";
    public static final String BREAKFAST_END = "breakfast_end";
    public static final String AM_END = "am_end";
    public static final String LUNCH_END = "lunch_end";
    public static final String PM_END = "pm_end";
    public static final String DINNER_END = "dinner_end";

    public static final int[] WHEN_FLAGS = {
            WHEN_1_BREAKFAST,
            WHEN_2_AM,
            WHEN_3_LUNCH,
            WHEN_4_PM,
            WHEN_5_DINNER,
            WHEN_6_NIGHT
    };
    public static final String[] WHEN_SP_KEYS = {
            BREAKFAST_END,
            AM_END,
            LUNCH_END,
            PM_END,
            DINNER_END
    };

    public static final Map<String, Integer> WHEN_SP_DEFAULT = new HashMap<String, Integer>() {
        {
            put(BREAKFAST_BEGIN, 6);
            put(BREAKFAST_END, 8);
            put(AM_END, 11);
            put(LUNCH_END, 14);
            put(PM_END, 17);
            put(DINNER_END, 20);
        }
    };

    public static final String SharedPrefFile = "com.palebluedot.SharedPreferences";

    public static String getWhenText(int flag) {
        String value = "";
        switch (flag) {
            case WHEN_1_BREAKFAST:
                value = "아침";
                break;
            case WHEN_2_AM:
                value = "오전";
                break;
            case WHEN_3_LUNCH:
                value = "점심";
                break;
            case WHEN_4_PM:
                value = "오후";
                break;
            case WHEN_5_DINNER:
                value = "저녁";
                break;
            case WHEN_6_NIGHT:
                value = "밤";
                break;
            default:
                value = "?";
        }
        return value;
    }

    public static int getWhenValue(SharedPreferences preferences, int hour_24) {
        int m = preferences.getInt(BREAKFAST_END, 8);
        int a = preferences.getInt(AM_END, 11);
        int l = preferences.getInt(LUNCH_END, 14);
        int p = preferences.getInt(PM_END, 17);
        int d = preferences.getInt(DINNER_END, 20);

        if (hour_24 >= 6 && hour_24 < m) {
            return WHEN_FLAGS[0];
        } else if (hour_24 >= m && hour_24 < a) {
            return WHEN_FLAGS[1];
        } else if (hour_24 >= a && hour_24 < l) {
            return WHEN_FLAGS[2];
        } else if (hour_24 >= l && hour_24 < p) {
            return WHEN_FLAGS[3];
        } else if (hour_24 >= p && hour_24 < d) {
            return WHEN_FLAGS[4];
        } else if (hour_24 >= d && hour_24 <= 24) {
            return WHEN_FLAGS[5];
        } else
            return 0;
    }

    public static int now(SharedPreferences preferences) {
        Calendar now = new GregorianCalendar();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int m = preferences.getInt("morningEnd", 8);
        int a = preferences.getInt("amEnd", 11);
        int l = preferences.getInt("lunchEnd", 14);
        int p = preferences.getInt("pmEnd", 17);
        int d = preferences.getInt("dinnerEnd", 20);

        if(hour>=6 && hour<m){
            return WHEN_FLAGS[0];
        }
        else if(hour>=m && hour < a){
            return WHEN_FLAGS[1];
        }
        else if(hour>=a && hour < l){
            return WHEN_FLAGS[2];
        }
        else if(hour>=l && hour < p){
            return WHEN_FLAGS[3];
        }
        else if(hour>=p && hour < d){
            return WHEN_FLAGS[4];
        }
        else if(hour>=d && hour <= 24){
            return WHEN_FLAGS[5];
        }
        else
            return 0;
    }
}
