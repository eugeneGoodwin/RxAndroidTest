package com.test.my.rxandroidtest.model;

import com.test.my.rxandroidtest.interfaces.API;
import com.test.my.rxandroidtest.interfaces.Callback;
import com.test.my.rxandroidtest.presenters.BasePresenter;
import com.test.my.rxandroidtest.retrofit.entries.JsonPost;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android-dev on 22.08.2016.
 */
public class NetModel<T extends BasePresenter> {
    private static NetModel netModel;
    public static final String SERVER_ENDPOINT = "https://jsonplaceholder.typicode.com/";

    T viewModel;

    public static <T extends BasePresenter> NetModel instance(T t) {
        if (netModel == null) {
            netModel = new NetModel(t);
        }
        return netModel;
    }

    private static API api;

    public static API getApi() {
        return api;
    }

    public NetModel(T t){
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(SERVER_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
        viewModel = t;
    }

    public void getPosts(Callback<List<JsonPost>> callback){
        List<JsonPost> posts = new ArrayList<>();
        getApi().getPosts().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(list -> rx.Observable.from(list))
                .toList()
                .subscribe(listPost ->  { posts.addAll(listPost);
                                            callback.T(posts);},
                            error ->viewModel.retrofitError(null, error)
                );
    }
}
