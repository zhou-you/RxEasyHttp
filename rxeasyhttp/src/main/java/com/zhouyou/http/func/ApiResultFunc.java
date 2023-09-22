/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhouyou.http.func;

import com.google.gson.Gson;
import com.zhouyou.http.gson.ZGsonBuilder;
import com.zhouyou.http.model.ApiResult;

import java.io.IOException;
import java.lang.reflect.Type;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;


/**
 * <p>描述：定义了ApiResult结果转换Func</p>
 * 作者： zhouyou<br>
 * 日期： 2017/3/15 16:52 <br>
 * 版本： v1.0<br>
 */
public class ApiResultFunc<T> implements Function<ResponseBody, ApiResult<T>> {
    protected Type type;
    protected Gson gson;

    public ApiResultFunc(Type type) {
        this.type = type;
        gson = ZGsonBuilder.gsonBuilder2(type).create();
    }

    @Override
    public ApiResult<T> apply(@NonNull ResponseBody responseBody) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(-1);

        try {
            String json = responseBody.string();
            return gson.fromJson(json, type);
        } catch (IOException e) {
            e.printStackTrace();
            apiResult.setMsg(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            apiResult.setMsg(e.getMessage());
        } finally {
            responseBody.close();
        }

        return apiResult;
    }

}




///*
// * Copyright (C) 2017 zhouyou(478319399@qq.com)
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *       http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.zhouyou.http.func;
//
//import android.text.TextUtils;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.zhouyou.http.model.ApiResult;
//import com.zhouyou.http.utils.Utils;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.lang.reflect.Modifier;
//import java.lang.reflect.Type;
//import java.util.List;
//
//import io.reactivex.annotations.NonNull;
//import io.reactivex.functions.Function;
//import okhttp3.ResponseBody;
//
//
///**
// * <p>描述：定义了ApiResult结果转换Func</p>
// * 作者： zhouyou<br>
// * 日期： 2017/3/15 16:52 <br>
// * 版本： v1.0<br>
// */
//@SuppressWarnings("unchecked")
//public class ApiResultFunc<T> implements Function<ResponseBody, ApiResult<T>> {
//    protected Type type;
//    protected Gson gson;
//
//    public ApiResultFunc(Type type) {
//        gson = new GsonBuilder()
//                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
//                .serializeNulls()
//                .create();
//        this.type = type;
//    }
//
//    @Override
//    public ApiResult<T> apply(@NonNull ResponseBody responseBody) {
//        ApiResult<T> apiResult = new ApiResult<>();
//        apiResult.setCode(-1);
//
//        try {
//            String json = responseBody.string();
//            final ApiResult result = parseApiResult(json, apiResult);
//
//            if (result == null) {
//                apiResult.setMsg("data is null");
//            } else {
//                apiResult.setCode(result.getCode());
//                apiResult.setMsg(result.getMsg());
//
//                final Class<T> clazz = Utils.getClass(type, 0);
//                //增加是List<String>判断错误的问题
//                if (apiResult.isOk()) {
//                    if (!List.class.isAssignableFrom(clazz) && clazz.equals(String.class)) {
//                        apiResult.setData((T) json);
//                        apiResult.setCode(0);
//                    } else {
//                        apiResult = gson.fromJson(json, type);
//                    }
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            apiResult.setMsg(e.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//            apiResult.setMsg(e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//            apiResult.setMsg(e.getMessage());
//        } finally {
//            responseBody.close();
//        }
//        return apiResult;
//    }
//
//    private ApiResult parseApiResult(String json, ApiResult apiResult) throws JSONException {
//        if (TextUtils.isEmpty(json))
//            return null;
//        JSONObject jsonObject = new JSONObject(json);
//        if (jsonObject.has("code")) {
//            apiResult.setCode(jsonObject.getInt("code"));
//        }
//        if (jsonObject.has("message")) {
//            apiResult.setMsg(jsonObject.getString("message"));
//        }
////        if (jsonObject.has("data")) {
////            apiResult.setData(jsonObject.getString("data"));
////        }
//        return apiResult;
//    }
//}
