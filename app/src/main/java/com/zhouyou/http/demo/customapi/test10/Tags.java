package com.zhouyou.http.demo.customapi.test10;

public class Tags {
    private String name;
    private String url;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
