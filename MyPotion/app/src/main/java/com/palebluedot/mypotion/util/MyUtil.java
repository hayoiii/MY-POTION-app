package com.palebluedot.mypotion.util;

import java.util.ArrayList;

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
}
