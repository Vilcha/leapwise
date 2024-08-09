package com.vilim.leapwise.utils;

import com.vilim.leapwise.model.request.CustomerRequest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class JsonUtil {

    private JsonUtil() {
    }

    public static Map<String, String> toFlatMap(final CustomerRequest customer) throws IllegalAccessException {
        Map<String, String> flatMap = new HashMap<>();
        flattenObject("", customer, flatMap);
        return flatMap;
    }

    private static void flattenObject(final String prefix, final Object customer, final Map<String, String> flatMap)
            throws IllegalAccessException {
        Class<?> clazz = customer.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(customer);
            String key = (prefix.isEmpty() ? "customer." : prefix + ".") + field.getName();

            if (value == null) {
                flatMap.put(key, null);
            } else {
                if (isWrapperOrString(value)) {
                    flatMap.put(key, value.toString());
                } else {
                    flattenObject(key, value, flatMap);
                }
            }
        }
    }

    private static boolean isWrapperOrString(final Object value) {
        Class<?> clazz = value.getClass();
        return clazz == Boolean.class || clazz == Byte.class || clazz == Character.class
                || clazz == Double.class || clazz == Float.class || clazz == Integer.class
                || clazz == Long.class || clazz == Short.class || clazz == String.class;
    }
}
