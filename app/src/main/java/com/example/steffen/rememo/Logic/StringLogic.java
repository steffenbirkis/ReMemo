package com.example.steffen.rememo.Logic;

public class StringLogic {

    public StringLogic() {

    }

    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }

    public static String formatString(String string) {
        String result = string.toLowerCase();
        result = result.substring(0, 1).toUpperCase() + result.substring(1);
        char c;
        for (int i = 0; i < result.length(); i++) {
            c = string.charAt(i);
            if (c == ' ') {
                result = result.substring(0, i + 1) + result.substring(i + 1, i + 2).toUpperCase() + result.substring(i + 2);
            }
        }
        return result;
    }
}

