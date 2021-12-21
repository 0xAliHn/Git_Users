package com.android.git.core

import android.os.Looper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class Interactor<Params, Type>(
    private val scheduler: Scheduler = Schedulers.io(),
    private val subscribeOnSchedulerPredicate: () -> Boolean = { isOnMainThread() }
) {

    protected abstract fun run(params: Params): Observable<Type>

    operator fun invoke(params: Params): Observable<Type> {
        return if (subscribeOnSchedulerPredicate()) {
            run(params)
                .subscribeOn(scheduler)
        } else {
            run(params)
        }
    }
}
operator fun <Type> Interactor<Unit, Type>.invoke(): Observable<Type> = this(Unit)

fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()
