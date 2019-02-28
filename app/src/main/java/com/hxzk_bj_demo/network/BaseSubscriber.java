package com.hxzk_bj_demo.network;

import android.util.Log;
import android.widget.Toast;

import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.ui.activity.base.BaseActivity;
import com.hxzk_bj_demo.utils.NetWorkUtil;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * c参考：https://mp.weixin.qq.com/s/I2w-sywcX3guj280Ge1Ifw
 * 作者：created by ${zjt} on 2019/2/28
 * 描述:统一对Subscriber对网络返回进行处理和， 有无网络做判断，甚至可以根据需求显示加载进度等
 * 只处理start()和onCompleted（） ，上层处理时只处理onError（）和onNext（）
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private BaseActivity context;


    public BaseSubscriber(BaseActivity context) {
        this.context = context;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (NetWorkUtil.isNetworkAvailable(context)) {
            // 当前网络不可用，请检查网络情况",
            ToastCustomUtil.showLongToast(context.getResources().getString(R.string.empty_network_error));
            //一定要主动调用下面这一句
            onCompleted();
            return;
        }
        //此处可以显示进度条

    }

    @Override
    public void onCompleted() {
        //此处可以关闭进度条

    }

    //如果想对Error错误统一处理，也可以在BaseSubscriber处理onError()，然后回调搭到callback上层
    @Override
    public void onError(Throwable e) {
        Log.e("Tamic", e.getMessage());
        // todo error somthing

        if(e instanceof ExceptionHandle.ResponeThrowable){
            onError(e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }



    //通过RXJva的 Func1来进行对原始的Throwable 进行包装转换
    private static class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override public Observable<T> call(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }

    }
   //将原来Throwable 强转成自定义的 ResponeThrowable
   public static class ResponeThrowable extends Exception {
       public int code;
       public String message;

       public ResponeThrowable(Throwable throwable, int code) {           super(throwable);
           this.code = code;

       }
   }
     //用observable提供的onErrorResumeNext 则可以将你自定义的Func1 关联到错误处理类中
     //((Observable) observable).onErrorResumeNext(new HttpResponseFunc<T>());
}
