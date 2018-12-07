package com.gengcon.android.fixedassets.base;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter<T extends Iview> implements Ipresenter<T> {
    protected T mMvpView;//所有View

    public T getMvpView() {
        return mMvpView;
    }

    @Override
    public void attachView(T view) {
        this.mMvpView = view;
    }

    @Override
    public void detachView() {
        unsubscribe();
        this.mMvpView = null;
    }

    @Override
    public void unsubscribe() {

    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public void subscribe(Observable observable, Observer subscriber) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber);
    }

}
