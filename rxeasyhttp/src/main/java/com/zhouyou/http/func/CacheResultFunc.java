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


import com.zhouyou.http.cache.model.CacheResult;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * <p>描述：缓存结果转换</p>
 * 作者： zhouyou<br>
 * 日期： 2017/4/21 10:53 <br>
 * 版本： v1.0<br>
 */
public class CacheResultFunc<T> implements Function<CacheResult<T>, T> {
    @Override
    public T apply(@NonNull CacheResult<T> tCacheResult) throws Exception {
        return tCacheResult.data;
    }
}
