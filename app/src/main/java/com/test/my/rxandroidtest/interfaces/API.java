package com.test.my.rxandroidtest.interfaces;

import com.test.my.rxandroidtest.retrofit.entries.JsonPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Android-dev on 22.08.2016.
 */
public interface API {
    @GET("/posts/")
    Observable<List<JsonPost>> getPosts();
}
