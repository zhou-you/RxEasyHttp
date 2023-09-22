package com.zhouyou.http.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigDecimal;

class BigDecimalTypeAdapter extends TypeAdapter<BigDecimal> {

    @Override
    public void write(JsonWriter out, BigDecimal value) throws IOException {
        out.value(value);
    }

    @Override
    public BigDecimal read(JsonReader in) throws IOException {

        if (in.peek().equals(JsonToken.NULL)) {
            in.skipValue();
            return BigDecimal.valueOf(0);
        }

        try {
            return BigDecimal.valueOf(Double.parseDouble(in.nextString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return BigDecimal.valueOf(0);
        } catch (IOException e) {
            e.printStackTrace();
            return BigDecimal.valueOf(0);
        }
    }
}
