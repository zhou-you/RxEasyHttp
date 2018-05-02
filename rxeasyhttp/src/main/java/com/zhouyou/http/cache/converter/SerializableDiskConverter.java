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

package com.zhouyou.http.cache.converter;


import com.zhouyou.http.utils.HttpLog;
import com.zhouyou.http.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * <p>描述：序列化对象的转换器</p>
 * 1.使用改转换器，对象&对象中的其它所有对象都必须是要实现Serializable接口（序列化）<br>
 * 优点：<br>
 *   速度快<br>
 * 《-------骚年，自己根据实际需求选择吧！！！------------》<br>
 * 作者： zhouyou<br>
 * 日期： 2016/12/24 17:35<br>
 * 版本： v2.0<br>
 */
@SuppressWarnings(value={"unchecked", "deprecation"})
public class SerializableDiskConverter implements IDiskConverter {

    @Override
    public <T> T load(InputStream source, Type type) {
        //序列化的缓存不需要用到clazz
        T value = null;
        ObjectInputStream oin = null;
        try {
            oin = new ObjectInputStream(source);
            value = (T) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            HttpLog.e(e);
        } finally {
            Utils.close(oin);
        }
        return value;
    }

    @Override
    public boolean writer(OutputStream sink, Object data) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(sink);
            oos.writeObject(data);
            oos.flush();
            return true;
        } catch (IOException e) {
            HttpLog.e(e);
        } finally {
            Utils.close(oos);
        }
        return false;
    }

}
