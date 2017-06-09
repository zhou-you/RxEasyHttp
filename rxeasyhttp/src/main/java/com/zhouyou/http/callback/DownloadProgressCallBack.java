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

package com.zhouyou.http.callback;

/**
 * <p>描述：下载进度回调（主线程，可以直接操作UI）</p>
 * 作者： zhouyou<br>
 * 日期： 2017/4/28 16:28 <br>
 * 版本： v1.0<br>
 */
public abstract class DownloadProgressCallBack<T> extends CallBack<T> {
    public DownloadProgressCallBack() {
    }

    @Override
    public void onSuccess(T response) {
        
    }

    public abstract void update(long bytesRead, long contentLength, boolean done);

    public abstract void onComplete(String path);

    @Override
    public void onCompleted() {
        
    }
}
