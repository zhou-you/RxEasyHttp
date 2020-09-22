package com.zhouyou.http.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FloatTypeAdapter extends TypeAdapter<Float> {

    @Override
    public void write(JsonWriter out, Float value) throws IOException {
        out.value(value);
    }

    @Override
    public Float read(JsonReader in) throws IOException {
        if (in.peek().equals(JsonToken.NULL)) {
            in.skipValue();
            return 0f;
        }
        try {
            return Float.parseFloat(in.nextString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0f;
        } catch (IOException e) {
            e.printStackTrace();
            return 0f;
        }
    }
}
