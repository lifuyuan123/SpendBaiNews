package com.example.lfy.spendbainews.http;



import com.example.lfy.spendbainews.Utils.NetworkUtils;
import com.example.lfy.spendbainews.entity.BaseResponse;
import com.example.lfy.spendbainews.entity.Config;

import io.reactivex.disposables.Disposable;
import retrofit2.adapter.rxjava.HttpException;


/**
 *
 */
public abstract class ApiBaseResponseCallback<T> extends ApiCallBack<BaseResponse<T>> {

    public abstract void onSuccessful(T t);

    public abstract void onFailure(String msg);

    public abstract void onFinish();

    private Disposable disposable;

    private static final String MESERROR = "系统错误";
    public static final String EMPTY = "EMPTY";



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
            } else if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onFailure(msg);
        } else {
            if (!NetworkUtils.getDataEnabled() && !NetworkUtils.getWifiEnabled()) {
                onFailure(Config.NONETWORK);
            } else
                onFailure(e.getMessage());
        }
        onFinish();
    }

    @Override
    public void OnError(String msg) {
        if (msg!=null)
        onFailure(msg);
    }


    @Override
    public void OnSuccess(BaseResponse<T> baseResponse) {

        if (baseResponse == null) {
            onFailure(EMPTY);
            return;
        }
        if (baseResponse.isSuccess())
            onSuccessful(baseResponse.getResult());
        else
            onFailure(baseResponse.msg == null ? MESERROR : baseResponse.msg);

    }

    @Override
    public void OnFinish() {
        onFinish();
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        OnSuccess(baseResponse);
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.setDisposable(d);
    }

    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }

    public Disposable getDisposable() {
        return this.disposable;
    }
}
