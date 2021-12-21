package com.android.git.core

import androidx.annotation.CallSuper
import com.android.git.core.lifecycle.AppLifecycleObserver
import com.android.git.core.lifecycle.LifecycleEvent.*
import com.android.git.core.lifecycle.LifecycleSubscriptionManager
import com.android.git.core.lifecycle.RxSubscriptionManager
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter(private val rxSubscriptionManager: RxSubscriptionManager = RxSubscriptionManager()) : LifecycleSubscriptionManager by rxSubscriptionManager,
    AppLifecycleObserver() {

    @CallSuper
    override fun onPause() = rxSubscriptionManager.onPause()

    @CallSuper
    override fun onStop() = rxSubscriptionManager.onStop()

    @CallSuper
    override fun onDestroy() = rxSubscriptionManager.onDestroy()
}
