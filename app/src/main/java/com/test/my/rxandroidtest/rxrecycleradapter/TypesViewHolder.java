package com.test.my.rxandroidtest.rxrecycleradapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class TypesViewHolder<T> extends RecyclerView.ViewHolder {
    private ViewDataBinding mViewDataBinding;

    public T getItem() {
        return mItem;
    }

    public ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    private T mItem;

    protected void setItem(final T item) {
        mItem = item;
    }

    public TypesViewHolder(final View itemView) {
        super(itemView);
        mViewDataBinding = DataBindingUtil.bind(itemView);
    }
}
