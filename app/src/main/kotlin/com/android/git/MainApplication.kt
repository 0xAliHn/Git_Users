package com.android.git

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

open class MainApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDaggerGraph()
        setupLogger()
    }

    private fun initDaggerGraph() {
        component = DaggerAppComponent.factory().create(this)
        component.inject(this)
    }

    private fun setupLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}