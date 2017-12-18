package com.example.nikhil.bakingapp

import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by NIKHIL on 17-12-2017.
 */

class SimpleIdlingResource: IdlingResource {

    var isIdleNow = AtomicBoolean(true)
    var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return this.javaClass.name
    }

    override fun isIdleNow(): Boolean {
        return isIdleNow.get()

    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

    fun setIdleState(isIdle: Boolean) {
        isIdleNow.set(isIdle)
        if (isIdle && resourceCallback != null) {
            resourceCallback!!.onTransitionToIdle()
        }
    }

}