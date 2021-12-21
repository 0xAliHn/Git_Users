package com.android.git.core.lifecycle

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

class RxSubscriptionManager @Inject constructor() : LifecycleSubscriptionManager, LifecycleTearDownAwareComponent {

    private var disposableMap: MutableMap<LifecycleEvent, CompositeDisposable> = mutableMapOf()

    override fun onPause() = clearSubscriptions(LifecycleEvent.PAUSE)

    override fun onStop() = clearSubscriptions(LifecycleEvent.STOP)

    override fun onDestroy() = clearSubscriptions(LifecycleEvent.DESTROY)

    private fun clearSubscriptions(event: LifecycleEvent) {
        disposableMap[event]?.clear()
        when (event) {
            LifecycleEvent.DESTROY -> clearSubscriptions(LifecycleEvent.STOP)
            LifecycleEvent.STOP -> clearSubscriptions(LifecycleEvent.PAUSE)
            else -> {}
        }
    }

    override fun addDisposable(disposable: Disposable, unsubscribeEvent: LifecycleEvent) {
        getOrMakeCompositeDisposable(unsubscribeEvent)
            .add(disposable)
    }

    private fun getOrMakeCompositeDisposable(event: LifecycleEvent): CompositeDisposable {
        return disposableMap.getOrElse(event) {
            CompositeDisposable().apply { disposableMap[event] = this }
        }
    }
}