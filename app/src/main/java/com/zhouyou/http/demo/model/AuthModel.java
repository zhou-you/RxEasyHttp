package com.zhouyou.http.demo.model;

import java.io.Serializable;

public class AuthModel implements Serializable {
    private String accessToken;
    private String accessTokenExpires;
    private String refreshToken;
    private String refreshTokenExpires;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenExpires() {
        return accessTokenExpires;
    }

    public void setAccessTokenExpires(String accessTokenExpires) {
        this.accessTokenExpires = accessTokenExpires;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshTokenExpires() {
        return refreshTokenExpires;
    }

    public void setRefreshTokenExpires(String refreshTokenExpires) {
        this.refreshTokenExpires = refreshTokenExpires;
    }

    @Override
    public String toString() {
        return "AuthModel{" +
                "accessToken='" + accessToken + '\'' +
                ", accessTokenExpires='" + accessTokenExpires + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", refreshTokenExpires='" + refreshTokenExpires + '\'' +
                '}';
    }
}
