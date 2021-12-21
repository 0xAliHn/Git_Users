package com.android.git.core.lifecycle

import io.reactivex.rxjava3.disposables.Disposable

interface LifecycleSubscriptionManager {
    /**
     * Add the [disposable] to be disposed in the lifecycle phase specified by [unsubscribeEvent]
     */
    fun addDisposable(disposable: Disposable, unsubscribeEvent: LifecycleEvent)
}
