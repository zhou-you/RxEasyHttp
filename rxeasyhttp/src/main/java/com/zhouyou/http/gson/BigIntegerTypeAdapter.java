package com.zhouyou.http.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigInteger;

class BigIntegerTypeAdapter extends TypeAdapter<BigInteger> {

    @Override
    public void write(JsonWriter out, BigInteger value) throws IOException {
        out.value(value);
    }

    @Override
    public BigInteger read(JsonReader in) throws IOException {

        if (in.peek().equals(JsonToken.NULL)) {
            in.skipValue();
            return BigInteger.valueOf(0);
        }

        try {
            return new BigInteger(in.nextString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return BigInteger.valueOf(0);
        } catch (IOException e) {
            e.printStackTrace();
            return BigInteger.valueOf(0);
        }
    }
}
