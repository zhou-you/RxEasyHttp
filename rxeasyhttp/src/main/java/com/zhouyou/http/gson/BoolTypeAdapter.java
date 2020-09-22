package com.zhouyou.http.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class BoolTypeAdapter extends TypeAdapter<Boolean> {

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        out.value(value);
    }

    @Override
    public Boolean read(JsonReader in) throws IOException {
        if (in.peek().equals(JsonToken.NULL)) {
            in.skipValue();
            return false;
        }

        try {
            return in.nextBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
