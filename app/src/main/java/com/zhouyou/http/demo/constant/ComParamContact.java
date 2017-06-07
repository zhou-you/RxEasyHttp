package com.zhouyou.http.demo.constant;

public class ComParamContact {

    public final static class Common {
        public final static String APPID = "appId";
        public final static String ACCESSTOKEN = "accessToken";
        public final static String TIMESTAMP = "timestamp";
        public final static String REFRESH_TOKEN = "refreshToken";
        public final static String SIGN = "sign";
    }

    public final static class Code {
        //http请求成功状态码
        public final static int HTTP_SUCESS = 0;
        //AccessToken错误或已过期
        public static final int ACCESS_TOKEN_EXPIRED = 100010101;
        //RefreshToken错误或已过期
        public static final int REFRESH_TOKEN_EXPIRED = 100010102;
        //帐号在其它手机已登录
        public static final int OTHER_PHONE_LOGINED = 100021006;
        //timestamp过期
        public static final int TIMESTAMP_ERROR = 100010104;
        //缺少授权信息,没有accessToken,应该是没有登录
        public final static int NO_ACCESS_TOKEN = 100010100;
        //签名错误
        public final static int ERROR_SIGN = 100010105;
        //设备未绑定
        public final static int DEVICE_NO_BIND = 100022001;
    }

    public final static class Token {
        public final static String PATH = "/v1/account/token/refresh";
        public final static String AUTH_MODEL = "authModel";

    }

    public final static class UserStatus {
        public final static String PATH = "/v1/user/get";
    }

    public final static class Login {
        public final static String PATH = "/v1/account/login";
        public final static String ACCOUNT = "account";
        public final static String PASSWORD = "password";
    }
}
