package com.android.git.core

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

private val testScheduler: TestScheduler = TestScheduler()

class RxSchedulerExtension : AfterAllCallback, BeforeAllCallback {

    companion object {
        fun triggerActions() = testScheduler.triggerActions()
    }

    override fun beforeAll(context: ExtensionContext?) {
        RxJavaPlugins.setInitIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setInitComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }
    }

    override fun afterAll(context: ExtensionContext?) {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }
}