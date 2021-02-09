package cn.mcres.imiPet.build.utils;

import java.util.Arrays;
import java.util.List;

public class StringListChange {
    public static String listToString(List<String> list) {
        return String.join(",", list);
    }

    public static List<String> stringToList(String string) {
        return Arrays.asList(string.split(","));
    }
}
