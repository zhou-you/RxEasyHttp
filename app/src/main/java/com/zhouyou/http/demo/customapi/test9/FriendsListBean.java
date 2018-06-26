package com.zhouyou.http.demo.customapi.test9;

public class FriendsListBean {
    private int uid;
    private String frined;
    private String headUrl;
    private String nick;

    public String getFrined() {
        return frined == null ? "" : frined;
    }

    public void setFrined(String frined) {
        this.frined = frined;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHeadUrl() {
        return headUrl == null ? "" : headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getNick() {
        return nick == null ? "" : nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "FriendsListBean{" +
                "uid=" + uid +
                ", frined='" + frined + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }
}
