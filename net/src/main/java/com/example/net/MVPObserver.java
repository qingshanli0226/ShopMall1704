package com.example.net;

import com.example.commen.util.ErrorUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MVPObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        ErrorUtil.handlerError(e);
    }

    @Override
    public void onComplete() {

    }
}
