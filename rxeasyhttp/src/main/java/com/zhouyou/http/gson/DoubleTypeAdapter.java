package com.zhouyou.http.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DoubleTypeAdapter extends TypeAdapter<Double> {

    @Override
    public void write(JsonWriter out, Double value) throws IOException {
        out.value(value);
    }

    @Override
    public Double read(JsonReader in) throws IOException {
        if (in.peek().equals(JsonToken.NULL)) {
            in.skipValue();
            return 0d;
        }

        try {
            return Double.parseDouble(in.nextString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0d;
        } catch (IOException e) {
            e.printStackTrace();
            return 0d;
        }
    }
}
