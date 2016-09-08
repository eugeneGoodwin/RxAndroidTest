package com.test.my.rxandroidtest.presenters;

import android.app.ProgressDialog;
import android.content.Context;

import com.test.my.rxandroidtest.R;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Android-dev on 22.08.2016.
 */
public class BaseProgressPresenter<T extends BasePresenterInterface> extends BasePresenter<T>{
    static ProgressDialog progress;

    public void showProgress() {
        Context context = view.getContext();
        if (progress == null) {
            progress = ProgressDialog.show(context, "", context.getString(R.string.loading));
        } else {
            progress.show();
        }
    }

    public void hideProgress() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
    }

    @Override
    public void retrofitError(Response res, Throwable ex){
        if (res != null) {
            if(res.code() == 403){
                // relogin
            }
            else if(res.code() == 504){
                // no internet connection
                //Context context = view.getContext();
            }
        }
        if (ex != null) {
            // exception
        }
    }
}
