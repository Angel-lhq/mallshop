package com.example.mallshop.utils;

import com.example.mallshop.common.Contant;
import com.example.mallshop.net.ApiService;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {
    private static volatile HttpUtil httpUtil = null;

    private HttpUtil() {
    }

    private Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new LogInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .addNetworkInterceptor(new NetInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .cache(new Cache(new File(Contant.PATH_CACHE),1024*1024*4))
                .retryOnConnectionFailure(true)
                .build();
    }


    public static HttpUtil getInstance() {
        if (httpUtil == null){
            synchronized (HttpUtil.class){
                if (httpUtil == null){
                    httpUtil = new HttpUtil();
                }
            }
        }
        return httpUtil;
    }

    private static volatile ApiService apiService = null;

    public ApiService getApiService() {
        if (apiService == null){
            synchronized (HttpUtil.class){
                if (apiService == null){
                    apiService = getRetrofit(ApiService.baseUrl).create(ApiService.class);
                }
            }
        }
        return apiService;
    }

    /**
     * 日志拦截器
     */
    static class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            LogUtil.logi(String.format("Sending request %s on %s%n%s",request.url(),chain.connection(),request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            LogUtil.logi(String.format("Received response for %s in %.1fms%n%s",response.request().url(),(t2-t1)/1e6d,response.headers()));
            LogUtil.logi(response.peekBody(1024*1024*4).string());
            return response;
        }
    }
    /**
     * 添加请求头
     */
    static class HeaderInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            String token = SpUtils.getInstance().getString("token");
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("token",token);
            Request request = builder.build();
            Response response = chain.proceed(request);
            return response;
        }
    }
    //需要头参数的接口
    private static String[] outHeaderUrl = {"login","register"};
    private static String signUrl = "cdwan.cn"; //需要签名的域名

    /**
     * 添加签名拦截器
     */
    static class SignInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            String url = chain.request().url().toString();
            String host = chain.request().url().host();
            //判断对应的url接口是否需要添加头信息
            boolean bool = false;
            for(int i=0; i<outHeaderUrl.length; i++){
                if(url.indexOf(outHeaderUrl[i]) != -1){
                    bool = true;
                    break;
                }
            }
            if(signUrl.equals(host) && !bool){
//                String token = SpUtils.getInstance().getString("token");
                //签名处理
                int timestamp = (int) (new Date().getTime()/1000);
                //随机字符
                String nonce = UUID.randomUUID().toString();
                //保存需要签名验证的参数
                //消息头
                Map<String,String> map = new HashMap<>();
                map.put("timestamp",String.valueOf(timestamp));
                map.put("nonce",nonce);

                //地址中的参数获取
                HttpUrl.Builder urlBuilder = chain.request().url().newBuilder();
                //获取get请求的地址参数
                Set<String> paramKeys = urlBuilder.build().queryParameterNames();
                for(String key:paramKeys){
                    String value = urlBuilder.build().queryParameter(key);
                    map.put(key, URLDecoder.decode(value));
                }

                //如果当前是post请求
                if("POST".equals(chain.request().method())){
                    //获取当前网络请求中的参数和值
                    FormBody formBody = (FormBody) chain.request().body();
                    if(formBody != null){
                        for(int i=0; i<formBody.size(); i++){
                            map.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i)));
                        }
                    }
                }

//                //获取需要签名的参数字符串
//                String source = DigestUtils.getSignSource(map);
//                //通过参数字符串获取对应的签名
//                String sign = DigestUtils.Encrypt(source,null);
//                //获取请求对象
                Request.Builder builder = chain.request().newBuilder();
//                builder.addHeader("token",token);
//                builder.addHeader("sign",sign);
//                builder.addHeader("timestamp", String.valueOf(timestamp));
//                builder.addHeader("nonce",nonce);
                Request request = builder.build();
                Response response = chain.proceed(request);
                //Log.i("response:",response.body().string());
                return response;
            }else{
                return chain.proceed(chain.request());
            }
        }
    }

    /**
     * 网络拦截器
     */
    static class NetInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!SystemUtils.checkNetWork()){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            //通过判断网络连接是否存在获取本地或者服务器的数据
            if(!SystemUtils.checkNetWork()){
                int maxAge = 0;
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public ,max-age="+maxAge).build();
            }else{
                int maxStale = 60*60*24*28; //设置缓存数据的保存时间
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public, onlyif-cached, max-stale="+maxStale).build();
            }
        }
    }
}
