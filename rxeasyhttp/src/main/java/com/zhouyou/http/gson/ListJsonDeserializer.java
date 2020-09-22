package com.zhouyou.http.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

public class ListJsonDeserializer implements JsonDeserializer<List> {

    @Override
    public List deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonArray()) {
            return ZGsonBuilder.gsonBuilder().create().fromJson(json, typeOfT);
        }
        return null;
    }
}
