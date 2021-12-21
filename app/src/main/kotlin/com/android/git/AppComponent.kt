package com.android.git

import android.app.Application
import com.android.git.core.di.NetworkModule
import com.android.git.core.di.PerApplication
import com.android.git.gitusers.di.GitUsersActivityAndroidInjectorModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

/**
 * Application AppComponent for generating connecting dagger object graph for all module
 */
@PerApplication
@Component(modules = [
    AndroidInjectionModule::class,
    GitUsersActivityAndroidInjectorModule::class,
    NetworkModule::class]
)
interface AppComponent {

    fun inject(application: MainApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}