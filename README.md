## RxEasyHttp

本库是一款基于RxJava2+Retrofit2实现简单易用的网络请求框架，结合android平台特性的网络封装库,采用api链式调用一点到底,集成cookie管理,多种缓存模式,极简https配置,上传下载进度显示,请求错误自动重试,请求携带token、时间戳、签名sign动态配置,自动登录成功后请求重发功能,3种层次的参数设置默认全局局部,默认标准ApiResult同时可以支持自定义的数据结构，已经能满足现在的大部分网络请求。
*注：Retrofit和Rxjava是当下非常火爆的开源框架，均来自神一般的公司。本库就不介绍Retrofit和Rxjava2的用法。*

## 为什么会封装此库？
网上好的开源网络库像Volley、async-http、okhttp、retrofit等都非常强大，但是实际应用中我们不会直接去使用，一般都会根据自己的业务再封装一层，这样更方便快捷，又能统一处理业务共性的东西例如：统一的数据结构（code、msg、data）、token处理、网络异常等情况。在使用retrofit来请求网络的时候，项目的需求越来越多，api也随之越来越多，一个普通的应用api一般也在100+左右。如果把这些api放在一个ApiService内会很臃肿，不利于查看api.如果采用模块的方式对api进行分类，每个模块对应若干个api.以retrofit的使用方式又需要创建若干个ApiService，这种方式维护方便，但是模块增多了，类也增多了很多。对于懒人来说就想通过一个URL就能回调你所需要的数据，什么ApiService都不想理会，同时又可以很快的与自己的业务相关联，就类似于代替你在开源网络库基础上再封装一层的作用，于是本库就应运而生。

## 特点
- 比Retrofit使用更简单、更易用。
- 采用链式调用一点到底
- 加入基础ApiService，减少Api冗余
- 支持动态配置和自定义底层框架Okhttpclient、Retrofit.
- 支持多种方式访问网络GET、POST、PUT、DELETE等请求协议
- 支持网络缓存,八种缓存策略可选,涵盖大多数业务场景
- 支持固定添加header和动态添加header
- 支持添加全局参数和动态添加局部参数
- 支持文件下载、多文件上传和表单提交数据
- 支持文件请求、上传、下载的进度回调、错误回调，也可以自定义回调
- 支持默认、全局、局部三个层次的配置功能
- 支持任意数据结构的自动解析
- 支持添加动态参数例如timeStamp时间戳、token、签名sign
- 支持自定义的扩展API
- 支持多个请求合并
- 支持Cookie管理
- 支持异步、同步请求
- 支持Https、自签名网站Https的访问、双向验证
- 支持失败重试机制，可以指定重试次数、重试间隔时间
- 支持根据ky删除网络缓存和清空网络缓存
- 提供默认的标准ApiResult解析和回调，并且可自定义ApiResult
- 支持取消数据请求，取消订阅，带有对话框的请求不需要手动取消请求，对话框消失会自动取消请求
- 支持请求数据结果采用回调和订阅两种方式
- api设计上结合http协议和android平台特点来实现,loading对话框,实时进度条显示
- 返回结果和异常统一处理
- 结合RxJava2，线程智能控制

## 关于我
[![github](https://img.shields.io/badge/GitHub-zhou--you-green.svg)](https://github.com/zhou-you)   [![csdn](https://img.shields.io/badge/CSDN-zhouy478319399-green.svg)](http://blog.csdn.net/zhouy478319399)
## 联系方式
本群旨在为使用我github项目的人提供方便，如果遇到问题欢迎在群里提问。

#### 欢迎加入QQ交流群（Q1群已满，请加入Q2群）

[![](https://img.shields.io/badge/%E7%82%B9%E6%88%91%E4%B8%80%E9%94%AE%E5%8A%A0%E5%85%A5Q1%E7%BE%A4-581235049%28%E5%B7%B2%E6%BB%A1%29-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=1e1f4bcfd8775a55e6cf6411f6ff0e7058ff469ef87c4d1e67890c27f0c5a390)

[![](https://img.shields.io/badge/%E7%82%B9%E6%88%91%E4%B8%80%E9%94%AE%E5%8A%A0%E5%85%A5Q2%E7%BE%A4-832887601-blue.svg)](http://shang.qq.com/wpa/qunwpa?idkey=f3c997d1c3cc6a8c9fa46d3fde0d663f50e4e6d0e6441b8cc276bef39befd24c)

![](http://img.blog.csdn.net/20170601165330238)![](https://img-blog.csdnimg.cn/20190627164802234.jpg)
## 演示（请star支持）
![](https://github.com/zhou-you/RxEasyHttp/raw/master/screenshot/1.gif) ![](https://github.com/zhou-you/RxEasyHttp/raw/master/screenshot/2.gif)
![](https://github.com/zhou-you/RxEasyHttp/raw/master/screenshot/3.gif) ![](https://github.com/zhou-you/RxEasyHttp/raw/master/screenshot/4.gif)

### RxEasyHttp与Rxjava结合使用场景演示
![](https://github.com/zhou-you/RxEasyHttp/raw/master/screenshot/5.gif)

**[RxEasyHttp网络库与Rxjava2结合常见使用场景介绍    点我！！！>>](http://blog.csdn.net/zhouy478319399/article/details/78550248)**

[![](https://badge.juejin.im/entry/5a0d4d0d6fb9a045080934f1/likes.svg?style=plastic)](https://juejin.im/post/5a0d4cd851882531ba108090)

## 版本说明

### 当前版本
[![release](https://img.shields.io/badge/release-V2.1.2-orange.svg)](https://github.com/zhou-you/RxEasyHttp/blob/master/update.md)

**[历史版本，点我、点我、点我>>](https://github.com/zhou-you/RxEasyHttp/blob/master/update.md)**

## 用法介绍
目前只支持主流开发工具AndtoidStudio的使用，没有提供Eclipse使用方式.
本项目Demo的网络请求的服务器地址为了安全，把url去掉了，但是Demo程序中的示例都是ok的
### 点击按钮下载Demo
[![downloads](https://img.shields.io/badge/downloads-2.2M-blue.svg)](https://github.com/zhou-you/RxEasyHttp/blob/master/apk/rxeasyhttp-demo.apk?raw=true) 
#### 扫码下载Demo
![](https://github.com/zhou-you/RxEasyHttp/blob/master/screenshot/down.png?raw=true)

### build.gradle设置
```
dependencies {
 compile 'com.zhouyou:rxeasyhttp:2.1.5'
}
```
想查看所有版本，请点击下面地址。

[![jcenter](https://img.shields.io/badge/Jcenter-Latest%20Release-orange.svg)](https://jcenter.bintray.com/com/zhouyou/rxeasyhttp/)
## 权限说明
如果使用本库实现文件下载到SD卡、或者配置了缓存数据到SD卡，你必须要考虑到Android6.0及以上系统的运行时权限，给大家推荐两个权限库：

[AndPermission](https://github.com/yanzhenjie/AndPermission)  
[RxPermissions](https://github.com/tbruyelle/RxPermissions) 

因为要请求网络、从SD卡读写缓存、下载文件到SD卡等等，所以需要在manifest.xml中配置以下几个权限，如果你已经配置过了这些权限，请不要重复配置：
```
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.INTERNET"/>
```
## 全局配置
一般在 Aplication，或者基类中，只需要调用一次即可，可以配置调试开关，全局的超时时间，公共的请求头和请求参数等信息
初始化需要一个Context，最好在Application#onCreate()中初始化，记得在manifest.xml中注册Application。
#### Application:

```
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
```
#### manifest.xml：

```
...

<application
    android:name=".MyApplication"
    ...
 />
```

## 默认初始化
如果使用默认始化后，一切采用默认设置。如果你需要配置全局超时时间、缓存、Cookie、底层为OkHttp的话，请看[高级初始化](https://github.com/zhou-you/RxEasyHttp#高级初始化)。
```
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EasyHttp.init(this);//默认初始化
    }
}
```
## 高级初始化
可以进行超时配置、网络缓存配置、okhttp相关参数配置、retrofit相关参数配置、cookie配置等，这些参数可以选择性的根据业务需要配置。
```
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        EasyHttp.init(this);//默认初始化,必须调用

        //全局设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.put("User-Agent", SystemInfoUtils.getUserAgent(this, AppConstant.APPID));
        //全局设置请求参数
        HttpParams params = new HttpParams();
        params.put("appId", AppConstant.APPID);

        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        EasyHttp.getInstance()
        
                //可以全局统一设置全局URL
                .setBaseUrl(Url)//设置全局URL  url只能是域名 或者域名+端口号 

                // 打开该调试开关并设置TAG,不需要就不要加入该行
                // 最后的true表示是否打印内部异常，一般打开方便调试错误
                .debug("EasyHttp", true)
                
                //如果使用默认的60秒,以下三行也不需要设置
                .setReadTimeOut(60 * 1000)
                .setWriteTimeOut(60 * 100)
                .setConnectTimeout(60 * 100)
                
                //可以全局统一设置超时重连次数,默认为3次,那么最差的情况会请求4次(一次原始请求,三次重连请求),
                //不需要可以设置为0
                .setRetryCount(3)//网络不好自动重试3次
                //可以全局统一设置超时重试间隔时间,默认为500ms,不需要可以设置为0
                .setRetryDelay(500)//每次延时500ms重试
                //可以全局统一设置超时重试间隔叠加时间,默认为0ms不叠加
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                
                //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体请看CacheMode
                .setCacheMode(CacheMode.NO_CACHE)
                //可以全局统一设置缓存时间,默认永不过期
                .setCacheTime(-1)//-1表示永久缓存,单位:秒 ，Okhttp和自定义RxCache缓存都起作用
                //全局设置自定义缓存保存转换器，主要针对自定义RxCache缓存
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                //全局设置自定义缓存大小，默认50M
                .setCacheMaxSize(100 * 1024 * 1024)//设置缓存大小为100M
                //设置缓存版本，如果缓存有变化，修改版本后，缓存就不会被加载。特别是用于版本重大升级时缓存不能使用的情况
                .setCacheVersion(1)//缓存版本为1
                //.setHttpCache(new Cache())//设置Okhttp缓存，在缓存模式为DEFAULT才起作用
                
                //可以设置https的证书,以下几种方案根据需要自己设置
                .setCertificates()                                  //方法一：信任所有证书,不安全有风险
                //.setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
                //配置https的域名匹配规则，不需要就不要加入，使用不当会导致https握手失败
                //.setHostnameVerifier(new SafeHostnameVerifier())
                //.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
                .addCommonHeaders(headers)//设置全局公共头
                .addCommonParams(params)//设置全局公共参数
                //.addNetworkInterceptor(new NoCacheInterceptor())//设置网络拦截器
                //.setCallFactory()//局设置Retrofit对象Factory
                //.setCookieStore()//设置cookie
                //.setOkproxy()//设置全局代理
                //.setOkconnectionPool()//设置请求连接池
                //.setCallbackExecutor()//全局设置Retrofit callbackExecutor
                //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
                //.addInterceptor(new GzipRequestInterceptor())//开启post数据进行gzip后发送给服务器
                .addInterceptor(new CustomSignInterceptor());//添加参数签名拦截器
    }
}
```
## 请求数据
网络请求，采用链式调用，支持一点到底。

### 入口方法
```
  /**
     * get请求
     */
    public static GetRequest get(String url);

    /**
     * post请求和文件上传
     */
    public static PostRequest post(String url);

    /**
     * delete请求
     */
    public static DeleteRequest delete(String url) ;

    /**
     * 自定义请求
     */
    public static CustomRequest custom();

    /**
     * 文件下载
     */
    public static DownloadRequest downLoad(String url) ;

    /**
     * put请求
     */
    public static PutRequest put(String url);
```


### 通用功能配置
1.包含一次普通请求所有能配置的参数，真实使用时不需要配置这么多，按自己的需要选择性的使用即可<br/>
2.以下配置全部是单次请求配置，不会影响全局配置，没有配置的仍然是使用全局参数。<br/>
3.为单个请求设置超时，比如涉及到文件的需要设置读写等待时间多一点。<br/>
完整参数GET示例：
```
EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .baseUrl("http://www.xxxx.com")//设置url
                .writeTimeOut(30*1000)//局部写超时30s,单位毫秒
                .readTimeOut(30*1000)//局部读超时30s,单位毫秒
                .connectTimeout(30*1000)//局部连接超时30s,单位毫秒
                .headers(new HttpHeaders("header1","header1Value"))//添加请求头参数
                .headers("header2","header2Value")//支持添加多个请求头同时添加
                .headers("header3","header3Value")//支持添加多个请求头同时添加
                .params("param1","param1Value")//支持添加多个参数同时添加
                .params("param2","param2Value")//支持添加多个参数同时添加
                //.addCookie(new CookieManger(this).addCookies())//支持添加Cookie
                .cacheTime(300)//缓存300s 单位s
                .cacheKey("cachekey")//缓存key
                .cacheMode(CacheMode.CACHEANDREMOTE)//设置请求缓存模式
                //.okCache()//使用模式缓存模式时，走Okhttp缓存
                .cacheDiskConverter(new GsonDiskConverter())//GSON-数据转换器
                //.certificates()添加证书
                .retryCount(5)//本次请求重试次数
                .retryDelay(500)//本次请求重试延迟时间500ms
                .addInterceptor(Interceptor)//添加拦截器
                .okproxy()//设置代理
                .removeHeader("header2")//移除头部header2
                .removeAllHeaders()//移除全部请求头
                .removeParam("param1")
                .accessToken(true)//本次请求是否追加token
                .timeStamp(false)//本次请求是否携带时间戳
                .sign(false)//本次请求是否需要签名
                .syncRequest(true)//是否是同步请求，默认异步请求。true:同步请求
                .execute(new CallBack<SkinTestResult>() {
                    @Override
                    public void onStart() {
                        //开始请求
                    }

                    @Override
                    public void onCompleted() {
                       //请求完成
                    }

                    @Override
                    public void onError(ApiException e) {
                      //请求错误
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                      //请求成功
                    }
                });
```
#### url
Url可以通过初始化配置的时候传入`EasyHttp.getInstance().setBaseUrl("http://www.xxx.com");`  
入口方法传入： `EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult").baseUrl("http://www.xxxx.com")`
如果入口方法中传入的url含有http或者https,则不会拼接初始化设置的baseUrl.
例如：`EasyHttp.get("http://www.xxx.com/v1/app/chairdressing/skinAnalyzePower/skinTestResult")`则setBaseUrl()和baseUrl()传入的baseurl都不会被拼接。
*注:EasyHttp.get/post/put/等采用拼接的用法时请注意，url要用/斜杠开头，例如：`EasyHttp.get("/v1/login")` 正确  ` EasyHttp.get("v1/login")` 错误*
#### http请求参数
两种设置方式
.params(HttpParams params)
.params("param1","param1Value")//添加参数键值对

 HttpParams params = new HttpParams();
 params.put("appId", AppConstant.APPID);
 .addCommonParams(params)//设置全局公共参数
#### http请求头
.headers(HttpHeaders headers) 
.headers("header2","header2Value")//添加参数键值对

.addCommonHeaders(headers)//设置全局公共头

### 普通网络请求
**支持get/post/delete/put等**
链式调用的终点请求的执行方式有：execute(Class<T> clazz) 、execute(Type type)、execute(CallBack<T> callBack)三种方式，都是针对标准的ApiResult
#### execute(CallBack<T> callBack)
1.EasyHttp（**推荐**）
示例：
```
方式一：
 //EasyHttp.post("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
 EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .readTimeOut(30 * 1000)//局部定义读超时
                .writeTimeOut(30 * 1000)
                .connectTimeout(30 * 1000)
                .params("name","张三")
                .timeStamp(true)
                .execute(new SimpleCallBack<SkinTestResult>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        if (response != null) showToast(response.toString());
                    }
                });
```
2.手动创建请求对象
```
 //GetRequest 、PostRequest、DeleteRequest、PutRequest
 GetRequest request = new GetRequest("/v1/app/chairdressing/skinAnalyzePower/skinTestResult");
        request.readTimeOut(30 * 1000)//局部定义读超时
                .params("param1", "param1Value1")
                .execute(new SimpleCallBack<SkinTestResult>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {

                    }
                });
```
#### execute(Class<T> clazz)和execute(Type type)
execute(Class<T> clazz)和execute(Type type)功能基本一样，execute(Type type)主要是针对集合不能直接传递Class
```
EasyHttp.get(url)
                .params("param1", "paramValue1")
                .execute(SkinTestResult.class)//非常简单直接传目标class
                //.execute(new TypeToken<List<SectionItem>>() {}.getType())//Type类型
                .subscribe(new BaseSubscriber<SkinTestResult>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(SkinTestResult skinTestResult) {
                        showToast(skinTestResult.toString());
                    }
                });
```
### 请求返回Disposable
网络请求会返回Disposable对象，方便取消网络请求
```
Disposable disposable = EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .params("param1", "paramValue1")
                .execute(new SimpleCallBack<SkinTestResult>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                        showToast(response.toString());
                    }
                });

        //在需要取消网络请求的地方调用,一般在onDestroy()中
        //EasyHttp.cancelSubscription(disposable);
```
### 带有进度框的请求
带有进度框的请求，可以设置对话框消失是否自动取消网络和自定义对话框功能，具体参数作用请看请求回调讲解
#### 方式一：ProgressDialogCallBack
ProgressDialogCallBack带有进度框的请求，可以设置对话框消失是否自动取消网络和自定义对话框功能，具体参数作用请看自定义CallBack讲解
```
 IProgressDialog mProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("请稍候...");
                return dialog;
            }
        };
        EasyHttp.get("/v1/app/chairdressing/")
                .params("param1", "paramValue1")
                .execute(new ProgressDialogCallBack<SkinTestResult>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);//super.onError(e)必须写不能删掉或者忘记了
                        //请求失败
                    }

                    @Override
                    public void onSuccess(SkinTestResult response) {
                       //请求成功
                    }
                });
```
*注：错误回调 super.onError(e);必须写*
#### 方式二：ProgressSubscriber

```
IProgressDialog mProgressDialog = new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("请稍候...");
                return dialog;
            }
        };
 EasyHttp.get(URL)
                .timeStamp(true)
                .execute(SkinTestResult.class)
                .subscribe(new ProgressSubscriber<SkinTestResult>(this, mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(SkinTestResult skinTestResult) {
                        showToast(skinTestResult.toString());
                    }
                });

```

### 请求返回Observable
通过网络请求可以返回Observable，这样就可以很好的通过Rxjava与其它场景业务结合处理，甚至可以通过Rxjava的connect()操作符处理多个网络请求。例如：在一个页面有多个网络请求，如何在多个请求都访问成功后再显示页面呢？这也是Rxjava强大之处。
*注：目前通过execute(Class<T> clazz)方式只支持标注的ApiResult结构，不支持自定义的ApiResult*
示例：
```
Observable<SkinTestResult> observable = EasyHttp.get(url)
                .params("param1", "paramValue1")
                .execute(SkinTestResult.class);

        observable.subscribe(new BaseSubscriber<SkinTestResult>() {
            @Override
            public void onError(ApiException e) {
                showToast(e.getMessage());
            }

            @Override
            public void onNext(SkinTestResult skinTestResult) {
                showToast(skinTestResult.toString());
            }
        });
```

### 文件下载
本库提供的文件下载非常简单，没有提供复杂的下载方式例如：下载管理器、断点续传、多线程下载等，因为不想把本库做重。如果复杂的下载方式，还请考虑其它下载方案。
文件目录如果不指定,默认下载的目录为/storage/emulated/0/Android/data/包名/files
文件名如果不指定,则按照以下规则命名:
>1.首先检查用户是否传入了文件名,如果传入,将以用户传入的文件名命名
>2.如果没有传入文件名，默认名字是时间戳生成的。
>3.如果传入了文件名但是没有后缀，程序会自动解析类型追加后缀名

示例：
```
 String url = "http://61.144.207.146:8081/b8154d3d-4166-4561-ad8d-7188a96eb195/2005/07/6c/076ce42f-3a78-4b5b-9aae-3c2959b7b1ba/kfid/2475751/qqlite_3.5.0.660_android_r108360_GuanWang_537047121_release_10000484.apk";
        EasyHttp.downLoad(url)
                .savePath("/sdcard/test/QQ")
                .saveName("release_10000484.apk")//不设置默认名字是时间戳生成的
                .execute(new DownloadProgressCallBack<String>() {
                    @Override
                    public void update(long bytesRead, long contentLength, boolean done) {
                        int progress = (int) (bytesRead * 100 / contentLength);
                        HttpLog.e(progress + "% ");
                        dialog.setProgress(progress);
                        if (done) {//下载完成
                        }
                        ...
                    }

                    @Override
                    public void onStart() {
                       //开始下载
                    }

                    @Override
                    public void onComplete(String path) {
                       //下载完成，path：下载文件保存的完整路径
                    }

                    @Override
                    public void onError(ApiException e) {
                        //下载失败
                    }
                });
```

### POST请求，上传String、json、object、body、byte[]
一般此种用法用于与服务器约定的数据格式，当使用该方法时，params中的参数设置是无效的，所有参数均需要通过需要上传的文本中指定，此外，额外指定的header参数仍然保持有效。
- `.upString("这是要上传的长文本数据！")//默认类型是：MediaType.parse("text/plain")`
- 如果你对请求头有自己的要求，可以使用这个重载的形式，传入自定义的content-type文本
 `upString("这是要上传的长文本数据！", "application/xml") // 比如上传xml数据，这里就可以自己指定请求头`
- upJson该方法与upString没有本质区别，只是数据格式是json,通常需要自己创建一个实体bean或者一个map，把需要的参数设置进去，然后通过三方的Gson或者 fastjson转换成json字符串，最后直接使用该方法提交到服务器。
`.upJson(jsonObject.toString())//上传json`
- `.upBytes(new byte[]{})//上传byte[]`
- `.requestBody(body)//上传自定义RequestBody`
- `.upObject(object)//上传对象object`   必须要增加`.addConverterFactory(GsonConverterFactory.create())`设置


> 1.upString、upJson、requestBody、upBytes、upObject五个方法不能同时使用，当前只能选用一个
> 2.使用upJson、upObject时候params、sign(true/false)、accessToken（true/false）、拦截器都不会起作用


示例：
``` 
HashMap<String, String> params = new HashMap<>();
params.put("key1", "value1");
params.put("key2", "这里是需要提交的json格式数据");
params.put("key3", "也可以使用三方工具将对象转成json字符串");
JSONObject jsonObject = new JSONObject(params);

RequestBody body=RequestBody.create(MediaType.parse("xxx/xx"),"内容");
EasyHttp.post("v1/app/chairdressing/news/favorite")
                //.params("param1", "paramValue1")//不能使用params，upString 与 params 是互斥的，只有 upString 的数据会被上传
                .upString("这里是要上传的文本！")//默认类型是：MediaType.parse("text/plain")
                //.upString("这是要上传的长文本数据！", "application/xml") // 比如上传xml数据，这里就可以自己指定请求头
                
                 //.upJson(jsonObject.toString())
                 //.requestBody(body)
                 //.upBytes(new byte[]{})
                 //.upObject(object)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });

```

### 上传图片或者文件
>支持单文件上传、多文件上传、混合上传，同时支持进度回调，
>暂不实现多线程上传/分片上传/断点续传等高级功能

上传文件支持文件与参数一起同时上传，也支持一个key上传多个文件，以下方式可以任选
上传文件支持两种进度回调：ProgressResponseCallBack(线程中回调)和UIProgressResponseCallBack（可以刷新UI）
```
final UIProgressResponseCallBack listener = new UIProgressResponseCallBack() {
            @Override
            public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
                int progress = (int) (bytesRead * 100 / contentLength);
                if (done) {//完成
                }
                ...
            }
        };
        EasyHttp.post("/v1/user/uploadAvatar")
                //支持上传新增的参数
                //.params(String key, File file, ProgressResponseCallBack responseCallBack)
                //.params(String key, InputStream stream, String fileName, ProgressResponseCallBack responseCallBack)
                //.params(String key, byte[] bytes, String fileName, ProgressResponseCallBack responseCallBack) 
                //.addFileParams(String key, List<File> files, ProgressResponseCallBack responseCallBack)
                //.addFileWrapperParams(String key, List<HttpParams.FileWrapper> fileWrappers)
                //.params(String key, File file, String fileName, ProgressResponseCallBack responseCallBack)
                //.params(String key, T file, String fileName, MediaType contentType, ProgressResponseCallBack responseCallBack)
                
                //方式一：文件上传
                File file = new File("/sdcard/1.jpg");
                //如果有文件名字可以不用再传Type,会自动解析到是image/*
                .params("avatar", file, file.getName(), listener)
                //.params("avatar", file, file.getName(),MediaType.parse("image/*"), listener)

                //方式二：InputStream上传
               final InputStream inputStream = getResources().getAssets().open("1.jpg");
                .params("avatar", inputStream, "test.png", listener)
                
                //方式三：byte[]上传
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                final byte[] bytes = baos.toByteArray();
                //.params("avatar",bytes,"streamfile.png",MediaType.parse("image/*"),listener)
                //如果有文件名字可以不用再传Type,会自动解析到是image/*
                .params("avatar", bytes, "streamfile.png", listener)
        
                .params("file1", new File("filepath1"))   // 可以添加文件上传
	            .params("file2", new File("filepath2")) 	// 支持多文件同时添加上传
	            .addFileParams("key", List<File> files)	// 这里支持一个key传多个文件
                .params("param1", "paramValue1") 		// 这里可以上传参数
                .accessToken(true)
                .timeStamp(true)
                .execute(new ProgressDialogCallBack<String>(mProgressDialog, true, true) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        showToast(response);
                    }
                });
```
### 取消请求
#### 通过Disposable取消
每个请求前都会返回一个Disposable，取消订阅就可以取消网络请求，如果是带有进度框的网络请求，则不需要手动取消网络请求，会自动取消。
```
 Disposable mSubscription = EasyHttp.get(url).execute(callback);
  ...
  @Override
    protected void onDestroy() {
        super.onDestroy();
        EasyHttp.cancelSubscription(mSubscription);
    }
```
#### 通过dialog取消
自动取消使用ProgressDialogCallBack回调或者使用ProgressSubscriber,就不用再手动调用cancelSubscription();
ProgressDialogCallBack:
 ```
EasyHttp.get(url).execute(new ProgressDialogCallBack());
```
ProgressSubscriber
```
EasyHttp.get(url).execute(SkinTestResult.class).subscribe(new ProgressSubscriber<SkinTestResult>(this, mProgressDialog) {
            @Override
            public void onError(ApiException e) {
                super.onError(e);
                showToast(e.getMessage());
            }

            @Override
            public void onNext(SkinTestResult skinTestResult) {
                showToast(skinTestResult.toString());
            }
        })
```

### 同步请求
同步请求只需要设置syncRequest()方法
```
 EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                ...
                .syncRequest(true)//设置同步请求
                .execute(new CallBack<SkinTestResult>() {});
```

### 请求回调CallBack支持的类型
```
//支持回调的类型可以是Bean、String、CacheResult<Bean>、CacheResult<String>、List<Bean>
new SimpleCallBack<CacheResult<Bean>>()//支持缓存的回调，请看缓存讲解
new SimpleCallBack<CacheResult<String>>()//支持缓存的回调，请看缓存讲解
new SimpleCallBack<Bean>()//返回Bean
new SimpleCallBack<String>()//返回字符串
new SimpleCallBack<List<Bean>()//返回集合
```
*注：其它回调同理*

### cookie使用
cookie的内容主要包括：名字，值，过期时间，路径和域。路径与域一起构成cookie的作用范围,关于cookie的作用这里就不再科普，自己可以去了解
cookie设置：
```
EasyHttp.getInstance()
   				 ...
                  //如果不想让本库管理cookie,以下不需要
                .setCookieStore(new CookieManger(this)) //cookie持久化存储，如果cookie不过期，则一直有效
                 ...
```

- 查看url所对应的cookie

```
HttpUrl httpUrl = HttpUrl.parse("http://www.xxx.com/test");
CookieManger cookieManger = getCookieJar();
List<Cookie> cookies =  cookieManger.loadForRequest(httpUrl);
```

- 查看CookieManger所有cookie
 
```
PersistentCookieStore cookieStore= getCookieJar().getCookieStore();
List<Cookie> cookies1= cookieStore.getCookies();
```

- 添加cookie

```
Cookie.Builder builder = new Cookie.Builder();
Cookie cookie = builder.name("mCookieKey1").value("mCookieValue1").domain(httpUrl.host()).build();
CookieManger cookieManger = getCookieJar();
cookieManger.saveFromResponse(httpUrl, cookie);
//cookieStore.saveFromResponse(httpUrl, cookieList);//添加cookie集合
```

- 移除cookie

```
HttpUrl httpUrl = HttpUrl.parse("http://www.xxx.com/test");
CookieManger cookieManger = EasyHttp.getCookieJar();
Cookie cookie = builder.name("mCookieKey1").value("mCookieValue1").domain(httpUrl.host()).build();
cookieManger.remove(httpUrl,cookie);
```

- 清空cookie

```
CookieManger cookieManger = EasyHttp.getCookieJar();
cookieManger.removeAll();
```

### 自定义call()请求
提供了用户自定义ApiService的接口，您只需调用call方法即可.
示例：
```
public interface LoginService {
    @POST("{path}")
    @FormUrlEncoded
    Observable<ApiResult<AuthModel>> login(@Path("path") String path, @FieldMap Map<String, String> map);
}

final CustomRequest request = EasyHttp.custom()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))//自定义的可以设置GsonConverterFactory
                .params("param1", "paramValue1")
                .build();

        LoginService mLoginService = request.create(LoginService.class);
        LoginService mLoginService = request.create(LoginService.class);
        Observable<ApiResult<AuthModel>> observable = request.call(mLoginService.login("v1/account/login", request.getParams().urlParamsMap));
        Disposable subscription = observable.subscribe(new Action1<ApiResult<AuthModel>>() {
            @Override
            public void call(ApiResult<AuthModel> result) {
                //请求成功
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //请求失败
            }
        });
```

### 自定义apiCall()请求
提供默认的支持ApiResult结构，数据返回不需要带ApiResult,直接返回目标.
示例：
```
Observable<AuthModel> observable = request.apiCall(mLoginService.login("v1/account/login", request.getParams().urlParamsMap));
```
## 请求回调和订阅
请求回调本库提供两种方式Callback和Subscriber
### 回调方式
此种方式主要针对execute(CallBack<T> callBack)，目前内部提供的回调包含CallBack, SimpleCallBack ,ProgressDialogCallBack ,DownloadProgressCallBack 可以根据自己的需求去自定义Callback

- CallBack所有回调的基类，抽象类
- SimpleCallBack简单回调，只有成功和失败
- ProgressDialogCallBack带有进度框的回调，可以自定义进度框、支持是否可以取消对话框、对话框消失自动取消网络请求等参数设置
- DownloadProgressCallBack如果要做文件下载，则必须使用该回调，内部封装了关于文件下载进度回调的方法，如果使用其他回调也可以，但是没有进度通知

该网络框架的核心使用方法即为Callback的继承使用，因为不同的项目需求，会有个性化的回调请自定义
#### CallBack回调
```
new CallBack<T>() {
                    @Override
                    public void onStart() {
                       //请求开始
                    }

                    @Override
                    public void onCompleted() {
                       //请求完成
                    }

                    @Override
                    public void onError(ApiException e) {
                       //请求失败
                    }

                    @Override
                    public void onSuccess(T t) {
                       //请求成功
                    }
                }
```

#### SimpleCallBack回调

```
new SimpleCallBack<T>() {
                    @Override
                    public void onError(ApiException e) {
                         //请求失败
                    }

                    @Override
                    public void onSuccess(T t) {
                        //请求成功
                    }
                }
```

#### ProgressDialogCallBack回调
可以自定义带有加载进度框的回调，取消对话框会自动取消掉网络请求

提供两个构造
> public ProgressDialogCallBack(IProgressDialog progressDialog);//默认不能取消对话框
> public ProgressDialogCallBack(IProgressDialog progressDialog, boolean isShowProgress, boolean isCancel);//自定义加载进度框,可以设置是否显示弹出框，是否可以取消 progressDialog: dialog对象接口  isShowProgress：对话框消失是否取消网络请求 isCancel：是否可以取消对话框对应Dialog的setCancelable(isCancel)方法;

自定义ProgressDialog对话框
```
 private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("请稍候...");
            return dialog;
        }
    };
```
#### DownloadProgressCallBack回调
此回调只用于文件下载，具体请看文件下载讲解
#### 自定义CallBack回调
如果对回调有特殊需求，支持可以继承CallBack自己扩展功能

### 订阅方式
此种方式主要是针对execute(Class<T> clazz)和execute(Type type)，目前内部提供的Subscriber包含BaseSubscriber、DownloadSubscriber、ProgressSubscriber，可以根据自己的需求去自定义Subscriber
- BaseSubscriber所有订阅者的基类，抽象类
- DownloadSubscriber下载的订阅者，上层不需要关注
- ProgressSubscriber带有进度框的订阅，可以自定义进度框、支持是否可以取消对话框、对话框消失自动取消网络请求等参数设置

```
new BaseSubscriber<T>() {
            @Override
            public void onError(ApiException e) {
               //请求失败
            }

            @Override
            public void onNext(T t) {
                //请求成功
            }
        }
```

```
new ProgressSubscriber<T>(this, mProgressDialog) {
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        //请求失败
                    }

                    @Override
                    public void onNext(T t) {
                         //请求成功
                    }
                }
```

### 自定义Subscriber
如果对Subscriber有特殊需求，支持可以继承BaseSubscriber自己扩展订阅者
## 缓存使用
### 缓存介绍
本库的缓存主要分okhttp的Cache缓存和自定义的RxCache缓存,大家有疑问okhttp有缓存，retrofit也是支持通过header来设置缓存，为什么还要自定义一个缓存机制呢？通过自定义RxCache缓存使用更简单，更符合我们常用的业务需求(常用的缓存策略也不会太复杂)， retrofit的缓存借助于okhttp通过拦截器interceptor实现或者通过[`@Headers("Cache-Control: public, max-age=3600)`](http://www.jianshu.com/p/9c3b4ea108a7)具体用法这里不做详细描述，有兴趣的可以自己去了解。动态修改缓存时间不方便，例如：同一个接口，不同时间段请求的内容缓存的时间不一样，需要动态修改。

对于`DEFAULT`模式是okhttp的Cache缓存。因为该模式是完全遵循标准的http协议的,缓存时间是依靠服务端响应头来控制，也可以通过拦截器自己处理

对于RxCache的缓存支持多种存储方式，提供`IDiskConverter`转换器接口目前支持`SerializableDiskConverter`和`GsonDiskConverter`两种方式，也可以自定义Parcelable、fastjson、xml、kryo等转换器
**SerializableDiskConverter**
使用缓存前，必须让缓存的数据所有javaBean对象实现Serializable接口，否则会报NotSerializableException。 因为缓存的原理是将对象序列化后保存，如果不实现Serializable接口，会导致对象无法序列化，进而无法保存，也就达不到缓存的效果。
优点：存储和读取都不用再转化直接就是需要的对象速度快 
缺点：如果javabean里面还有javabean且层级比较多，也必须每个都要实现Serializable接口，比较麻烦
**GsonDiskConverter**
此种方式就是以json字符串的方式存储
优点：相对于SerializableDiskConverter转换器，存储的对象不需要进行序列化
缺点：就是存储和读取都要使用Gson进行转换，object->String->Object的给一个过程，相对来说每次都要转换性能略低，但是性能基本忽略不计

目前提供了八种CacheMode缓存模式,每种缓存模式都可以指定对应的CacheTime,将复杂常用的业务场景封装在里面，让你不用关心缓存的具体实现，而专注于数据的处理

- NO_CACHE：不使用缓存,该模式下,cacheKey,cacheTime 等参数均无效
- DEFAULT：按照HTTP协议的默认缓存规则，走OKhttp的Cache缓存
- FIRSTREMOTE：先请求网络，请求网络失败后再加载缓存
- FIRSTCACHE：先加载缓存，缓存没有再去请求网络
- ONLYREMOTE：仅加载网络，但数据依然会被缓存
- ONLYCACHE：只读取缓存，缓存没有会返回null
- CACHEANDREMOTE:先使用缓存，不管是否存在，仍然请求网络，CallBack会回调两次.
- CACHEANDREMOTEDISTINCT:先使用缓存，不管是否存在，仍然请求网络，CallBack回调不一定是两次，如果发现请求的网络数据和缓存数据是一样的，就不会再返回网络的回调,既回调一次。否则不相同仍然会回调两次。（目的是为了防止数据没有发生变化，也需要回调两次导致界面无用的重复刷新）,**此种模式缓存的对象bean一定要重写tostring()方法**

*注：无论对于哪种缓存模式，都可以指定一个cacheKey，建议针对不同需要缓存的页面设置不同的cacheKey，如果相同，会导致数据覆盖。*

### 缓存设置
缓存设置有两种方式
方式一：全局设置，所有请求都会默认使用此模式
```
 EasyHttp.getInstance()
 				...
                .setCacheMode(CacheMode.CACHEANDREMOTE)//不设置默认是NO_CACHE模式
                ...
```
方式二：单个请求设置缓存模式
```
 EasyHttp.get(URL)
 				...
                .cacheMode(CacheMode.FIRSTREMOTE)
                ...
```
### 设置转换器
方式一：全局设置，所有请求都会默认使用此存储转换器
```
EasyHttp.getInstance().setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
```
方式二：单个请求设置存储转换器
```
EasyHttp.get(URL).cacheDiskConverter(new GsonDiskConverter());
```
*注：一个请求就选用一种转换器，切记不要使用SerializableDiskConverter来缓存，又用GsonDiskConverter来读会报错*

### 自定义转换器
如果你想拥有自己的转换器请实现`IDiskConverter`接口。
示例：
```
public class CustomDiskConverter implements IDiskConverter {
    @Override
    public <T> T load(InputStream source, Type type) {
        //实现读功能
        return null;
    }

    @Override
    public boolean writer(OutputStream sink, Object data) {
        //实现写功能
        return false;
    }
}

```
### 缓存回调
对具有缓存的回调CallBack，如果你想知道当前的缓存是来自本地还是网络，只需要回调中加入CacheResult，其它和普通的网络请求方式一模一样。CacheResult中的isFromCache可以知道是否来自缓存，true：来自缓存，false：来自网络。请使用`new SimpleCallBack<CacheResult<T>>()` 也就是在你原有的T上包含一层CacheResult就可以了。如果不想用到isFromCache就不需要用CacheResult，直接使用`new SimpleCallBack<T>()`
带有CacheResult回调示例：
```
 EasyHttp.get(url)
                .readTimeOut(30 * 1000)//测试局部读超时30s
                .cacheMode(cacheMode)
                .cacheKey(this.getClass().getSimpleName())//缓存key
                .retryCount(5)//重试次数
                .cacheTime(5 * 60)//缓存时间300s，默认-1永久缓存  okhttp和自定义缓存都起作用
                //.okCache(new Cache());//okhttp缓存，模式为默认模式（CacheMode.DEFAULT）才生效
                //.cacheDiskConverter(new GsonDiskConverter())//默认使用的是 new SerializableDiskConverter();
                .cacheDiskConverter(new SerializableDiskConverter())//默认使用的是 new SerializableDiskConverter();
                .timeStamp(true)
                .execute(new SimpleCallBack<CacheResult<SkinTestResult>>() {

                    @Override
                    public void onError(ApiException e) {
                       //请求失败
                    }

                    @Override
                    public void onSuccess(CacheResult<SkinTestResult> cacheResult) {
                        HttpLog.i(cacheResult.toString());
                        String from = "";
                        if (cacheResult.isFromCache) {
                            from = "我来自缓存";
                        } else {
                            from = "我来自远程网络";
                        }
                       ....
                    }
                });
```
### 移除缓存
支持根据缓存key移除缓存，主要是针对RxCache才能起作用
```
EasyHttp.removeCache("cachekey");
```
### 清空缓存
```
EasyHttp.clearCache();
```

### RxCache
RxCache是自己封装的一个本地缓存功能库，采用Rxjava+DiskLruCache来实现，线程安全内部采用ReadWriteLock机制防止频繁读写缓存造成的异常，可以独立使用，单独用RxCache来存储数据。采用transformer与网络请求结合，可以实现网络缓存功能,本地硬缓存，具有缓存读写功能（异步）、缓存是否存在、根据key删除缓存、清空缓存（异步）、缓存Key会自动进行MD5加密、可以设置缓存磁盘大小、缓存key、缓存时间、缓存存储的转换器、缓存目录、缓存Version等功能本库不作为重点介绍。后期会将此代码独立开源一个库，作为一分钟让你自己的网络库也具有缓存功能，敬请期待！！！

## 动态参数
动态参数就是像我们的token、时间戳timeStamp、签名sign等，这些参数不能是全局参数因为是变化的，设置成局部参数又太麻烦，每次都要获取。token是有有效时间的或者异地登录等都会变化重新获取，时间戳一般是根据系统的时间，sign是根据请求的url和参数进行加密签名一般都有自己的签名规则。有的接口需要这些参数有的接口不需要，本库很好的解决这个问题。
#### 1.在请求的时候可以设置下面三个参数
```
.accessToken(true)//本次请求是否追加token
.timeStamp(false)//本次请求是否携带时间戳
.sign(false)//本次请求是否需要签名
```
#### 2.需要继承库中提供的动态拦截器BaseDynamicInterceptor
继承BaseDynamicInterceptor后就可以获取到参数的设置值，请详细看`CustomSignInterceptor`的注释讲解，也可以查看Demo示例
示例:
```
/**
 * <p>描述：对参数进行签名、添加token、时间戳处理的拦截器</p>
 * 主要功能说明：<br>
 * 因为参数签名没办法统一，签名的规则不一样，签名加密的方式也不同有MD5、BASE64等等，只提供自己能够扩展的能力。<br>
 * 作者： zhouyou<br>
 * 日期： 2017/5/4 15:21 <br>
 * 版本： v1.0<br>
 */
public class CustomSignInterceptor extends BaseDynamicInterceptor<CustomSignInterceptor> {
    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> dynamicMap) {
        //dynamicMap:是原有的全局参数+局部参数
        //你不必关心当前是get/post/上传文件/混合上传等，库中会自动帮你处理。
        //根据需要自己处理，如果你只用到token则不必处理isTimeStamp()、isSign()
        if (isTimeStamp()) {//是否添加时间戳，因为你的字段key可能不是timestamp,这种动态的自己处理
            dynamicMap.put(ComParamContact.Common.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        }
        if (isSign()) {是否签名
            //1.因为你的字段key可能不是sign，这种需要动态的自己处理
            //2.因为你的签名的规则不一样，签名加密方式也不一样，只提供自己能够扩展的能力
            dynamicMap.put(ComParamContact.Common.SIGN, sign(dynamicMap));
        }
        if (isAccessToken()) {//是否添加token
            String acccess = TokenManager.getInstance().getAuthModel().getAccessToken();
            dynamicMap.put(ComParamContact.Common.ACCESSTOKEN, acccess);
        }
        //Logc.i("dynamicMap:" + dynamicMap.toString());
        return dynamicMap;//dynamicMap:是原有的全局参数+局部参数+新增的动态参数
    }

    //示例->签名规则：POST+url+参数的拼装+secret
    private String sign(TreeMap<String, String> dynamicMap) {
        String url = getHttpUrl().url().toString();
        url = url.replaceAll("%2F", "/");
        StringBuilder sb = new StringBuilder("POST");
        sb.append(url);
        for (Map.Entry<String, String> entry : dynamicMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        sb.append(AppConstant.APP_SECRET);
        HttpLog.i(sb.toString());
        return MD5.encode(sb.toString());
    }
}
```
#### 3.设置自定义的动态拦截器
最好通过全局的方式设置，因为一般很多接口都会使用到
```
 EasyHttp.getInstance()
                 ...
                .addInterceptor(new CustomSignInterceptor())//添加动态参数（签名、token、时间戳）拦截器
                 ...
```
## 自定义ApiResult
本库中默认提供的是标准ApiResult.内部是靠ApiResult进行解析的，如果你的数据结构跟ApiResult不同，你可以在你的项目中继承ApiResult，然后重写getCode()、getData()、getMsg()和isOk()等方法来实现自己的需求。
本库中ApiResult如下：
```
public class ApiResult<T> {
    private int code;
    private String msg;
    private T data;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {//请求成功的判断方法
        return code == 0 ? true : false;
    }
}
```
json格式类似:
```
{
"code": 100010101,
"data": 内容,
"msg": "请求成功"
}
```
假如你的数据结构是这样的：
```
{
"error_code": 0,
"result": 内容,
"reason": "请求成功"
}
```
那么你的basebean可以写成这样
```
public class CustomApiResult<T> extends ApiResult<T> {
    String reason;
    int error_code;
    //int resultcode;
    T result;
    @Override
    public int getCode() {
        return error_code;
    }
    @Override
    public void setCode(int code) {
        error_code = code;
    }
    @Override
    public String getMsg() {
        return reason;
    }
    @Override
    public void setMsg(String msg) {
        reason = msg;
    }
    @Override
    public T getData() {
        return result;
    }
    @Override
    public void setData(T data) {
        result = data;
    }
   /* @Override
    public boolean isOk() {
        return error_code==200;//如果不是0表示成功，请重写isOk()方法。
    }*/
}
```
那么你的网络请求可以这样写
##### 自定义ApiResult回调方式（通过CallBackProxy代理）
```
EasyHttp.get(url)
                .readTimeOut(30 * 1000)//局部定义读超时
                .writeTimeOut(30 * 1000)
                .connectTimeout(30 * 1000)
                //.cacheKey(this.getClass().getSimpleName()+"11")
                //.cacheMode(CacheMode.CACHEANDREMOTE)
                //.cacheMode(CacheMode.ONLYREMOTE)
                //.headers("","")//设置头参数
                //.params("name","张三")//设置参数
                //.addInterceptor()
                //.addConverterFactory()
                //.addCookie()
                //.timeStamp(true)
                .baseUrl("http://apis.juhe.cn")
                .params("phone", "手机号")
                .params("dtype", "json")
                .params("key", "5682c1f44a7f486e40f9720d6c97ffe4")
                .execute(new CallBackProxy<CustomApiResult<ResultBean>, ResultBean>(new SimpleCallBack<ResultBean>() {
                    @Override
                    public void onError(ApiException e) {
                        //请求错误
                    }

                    @Override
                    public void onSuccess(ResultBean response) {
                        //请求成功
                    }
                }) {
                });
```

这种写法会觉得有点长，CallBackProxy的泛型参数每次都需要填写，其中CustomApiResult是继承ApiResult的，CustomApiResult相当于项目的basebean,对于一个实际项目来讲，basebean是固定的，所以我们可以继续封装这个方法，根据需要一般只需要封装get和post请求就可以了。
```
 public static <T> Disposable customExecute(CallBack<T> callBack) {
        return execute(new CallBackProxy<CustomApiResult<T>, T>(callBack) {
        });
    }
```

通过以上改造，再次调用时直接使用CallBack，不用再关注CallBackProxy，是不是明显简单很多了，具体请看代码Demo!!!
##### 自定义ApiResult订阅方式（通过CallClazzProxy代理）
```
Observable<ResultBean> observable = EasyHttp.get("/mobile/get")
                .readTimeOut(30 * 1000)//局部定义读超时
                .writeTimeOut(30 * 1000)
                .baseUrl("http://apis.juhe.cn")
                .params("phone", "18688994275")
                .params("dtype", "json")
                .params("key", "5682c1f44a7f486e40f9720d6c97ffe4")
                .execute(new CallClazzProxy<CustomApiResult<ResultBean>, ResultBean>(ResultBean.class) {
                });
        observable.subscribe(new ProgressSubscriber<ResultBean>(this, mProgressDialog) {
            @Override
            public void onError(ApiException e) {
                super.onError(e);
                showToast(e.getMessage());
            }

            @Override
            public void onNext(ResultBean result) {
                showToast(result.toString());
            }
        });
```
## 调试模式
一个好的库，一定有比较人性化的调试模式，为了方便开发者查看请求过程和请求日志，本库提供详细的日志打印，最好在开发阶段，请打开调试模式输出优雅的Log.
调试模式的控制在初始化配置时就可以直接设置。
```
public class MyApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            ...
            EasyHttp.getInstance()
            		...
                    // 打开该调试开关并设置TAG,不需要就不要加入该行
                    // 最后的true表示是否打印内部异常，一般打开方便调试错误
                    .debug("EasyHttp", true);
        }
    }
```
#### Log预览说明
这里一个成功请求的例子：
![](http://img.blog.csdn.net/20170608191720172)
上方的Log打印了一个Request完整的声明周期，一个请求的Log有以下特点：
1.开头和结尾打了-->http is start和 -->http is Complete分割请求，完整的生命周期的内容都会打印在开头和结尾的里面。
2.request请求和response响应分割，分别是
> -------------------------------request-------------------------------

> -------------------------------response-------------------------------

3.在---request---之后会打印请求的url、当前请求的类型GET/POST... -->GET/POST开头  -->END GET/POST结尾。如果是GET、HEAD请求方式添加的参数将会在这里完整的以url?key=value&key=value的形式打印。
4.在----response----之后会打印（在服务器响应后被打印），包含响应码、响应状态、响应头、cookie,body等以<--200(响应码)开头，<--END HTTP结尾
5.loadCache  key=如果设置了缓存，会看到缓存的key，开启了网络缓存功能才会输出。
6.loadCache result=从缓存里读取的结果，开启了网络缓存功能才会输出。
7.save status => true保存缓存的状态
## 混淆
```
#okhttp
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions

# Retrolambda
-dontwarn java.lang.invoke.*

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
###rxandroid-1.2.1
-keepclassmembers class rx.android.**{*;}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
-keep class org.xz_sale.entity.**{*;}
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

#RxEasyHttp
-keep class com.zhouyou.http.model.** {*;}
-keep class com.zhouyou.http.cache.model.** {*;}
-keep class com.zhouyou.http.cache.stategy.**{*;}
```
[请查看Demo中完整的混淆文件](https://github.com/zhou-you/RxEasyHttp/blob/master/app/proguard-rules.pro)

