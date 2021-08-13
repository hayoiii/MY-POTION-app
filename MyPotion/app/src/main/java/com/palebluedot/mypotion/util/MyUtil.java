package com.palebluedot.mypotion.util;

import java.text.ParseException;
import java.util.ArrayList;
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

    public static Date stringToDate(String formattedStr){
        Date date = null;
        try {
            date = Constant.DATE_FORMAT.parse(formattedStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
