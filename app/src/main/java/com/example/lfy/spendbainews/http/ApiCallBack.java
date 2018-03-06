package com.example.lfy.spendbainews.http;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava.HttpException;

/**
 *抽象类  暴露出抽象方法给子类操作
 */

abstract public class ApiCallBack<T>  implements Observer<T> {

    public Disposable getDisposable() {
        return disposable;
    }
    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }
    //是否在订阅
    private Disposable disposable;

    abstract public void OnSuccess(T value);
    abstract public void OnError(String msg);
    abstract public void OnFinish();



    //是否在订阅
    @Override
    public void onSubscribe(Disposable d) {
        this.disposable=d;

    }

    //正常接收数据调用
    @Override
    public void onNext(T value) {
     OnSuccess(value);
    }

    //发生错误调用
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();

            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            OnError(msg);
        } else {
            OnError(e.getMessage());
        }
        OnFinish();
    }
    //数据接收完成时调用
    @Override
    public void onComplete() {
        OnFinish();
    }
}
