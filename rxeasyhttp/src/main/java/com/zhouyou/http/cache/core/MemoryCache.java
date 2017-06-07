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

package com.zhouyou.http.cache.core;

import java.lang.reflect.Type;

/**
 * <p>描述：内存缓存</p>
 * 内存缓存针对缓存的时间不好处理，暂时没有写内存缓存，等后面有思路了，再加上该部分。<br>
 * 作者： zhouyou<br>
 * 日期： 2017/1/7 15:22<br>
 * 版本： v2.0<br>
 */

public class MemoryCache extends BaseCache {
    @Override
    protected boolean doContainsKey(String key) {
        return false;
    }

    @Override
    protected boolean isExpiry(String key, long existTime) {
        return false;
    }

    @Override
    protected <T> T doLoad(Type type, String key) {
        return null;
    }

    @Override
    protected <T> boolean doSave(String key, T value) {
        return false;
    }

    @Override
    protected boolean doRemove(String key) {
        return false;
    }

    @Override
    protected boolean doClear() {
        return false;
    }
}
