package com.hxzk_bj_demo.network;

import com.google.gson.GsonBuilder;
import com.hxzk_bj_demo.common.MyApplication;


import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;


/**
 * Created by ${赵江涛} on 2018-1-18.
 * 作用:
 */

public class HttpRequest {


    //通过volatile关键字来确保安全，使用该关键字修饰的变量在被变更时会被其他变量可见
    private volatile static HttpRequest sHttpRequest = null;
    //请求服务器ip或域名>
    public static String BASE_URL="https://www.wanandroid.com/";
    /*public static String BASE_URL="http://ygcy.drugwebcn.com/";*/


    //Rotrofit
    private static Retrofit.Builder sRetrofit;
    //ServiceInterface
    private static ServiceInterface sServiceInterface;




    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    /**
     * class 无参构造
     */
    private HttpRequest() {
        sRetrofit = new Retrofit.Builder()
                .client(MyApplication.getOkHttpClientbBuild().build())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))//转Gson
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .addConverterFactory(ProtoConverterFactory.create())//转jsonObject
//                .addConverterFactory(ScalarsConverterFactory.create())//转string
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .baseUrl(BASE_URL);
        sServiceInterface=sRetrofit.build().create(ServiceInterface.class);
    }


    /**
     * 获取HttpRequest实例
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




    private static Subscription mSubscription;
    /** 观察者添加订阅 */
    public  <T>  Subscription toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        mSubscription=
                //指定 subscribe() 发生在新的线程
                observable.subscribeOn(Schedulers.io())
                //需要UI绘制后再进行订阅的场景，防止阻塞UI，我们需要延迟订阅执行
                .delay(2, TimeUnit.SECONDS)
                //取消发生在IO线程
                .unsubscribeOn(Schedulers.io())
                // 指定 Subscriber 的回调发生在主线程
                //.observeOn(AndroidSchedulers.mainThread())
                        .observeOn(Schedulers.from(new Executor(){
                            @Override
                            public void execute(Runnable command) {
                                runOnUiThread(command);
                            }
                        }))
                .subscribe(subscriber);

        return mSubscription;
    }




    /**
     * 观察者取消订阅
     */
    public  void unsubscribe(Observable observable) {
        //使用 isUnsubscribed() 先判断一下状态
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            //unsubscribe() 来解除引用关系，以避免内存泄露的发生
            mSubscription.unsubscribe();
        }
    }


}
