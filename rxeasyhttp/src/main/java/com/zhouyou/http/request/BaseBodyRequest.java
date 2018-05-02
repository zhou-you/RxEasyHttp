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

package com.zhouyou.http.request;

import com.zhouyou.http.body.ProgressResponseCallBack;
import com.zhouyou.http.body.RequestBodyUtils;
import com.zhouyou.http.body.UploadProgressRequestBody;
import com.zhouyou.http.model.HttpParams;
import com.zhouyou.http.utils.Utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;

/**
 * <p>描述：body请求的基类</p>
 * 作者： zhouyou<br>
 * 日期： 2017/5/22 17:13 <br>
 * 版本： v1.0<br>
 */
@SuppressWarnings(value={"unchecked", "deprecation"})
public abstract class BaseBodyRequest<R extends BaseBodyRequest> extends BaseRequest<R> {
    protected String string;                                   //上传的文本内容
    protected MediaType mediaType;                                   //上传的文本内容
    protected String json;                                     //上传的Json
    protected byte[] bs;                                       //上传的字节数据
    protected Object object;                                   //上传的对象
    protected RequestBody requestBody;                         //自定义的请求体

    public enum UploadType {
        /**
         * MultipartBody.Part方式上传
         */
        PART,
        /**
         * Map RequestBody方式上传
         */
        BODY
    }

    private UploadType currentUploadType = UploadType.PART;

    public BaseBodyRequest(String url) {
        super(url);
    }

    public R requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return (R) this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    public R upString(String string) {
        this.string = string;
        this.mediaType = okhttp3.MediaType.parse("text/plain");
        return (R) this;
    }

    public R upString(String string, String mediaType) {
        this.string = string;
        Utils.checkNotNull(mediaType, "mediaType==null");
        this.mediaType = okhttp3.MediaType.parse(mediaType);
        return (R) this;
    }

    public R upObject(@Body Object object) {
        this.object = object;
        return (R) this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    public R upJson(String json) {
        this.json = json;
        return (R) this;
    }

    /**
     * 注意使用该方法上传字符串会清空实体中其他所有的参数，头信息不清除
     */
    public R upBytes(byte[] bs) {
        this.bs = bs;
        return (R) this;
    }

    public R params(String key, File file, ProgressResponseCallBack responseCallBack) {
        params.put(key, file, responseCallBack);
        return (R) this;
    }

    public R params(String key, InputStream stream, String fileName, ProgressResponseCallBack responseCallBack) {
        params.put(key, stream, fileName, responseCallBack);
        return (R) this;
    }

    public R params(String key, byte[] bytes, String fileName, ProgressResponseCallBack responseCallBack) {
        params.put(key, bytes, fileName, responseCallBack);
        return (R) this;
    }

    public R addFileParams(String key, List<File> files, ProgressResponseCallBack responseCallBack) {
        params.putFileParams(key, files, responseCallBack);
        return (R) this;
    }

    public R addFileWrapperParams(String key, List<HttpParams.FileWrapper> fileWrappers) {
        params.putFileWrapperParams(key, fileWrappers);
        return (R) this;
    }

    public R params(String key, File file, String fileName, ProgressResponseCallBack responseCallBack) {
        params.put(key, file, fileName, responseCallBack);
        return (R) this;
    }

    public <T> R params(String key, T file, String fileName, MediaType contentType, ProgressResponseCallBack responseCallBack) {
        params.put(key, file, fileName, contentType, responseCallBack);
        return (R) this;
    }

    /**
     * 上传文件的方式，默认part方式上传
     */
    public <T> R uploadType(UploadType uploadtype) {
        currentUploadType = uploadtype;
        return (R) this;
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        if (this.requestBody != null) { //自定义的请求体
            return apiManager.postBody(url, this.requestBody);
        } else if (this.json != null) {//上传的Json
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), this.json);
            return apiManager.postJson(url, body);
        } else if (this.object != null) {//自定义的请求object
            return apiManager.postBody(url, object);
        } else if (this.string != null) {//上传的文本内容
            RequestBody body = RequestBody.create(mediaType, this.string);
            return apiManager.postBody(url, body);
        } else if (this.bs != null) {//上传的字节数据
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/octet-stream"), this.bs);
            return apiManager.postBody(url, body);
        }
        if (params.fileParamsMap.isEmpty()) {
            return apiManager.post(url, params.urlParamsMap);
        } else {
            if (currentUploadType == UploadType.PART) {//part方式上传
                return uploadFilesWithParts();
            } else {//body方式上传
                return uploadFilesWithBodys();
            }
        }
    }

    protected Observable<ResponseBody> uploadFilesWithParts() {
        List<MultipartBody.Part> parts = new ArrayList<>();
        //拼接参数键值对
        for (Map.Entry<String, String> mapEntry : params.urlParamsMap.entrySet()) {
            parts.add(MultipartBody.Part.createFormData(mapEntry.getKey(),mapEntry.getValue()));
        }
        //拼接文件
        for (Map.Entry<String, List<HttpParams.FileWrapper>> entry : params.fileParamsMap.entrySet()) {
            List<HttpParams.FileWrapper> fileValues = entry.getValue();
            for (HttpParams.FileWrapper fileWrapper : fileValues) {
                MultipartBody.Part part = addFile(entry.getKey(), fileWrapper);
                parts.add(part);
            }
        }
        return apiManager.uploadFiles(url, parts);
    }

    protected Observable<ResponseBody> uploadFilesWithBodys() {
        Map<String, RequestBody> mBodyMap = new HashMap<>();
        //拼接参数键值对
        for (Map.Entry<String, String> mapEntry : params.urlParamsMap.entrySet()) {
            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), mapEntry.getValue());
            mBodyMap.put(mapEntry.getKey(), body);
        }
        //拼接文件
        for (Map.Entry<String, List<HttpParams.FileWrapper>> entry : params.fileParamsMap.entrySet()) {
            List<HttpParams.FileWrapper> fileValues = entry.getValue();
            for (HttpParams.FileWrapper fileWrapper : fileValues) {
                RequestBody requestBody = getRequestBody(fileWrapper);
                UploadProgressRequestBody uploadProgressRequestBody = new UploadProgressRequestBody(requestBody, fileWrapper.responseCallBack);
                mBodyMap.put(entry.getKey(), uploadProgressRequestBody);
            }
        }
        return apiManager.uploadFiles(url, mBodyMap);
    }

    //文件方式
    private MultipartBody.Part addFile(String key, HttpParams.FileWrapper fileWrapper) {
        //MediaType.parse("application/octet-stream", file)
        RequestBody requestBody = getRequestBody(fileWrapper);
        Utils.checkNotNull(requestBody, "requestBody==null fileWrapper.file must is File/InputStream/byte[]");
        //包装RequestBody，在其内部实现上传进度监听
        if (fileWrapper.responseCallBack != null) {
            UploadProgressRequestBody uploadProgressRequestBody = new UploadProgressRequestBody(requestBody, fileWrapper.responseCallBack);
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, fileWrapper.fileName, uploadProgressRequestBody);
            return part;
        } else {
            MultipartBody.Part part = MultipartBody.Part.createFormData(key, fileWrapper.fileName, requestBody);
            return part;
        }
    }

    private RequestBody getRequestBody(HttpParams.FileWrapper fileWrapper) {
        RequestBody requestBody = null;
        if (fileWrapper.file instanceof File) {
            requestBody = RequestBody.create(fileWrapper.contentType, (File) fileWrapper.file);
        } else if (fileWrapper.file instanceof InputStream) {
            //requestBody = RequestBodyUtils.create(RequestBodyUtils.MEDIA_TYPE_MARKDOWN, (InputStream) fileWrapper.file);
            requestBody = RequestBodyUtils.create(fileWrapper.contentType, (InputStream) fileWrapper.file);
        } else if (fileWrapper.file instanceof byte[]) {
            requestBody = RequestBody.create(fileWrapper.contentType, (byte[]) fileWrapper.file);
        }
        return requestBody;
    }
}
