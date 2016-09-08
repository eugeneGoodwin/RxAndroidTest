package com.test.my.rxandroidtest.presenters;

import retrofit2.Response;

public abstract class BasePresenter<T extends BasePresenterInterface> {

    protected T view;

    public final void attachView(T t) {
        this.view = t;
    }

    public final void detachView() {
        view = null;
    }

    public abstract void retrofitError(Response res, Throwable ex);
}
