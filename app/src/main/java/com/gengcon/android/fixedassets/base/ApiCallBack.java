package com.gengcon.android.fixedassets.base;



import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class ApiCallBack<M> implements Observer<M> {

    private Disposable disposable;

    public abstract void onSuccess(M modelBean);

    public abstract void onFailure(int status, String errorMsg);

    public abstract void onFinished();

    public abstract void onStart();

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        onStart();
    }

    @Override
    public void onNext(M modelBean) {
        onSuccess(modelBean);
    }

    @Override
    public void onComplete() {
        onFinished();
        dispose();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int exceptionCode = httpException.code();
            String msg = httpException.getMessage();
            if (exceptionCode == 401) {
                msg = "用户名密码错误，请重新登录！";
            }
            if (exceptionCode == 403 || exceptionCode == 404 || exceptionCode == 407 || exceptionCode == 408) {
                msg = "网络链接超时，请稍后再试！";
            }
            if (exceptionCode == 501 || exceptionCode == 502 || exceptionCode == 504) {
                msg = "服务器无响应，请稍后再试！";
            }
            onFailure(exceptionCode, msg);
        } else {
            onFailure(-1, e.getMessage());
        }
        onFinished();
        dispose();
    }

    private void dispose(){
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
