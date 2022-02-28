package com.github.unldenis.packetsender.util;


import java.lang.reflect.*;

public class ReflectionUtils {

    public static Object[] getTypeArgument(Field field) {
        return getTypeArgument(field.getGenericType(), 0);
    }

    private static Object[] getTypeArgument(Type genericType, int j) {
        if (genericType instanceof ParameterizedType) {
            // types with parameters
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] typeArgs = parameterizedType.getActualTypeArguments();
            for (Type typeArg : typeArgs) {
                // note: recursive call
                return getTypeArgument(typeArg, j + 1);
            }
        } else if(genericType instanceof Class<?>) {
            final Object[] r = new Object[j];
            r[0] = (Class<?>) genericType;
            return r;
        }
        return null;
    }

    public static Object getUncheckedField(Field field) {
        try {
            return field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
