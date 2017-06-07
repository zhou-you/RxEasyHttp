package com.zhouyou.http.demo.constant;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class AppConstant {

    /**
     * AppId
     **/
    public static String APPID = "10101";

    /**
     * AppSecret
     **/
    public static String APP_SECRET = "afd55f877bad4aaeab45fb4ca567d234";

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"data\": 1468993603787,\n" +
                "    \"code\": 100010104,\n" +
                "    \"msg\": \"timestamp过期\"\n" +
                "}";

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map ss = gson.fromJson(json, type);
        System.out.println(ss);
    }
}
