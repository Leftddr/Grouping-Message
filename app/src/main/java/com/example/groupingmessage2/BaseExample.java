package com.example.groupingmessage2;

import java.util.function.Consumer;

import io.reactivex.disposables.Disposable;
import sdk.guru.common.DisposableMap;

public class BaseExample {
    // Add the disposables to a map so you can dispose of them all at one time
    protected DisposableMap dm = new DisposableMap();

    public void accept(final Throwable throwable) {
        return;
    }
}
