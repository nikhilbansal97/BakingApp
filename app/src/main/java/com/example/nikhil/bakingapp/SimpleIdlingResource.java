package com.example.nikhil.bakingapp;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by NIKHIL on 24-12-2017.
 */

public class SimpleIdlingResource implements IdlingResource {

    AtomicBoolean isIdleNow = new AtomicBoolean(true);
    IdlingResource.ResourceCallback resourceCallback;

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    public void setIdleState(Boolean isIdle) {
        isIdleNow.set(isIdle);
        if (isIdle && resourceCallback != null)
            resourceCallback.onTransitionToIdle();
    }
}
