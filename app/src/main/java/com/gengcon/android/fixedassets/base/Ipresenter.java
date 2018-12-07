package com.gengcon.android.fixedassets.base;

import io.reactivex.Observable;
import io.reactivex.Observer;

public interface Ipresenter<T extends Iview> {

    void attachView(T view);

    void detachView();

    void subscribe(Observable observable, Observer subscriber);

    void unsubscribe();

}
