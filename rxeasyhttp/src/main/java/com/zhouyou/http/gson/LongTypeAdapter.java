package com.zhouyou.http.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class LongTypeAdapter extends TypeAdapter<Long> {

    @Override
    public void write(JsonWriter out, Long value) throws IOException {
        out.value(value);
    }

    @Override
    public Long read(JsonReader in) throws IOException {
        if (in.peek().equals(JsonToken.NULL)) {
            in.skipValue();
            return 0L;
        }

        try {
            return Long.parseLong(in.nextString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0L;
        } catch (IOException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
