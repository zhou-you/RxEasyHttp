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

package com.zhouyou.http.cache.model;

/**
 * <p>描述：网络请求策略</p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/24 10:35<br>
 * 版本： v2.0<br>
 */
public enum CacheMode {
    /**
     * 不使用缓存,该模式下,cacheKey,cacheMaxAge 参数均无效
     **/
    NO_CACHE("NoStrategy"),
    /**
     * 完全按照HTTP协议的默认缓存规则，走OKhttp的Cache缓存
     **/
    DEFAULT("NoStrategy"),
    /**
     * 先请求网络，请求网络失败后再加载缓存
     */
    FIRSTREMOTE("FirstRemoteStrategy"),

    /**
     * 先加载缓存，缓存没有再去请求网络
     */
    FIRSTCACHE("FirstCacheStategy"),

    /**
     * 仅加载网络，但数据依然会被缓存
     */
    ONLYREMOTE("OnlyRemoteStrategy"),

    /**
     * 只读取缓存
     */
    ONLYCACHE("OnlyCacheStrategy"),

    /**
     * 先使用缓存，不管是否存在，仍然请求网络，会回调两次
     */
    CACHEANDREMOTE("CacheAndRemoteStrategy"),
    /**
     * 先使用缓存，不管是否存在，仍然请求网络，会先把缓存回调给你，
     * 等网络请求回来发现数据是一样的就不会再返回，否则再返回
     * （这样做的目的是防止数据是一样的你也需要刷新界面）
     */
    CACHEANDREMOTEDISTINCT("CacheAndRemoteDistinctStrategy");

    private final String className;

    CacheMode(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
