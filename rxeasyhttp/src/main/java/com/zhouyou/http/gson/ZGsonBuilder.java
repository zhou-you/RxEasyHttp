package com.zhouyou.http.gson;

import com.google.gson.GsonBuilder;
import com.zhouyou.http.utils.Utils;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

public class ZGsonBuilder {

    public static <T> GsonBuilder gsonBuilder() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new ZTypeAdapterFactory())
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls();
    }

    public static <T> GsonBuilder gsonBuilder2(Type type) {
        final Class<T> clazz = Utils.getClass(type, 0);
        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls();
        if (clazz.isAssignableFrom(List.class)) {
            gsonBuilder.registerTypeHierarchyAdapter(clazz, new ListJsonDeserializer());
        } else if (clazz.equals(Integer.class) || clazz.equals(int.class)
                || clazz.equals(Boolean.class) || clazz.equals(boolean.class)
                || clazz.equals(Byte.class) || clazz.equals(byte.class)
                || clazz.equals(Character.class) || clazz.equals(char.class)
                || clazz.equals(Double.class) || clazz.equals(double.class)
                || clazz.equals(Float.class) || clazz.equals(float.class)
                || clazz.equals(Long.class) || clazz.equals(long.class)
                || clazz.equals(Short.class) || clazz.equals(short.class)
                || clazz.equals(String.class)
        ) {
            gsonBuilder.registerTypeAdapterFactory(new ZTypeAdapterFactory());
        } else {
            gsonBuilder.registerTypeHierarchyAdapter(clazz, new ObjectJsonDeserializer<T>());
        }
        return gsonBuilder;
    }

}
