package com.hxzk_bj_demo.network;

import android.content.Context;
import com.hxzk_bj_demo.R;
import com.hxzk_bj_demo.utils.NetWorkUtil;
import com.hxzk_bj_demo.utils.ProgressDialogUtil;
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * c参考：https://mp.weixin.qq.com/s/I2w-sywcX3guj280Ge1Ifw
 * 作者：created by ${zjt} on 2019/2/28
 * 描述:统一对Subscriber对网络返回进行处理和， 有无网络做判断，甚至可以根据需求显示加载进度等
 * 只处理start()和onCompleted（） ，上层处理时只处理onError（）和onNext（）
 */
public abstract class BaseSubscriber <T> extends Subscriber<T> {

    private Context context;


    public BaseSubscriber(Context context) {
        this.context = context;

    }


    @Override
    public void onStart() {
        super.onStart();
       if (!NetWorkUtil.isNetworkAvailable(context)) {
            // 当前网络不可用，请检查网络情况",
            ToastCustomUtil.showLongToast(context.getResources().getString(R.string.empty_network_error));
            return;
       }
        //此处可以打开加载框
        ProgressDialogUtil.getInstance().mshowDialog(context);
    }

    @Override
    public void onCompleted() {
         //此处可以关闭进度条
        ProgressDialogUtil.getInstance().mdismissDialog();
    }

    @Override
    public void onNext(T t) {
        onResult(t);
    }
     public abstract void onResult(T t);




    //如果想对Error错误统一处理，也可以在BaseSubscriber处理onError()，然后回调搭到callback上层
//    @Override
//    public void onError(Throwable e) {
//        if(e instanceof ExceptionHandle.ResponeThrowable){
//            onFail((ExceptionHandle.ResponeThrowable) e);
//        } else {
//            onFail(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
//        }
//    }
//   public abstract void onFail(ExceptionHandle.ResponeThrowable e);


    @Override
    public void onError(Throwable e) {
        if(ProgressDialogUtil.getInstance().xDialogInstance().isShowing()){
            ProgressDialogUtil.getInstance().mdismissDialog();
        }
        onFail(e);
    }
    public abstract void onFail(Throwable e);


    //通过RXJva的 Func1来进行对原始的Throwable 进行包装转换将原来Throwable 强转成自定义的 ResponeThrowable
    public static class HttpResponseFunc<T> implements Func1<Throwable, Observable> {
        @Override public Observable<T> call(Throwable t) {
            return Observable.error(ExceptionHandle.handleException(t));
        }

    }


    /**
     * 统一线程处理
     *
     * @param <T> 指定的泛型类型
     * @return ObservableTransformer
     */
    public static <T> ObservableTransformer<T, T> Obs_io_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(io.reactivex.Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
