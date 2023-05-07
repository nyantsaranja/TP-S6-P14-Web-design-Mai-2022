package itu.s6.tpseo.framework.springutils.util.map;


import itu.s6.tpseo.framework.springutils.exception.CustomException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {


    public static Map<String, Object> convert(Object obj) throws CustomException {
        Map<String, Object> map = new HashMap<>();
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.isAnnotationPresent(IgnoreMapping.class)) {
                    map.put(field.getName(), field.get(obj));
                }
            }
        }
        catch (ReflectiveOperationException e) {
            throw new CustomException("internal error: "+e.getMessage(), e);
        }
        return map;
    }
}
