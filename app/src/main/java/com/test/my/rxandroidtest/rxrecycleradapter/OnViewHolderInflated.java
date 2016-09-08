package com.test.my.rxandroidtest.rxrecycleradapter;

import android.view.View;
import android.view.ViewGroup;

public abstract class OnViewHolderInflated {
    public abstract void onInflated(final View view, final ViewGroup parent, final int viewType);
}