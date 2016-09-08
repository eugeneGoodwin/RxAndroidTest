package com.test.my.rxandroidtest.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import android.widget.TextView;

import com.test.my.rxandroidtest.R;
import com.test.my.rxandroidtest.databinding.ActivityMainBinding;
import com.test.my.rxandroidtest.presenters.MainPresenter;


public class MainActivity extends AppCompatActivity implements MainPresenter.View{

    private ActivityMainBinding mBinding;
    private MainPresenter mPresenter;

    @Override
    public RecyclerView getRecyclerView(){
        return mBinding.recyclerView;
    }

    @Override
    public TextView getStatus(){
        return mBinding.textView;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mPresenter = new MainPresenter();
        mBinding.button.setOnClickListener(v -> mPresenter.start());

        mPresenter.attachView(this);
        mPresenter.init();
    }
}
