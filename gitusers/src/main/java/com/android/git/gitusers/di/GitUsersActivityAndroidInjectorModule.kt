package com.android.git.gitusers.di

import androidx.lifecycle.LifecycleOwner
import com.android.git.core.di.PerActivity
import com.android.git.gitusers.ui.GitUsersActivity
import com.android.git.gitusers.ui.GitUsersContract
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface GitUsersActivityAndroidInjectorModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [GitUsersActivityBindingModule::class, GitUsersViewModule::class])
    fun bind(): GitUsersActivity
}

@Module
interface GitUsersActivityBindingModule {
    @Binds
    fun bindLifecycleOwner(activity: GitUsersActivity): LifecycleOwner

    @Binds
    fun bindContractView(activity: GitUsersActivity): GitUsersContract.View
}
