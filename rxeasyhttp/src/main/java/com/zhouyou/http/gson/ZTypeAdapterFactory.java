package com.zhouyou.http.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.math.BigInteger;

class ZTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType.equals(Integer.class) || rawType.equals(int.class)) {
            return (TypeAdapter<T>) new IntegerTypeAdapter();
        } else if (rawType.equals(Float.class) || rawType.equals(float.class)) {
            return (TypeAdapter<T>) new FloatTypeAdapter();
        } else if  (rawType.equals(Double.class) || rawType.equals(double.class) || rawType.equals(Number.class)) {
            return (TypeAdapter<T>) new DoubleTypeAdapter();
        } else if  (rawType.equals(Boolean.class) || rawType.equals(boolean.class)) {
            return (TypeAdapter<T>) new BoolTypeAdapter();
        } else if  (rawType.equals(Character.class) || rawType.equals(char.class)) {
            return (TypeAdapter<T>) new ChatTypeAdapter();
        } else if  (rawType.equals(Byte.class) || rawType.equals(byte.class)) {
            return (TypeAdapter<T>) new ByteTypeAdapter();
        } else if  (rawType.equals(Long.class) || rawType.equals(long.class)) {
            return (TypeAdapter<T>) new LongTypeAdapter();
        } else if  (rawType.equals(Short.class) || rawType.equals(short.class)) {
            return (TypeAdapter<T>) new ShortTypeAdapter();
        } else if  (rawType.equals(String.class)) {
            return (TypeAdapter<T>) new StringTypeAdapter();
        } else if (rawType.equals(BigDecimal.class)) {
            return (TypeAdapter<T>) new BigDecimalTypeAdapter();
        } else if (rawType.equals(BigInteger.class)) {
            return (TypeAdapter<T>) new BigIntegerTypeAdapter();
        }
//        else if  (rawType.equals(List.class)) {
//            return (TypeAdapter<T>) new ListTypeAdapter();
//        }
//        else {
//            return new ObjectTypeAdapter(rawType);
//        }
        return null;
    }
}
