package com.zhouyou.http.demo.interceptor;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.demo.constant.ComParamContact;
import com.zhouyou.http.demo.model.AuthModel;
import com.zhouyou.http.demo.model.LoginCache;
import com.zhouyou.http.demo.model.LoginInfo;
import com.zhouyou.http.demo.token.TokenManager;
import com.zhouyou.http.demo.utils.DateTimeUtils;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.interceptor.BaseExpiredInterceptor;
import com.zhouyou.http.model.ApiResult;
import com.zhouyou.http.utils.HttpLog;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>描述：处理het token、签名等异常</p>
 * 作者： zhouyou<br>
 * 日期： 2017/5/4 21:02 <br>
 * 版本： v1.0<br>
 */
public class MyTokenInterceptor extends BaseExpiredInterceptor {
    private ApiResult apiResult;

    @Override
    public boolean isResponseExpired(Response response, String bodyString) {
        apiResult = new Gson().fromJson(bodyString, ApiResult.class);
        if (apiResult != null) {
            int code = apiResult.getCode();
            if (code == ComParamContact.Code.ACCESS_TOKEN_EXPIRED
                    || code == ComParamContact.Code.REFRESH_TOKEN_EXPIRED
                    || code == ComParamContact.Code.OTHER_PHONE_LOGINED
                    || code == ComParamContact.Code.ERROR_SIGN
                    || code == ComParamContact.Code.TIMESTAMP_ERROR
                    ) {
                return true;
            }
        }
        return false;
    }

    //只有上面配置的过期情况才会执行这里
    @Override
    public Response responseExpired(Chain chain, String bodyString) {
        try {
            switch (apiResult.getCode()) {
                case ComParamContact.Code.ACCESS_TOKEN_EXPIRED: //AccessToken错误或已过期
                    refreshToken();
                    if (authModel != null) {
                        return processAccessTokenError(chain, chain.request());
                    }
                    break;
//                    case ComParamContact.Code.NO_ACCESS_TOKEN://缺少授权信息,没有accessToken,应该是没有登录
                case ComParamContact.Code.REFRESH_TOKEN_EXPIRED://RefreshToken错误或已过期
                    reLogin();
                    if (authModel != null) {
                        return processRefreshTokenError(chain, chain.request());
                    }
                    break;
                case ComParamContact.Code.OTHER_PHONE_LOGINED://帐号在其它手机已登录
                    notifyLoginExit(apiResult.getMsg());
                    break;
                case ComParamContact.Code.ERROR_SIGN://签名错误
                    return processSignError(chain, chain.request());
                case ComParamContact.Code.TIMESTAMP_ERROR://timestamp过期
                    Type type = new TypeToken<ApiResult<Long>>() {
                    }.getType();
                    ApiResult<Long> aaa = new Gson().fromJson(bodyString, type);
                    HttpLog.e("uuok.timestapm:" + aaa.getData());
                    TokenManager.getInstance().setTimestamp(aaa.getData());
                    return processTimestampError(chain, chain.request());
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    AuthModel authModel = null;

    //同步请求refreshToken
    public void refreshToken() throws IOException {
        EasyHttp.post(ComParamContact.Token.PATH)
                .params(ComParamContact.Common.REFRESH_TOKEN, TokenManager.getInstance().getAuthModel().getRefreshToken())
                .sign(false)
                .accessToken(false)
                .timeStamp(true)
                .syncRequest(true)//同步请求
                .execute(new SimpleCallBack<AuthModel>() {
                    @Override
                    public void onError(ApiException e) {
                        notifyLoginExit(e.getMessage());
                    }

                    @Override
                    public void onSuccess(AuthModel response) {
                        TokenManager.getInstance().setAuthModel(response);
                        authModel = response;
                    }
                });
    }

    /**
     * 处理AccessToke错误
     */
    private Response processAccessTokenError(Chain chain, Request request) throws IOException {
        return processError(chain, request, ComParamContact.Common.ACCESSTOKEN, TokenManager.getInstance().getAuthModel().getAccessToken());
    }

    /**
     * 处理HeT网络请求出现的业务错误
     *
     * @return
     * @throws IOException
     */
    private Response processError(Chain chain, Request request, String key, String data) throws IOException {
        // create a new request and modify it accordingly using the new token
        String method = request.method();
        FormBody oldBody = (FormBody) request.body();
        if (oldBody == null) {
            if (method.equalsIgnoreCase("GET")) {
                oldBody = getRequestParams(request.url().query());
            } else {
                return chain.proceed(request);
            }
        }
        FormBody.Builder newBody = new FormBody.Builder();
        for (int i = 0; i < oldBody.size(); i++) {
            String name = oldBody.encodedName(i);
            String value = oldBody.encodedValue(i);
            if (!TextUtils.isEmpty(name)) {
                if (name.equals(key)) {
                    value = data;
                }
                if (name.equals(ComParamContact.Common.SIGN)) {
                    String path = request.url().pathSegments().get(0);
                    String ret = processSign(oldBody, path);
                    if (!TextUtils.isEmpty(ret)) {
                        value = ret;
                    }
                }
            }
            newBody.add(name, value);
        }

        Request newRequest;
        if (method.equalsIgnoreCase("GET")) {
            String url = packageParams(newBody.build());
            HttpLog.i("uuok.GET.Error.newUrl:" + url);
            HttpUrl newHrrpIrl = request.url().newBuilder().query(url).build();
            newRequest = request.newBuilder().url(newHrrpIrl).get().build();
        } else {
            newRequest = request.newBuilder().post(newBody.build()).build();
        }
        // retry the request
//        originalResponse.body().close();
        return chain.proceed(newRequest);
    }

    /**
     * 将GET请求的参数封装成FormBody
     */
    private FormBody getRequestParams(String params) {
        if (params == null)
            return null;
        String[] strArr = params.split("&");
        if (strArr == null) {
            return null;
        }

        TreeMap<String, String> map = new TreeMap<>();
        FormBody.Builder fBulder = new FormBody.Builder();
        for (String s : strArr) {
            String[] sArr = s.split("=");
            if (sArr.length < 2)
                continue;
            map.put(sArr[0], sArr[1]);
            fBulder.add(sArr[0], sArr[1]);
        }
        FormBody formBody = fBulder.build();
        return formBody;
    }

    /**
     * 封装参数
     */
    private String packageParams(FormBody oldBody) {
        List<String> namesAndValues = new ArrayList<>();
        for (int i = 0; i < oldBody.size(); i++) {
            String name = oldBody.encodedName(i);
            String value = oldBody.encodedValue(i);
            if (!TextUtils.isEmpty(name)) {
                namesAndValues.add(name);
                namesAndValues.add(value);
            }
        }
        StringBuilder sb = new StringBuilder();
        namesAndValuesToQueryString(sb, namesAndValues);
        return sb.toString();
    }

    /**
     * 合并GET参数
     */
    private void namesAndValuesToQueryString(StringBuilder out, List<String> namesAndValues) {
        for (int i = 0, size = namesAndValues.size(); i < size; i += 2) {
            String name = namesAndValues.get(i);
            String value = namesAndValues.get(i + 1);
            if (i > 0) out.append('&');
            out.append(name);
            if (value != null) {
                out.append('=');
                out.append(value);
            }
        }
    }

    /**
     * 处理签名
     */
    private String processSign(FormBody body, String path) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        boolean isSign = false;
        for (int i = 0; i < body.size(); i++) {
            String name = body.encodedName(i);
            String value = body.encodedValue(i);
            if (name.equals(ComParamContact.Common.SIGN)) {
                isSign = true;
                continue;
            }
            if (name.equals(ComParamContact.Common.ACCESSTOKEN)) {
                value = TokenManager.getInstance().getAuthModel().getAccessToken();
            } else if (name.equals(ComParamContact.Common.REFRESH_TOKEN)) {
                value = TokenManager.getInstance().getAuthModel().getRefreshToken();
            } else if (name != null && name.equals(ComParamContact.Common.TIMESTAMP)) {
                value = String.valueOf(TokenManager.getInstance().getTimestamp());
            }
            treeMap.put(name, value);
        }
        if (isSign) {
            return sign(path, treeMap);
        }
        return null;
    }

    /**
     * 处理刷新Token错误
     */
    private Response processRefreshTokenError(Chain chain, Request request) throws IOException {
        return processError(chain, request, ComParamContact.Common.REFRESH_TOKEN, TokenManager.getInstance().getAuthModel().getRefreshToken());
    }

    //可以根据自己规则来签名
    private String sign(String path, TreeMap<String, String> dynamicMap) {
        //写签名逻辑...........
        return "";
    }

    /**
     * 同步请求重新登录
     *
     * @return
     * @throws IOException
     */
    private void reLogin() throws IOException {
        //从ACache缓存中去出用户名和密码
        LoginInfo loginInfo = LoginCache.getInstance().get();
        if (loginInfo == null) {
            authModel = null;
            return;
        }
        EasyHttp.post(ComParamContact.Login.PATH)
                .params(ComParamContact.Login.ACCOUNT, loginInfo.getUsername())
                .params(ComParamContact.Login.PASSWORD, loginInfo.getPassword())
                .sign(true)
                .timeStamp(true)
                .syncRequest(true)
                .execute(new SimpleCallBack<AuthModel>() {
                    @Override
                    public void onError(ApiException e) {
                        //如果刷新都失败了,那只能通知上层退出登陆了
                        notifyLoginExit(e.getMessage());
                    }

                    @Override
                    public void onSuccess(AuthModel response) {
                        if (response != null) {
                            TokenManager.getInstance().setAuthModel(response);
                            authModel = response;
                        }
                    }
                });
    }

    /**
     * 处理时间戳过期错误
     *
     * @param chain
     * @param request
     * @return
     * @throws IOException
     */
    private Response processTimestampError(Chain chain, Request request) throws IOException {
        return processError(chain, request, ComParamContact.Common.TIMESTAMP, TokenManager.getInstance().getTimestamp() + "");
    }

    /**
     * 处理签名错误
     */
    private Response processSignError(Chain chain, Request request) throws IOException {
        String method = request.method();
        // create a new request and modify it accordingly using the new token
        FormBody oldBody = (FormBody) request.body();
        if (oldBody == null) {
            if (request.method().equalsIgnoreCase("GET")) {
                oldBody = getRequestParams(request.url().query());
            } else {
                return chain.proceed(request);
            }
        }
        String path = request.url().pathSegments().get(0);
        String sign = processSign(oldBody, path);
        FormBody.Builder newBody = new FormBody.Builder();
        for (int i = 0; i < oldBody.size(); i++) {
            String name = oldBody.encodedName(i);
            String value = oldBody.encodedValue(i);
            if (!TextUtils.isEmpty(name)) {
                if (name.equals(ComParamContact.Common.SIGN)) {
                    if (!TextUtils.isEmpty(sign)) {
                        value = sign;
                    }
                }
            }
            newBody.add(name, value);
        }

        Request newRequest;
        if (method.equalsIgnoreCase("GET")) {
            String url = packageParams(newBody.build());
            HttpLog.i("uuok.GET.Error.newUrl:" + url);
            HttpUrl newHrrpIrl = request.url().newBuilder().query(url).build();
            newRequest = request.newBuilder().url(newHrrpIrl).get().build();
        } else {
            newRequest = request.newBuilder().post(newBody.build()).build();
        }
        return chain.proceed(newRequest);
    }


    private void notifyLoginExit(String msg) {
        //如果刷新都失败了,那只能通知上层登陆推出了
        msg = DateTimeUtils.utc2BeiJingTime(msg);
        HttpLog.e("notifyLoginExit ################## " + msg);
        //RxBus.getInstance().post(ECode.Token.EC_LOGINOUT, msg);
        //业务出错,可以把所有HTTP请求干掉.
        // OkHttpManager.cancelAll();
    }
}
