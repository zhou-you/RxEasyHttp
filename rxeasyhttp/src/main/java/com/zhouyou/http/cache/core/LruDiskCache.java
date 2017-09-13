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


import com.jakewharton.disklrucache.DiskLruCache;
import com.zhouyou.http.cache.converter.IDiskConverter;
import com.zhouyou.http.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;


/**
 * <p>描述：磁盘缓存实现类</p>
 * 作者： zhouyou<br>
 * 日期： 2016/12/24 10:35<br>
 * 版本： v2.0<br>
 *
 * 修改者： zhouyou<br>
 * 日期： 2016/01/07 10:35<br>
 *  1.为了更好的扩展功能，统一使用BasicCache<br>
 *  2.将来做内存管理也可以继承BasicCache来统一处理<br>
 */
public class LruDiskCache extends BaseCache {
    private IDiskConverter mDiskConverter;
    private DiskLruCache mDiskLruCache;


    public LruDiskCache(IDiskConverter diskConverter, File diskDir, int appVersion, long diskMaxSize) {
        this.mDiskConverter = Utils.checkNotNull(diskConverter, "diskConverter ==null");
        try {
            mDiskLruCache = DiskLruCache.open(diskDir, appVersion, 1, diskMaxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected <T> T doLoad(Type type, String key) {
        if (mDiskLruCache == null) {
            return null;
        }
        try {
            DiskLruCache.Editor edit = mDiskLruCache.edit(key);
            if (edit == null) {
                return null;
            }

            InputStream source = edit.newInputStream(0);
            T value;
            if (source != null) {
                value = mDiskConverter.load(source,type);
                Utils.close(source);
                edit.commit();
                return value;
            }
            edit.abort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected <T> boolean doSave(String key, T value) {
        if (mDiskLruCache == null) {
            return false;
        }
        try {
            DiskLruCache.Editor edit = mDiskLruCache.edit(key);
            if (edit == null) {
                return false;
            }
            OutputStream sink = edit.newOutputStream(0);
            if (sink != null) {
                boolean result = mDiskConverter.writer(sink, value);
                Utils.close(sink);
                edit.commit();
                return result;
            }
            edit.abort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected boolean doContainsKey(String key) {
        if (mDiskLruCache == null) {
            return false;
        }
        try {
            return mDiskLruCache.get(key) != null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected boolean doRemove(String key) {
        if (mDiskLruCache == null) {
            return false;
        }
        try {
            return mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    protected boolean doClear() {
        boolean statu = false;
        try {
            mDiskLruCache.delete();
            statu = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statu;
    }

    @Override
    protected boolean isExpiry(String key, long existTime) {
        if (mDiskLruCache == null) {
            return false;
        }
        if (existTime > -1) {//-1表示永久性存储 不用进行过期校验
            //为什么这么写，请了解DiskLruCache，看它的源码
            File file = new File(mDiskLruCache.getDirectory(), key + "." + 0);
            if (isCacheDataFailure(file, existTime)) {//没有获取到缓存,或者缓存已经过期!
                return true;
            }
        }
        return false;
    }

    /**
     * 判断缓存是否已经失效
     */
    private boolean isCacheDataFailure(File dataFile, long time) {
        if (!dataFile.exists()) {
            return false;
        }
        long existTime = System.currentTimeMillis() - dataFile.lastModified();
        return existTime > time*1000;
    }
    
}
