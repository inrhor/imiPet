package cn.mcres.imiPet.entity;

import java.lang.reflect.Field;

public class Utils {
    public static Object getPrivateField(String fieldName, Class clazz, Object object) {
        Field field;
        Object obj = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            obj = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
