package com.hxzk_bj_demo.network;

import com.google.gson.GsonBuilder;
import com.hxzk_bj_demo.common.MyApplication;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${赵江涛} on 2018-1-18.
 * 作用:
 */

public class HttpRequest {


    //通过volatile关键字来确保安全，使用该关键字修饰的变量在被变更时会被其他变量可见
    private volatile static HttpRequest sHttpRequest = null;
    //请求服务器ip或域名
    public static String BASE_URL="http://192.168.1.112:8080/ygcy/";
    //public static String BASE_URL="http://ygcy.drugwebcn.com//ygcy/";

    //Rotrofit
    private static Retrofit.Builder sRetrofit;
    //ServiceInterface
    private static ServiceInterface sServiceInterface;




    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    /**
     * class 无参构造
     */
    public HttpRequest() {
        sRetrofit = new Retrofit.Builder()
                .client(MyApplication.getOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))//转Gson
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ProtoConverterFactory.create())//转jsonObject
                .addConverterFactory(ScalarsConverterFactory.create())//转string
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .baseUrl(BASE_URL);
        sServiceInterface=sRetrofit.build().create(ServiceInterface.class);
    }




    /**
     * 获取HttpRequest对象实例
     *
     // DCL线程安全实现--volatile实现
     //懒汉式多线程容易出现问题，线程不安全，最简单方法是用同步块synchronized，但会导致较大的性能损失
     //DCL非线程安全的实现，不加volatile关键字,涉及指令重排序，指令重排序是JVM为了优化指令，允许可以在不影响单线程程序执行结果前提下进行
     //最终采用DCL线程安全实现--volatile实现
     * @return
     */
   public static HttpRequest getInstance() {
        if (sHttpRequest == null) {
            synchronized (HttpRequest.class) {
                if (sHttpRequest == null) {
                    sHttpRequest = new HttpRequest();
                }
            }
        }
        return sHttpRequest;
    }


    /**
     * 获取接口对象
     * @return
     */
    public ServiceInterface getServiceInterface() {
        return sServiceInterface;
    }


    private static  Subscription mSubscription;

    /** 给观察者模式添加订阅 */
    public <T> Subscription toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        mSubscription= observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        return mSubscription;
    }



}
