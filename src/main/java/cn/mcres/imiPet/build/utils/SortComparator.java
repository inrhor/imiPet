package cn.mcres.imiPet.build.utils;

import java.util.Comparator;
import com.google.gson.JsonObject;

public class SortComparator implements Comparator<JsonObject> {

    private String sortItem;

    public SortComparator(String sortItem) {
        this.sortItem = sortItem;
    }

    @Override
    public int compare(JsonObject o1, JsonObject o2) {
        String value1 = o1.getAsJsonObject().get("predicate").getAsJsonObject().get(sortItem).getAsString();
        String value2 = o2.getAsJsonObject().get("predicate").getAsJsonObject().get(sortItem).getAsString();
        int int1 = Integer.parseInt(value1);
        int int2 = Integer.parseInt(value2);
        if (int1 == int2) return 0;
        return int1 - int2;
    }
}
