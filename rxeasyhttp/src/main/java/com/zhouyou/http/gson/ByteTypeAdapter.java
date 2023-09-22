package com.zhouyou.http.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ByteTypeAdapter extends TypeAdapter<Byte> {

    @Override
    public void write(JsonWriter out, Byte value) throws IOException {
        out.value(value);
    }

    @Override
    public Byte read(JsonReader in) throws IOException {
        if (in.peek().equals(JsonToken.NULL)) {
            in.skipValue();
            return 0;
        }

        try {
            return Byte.valueOf(in.nextString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
