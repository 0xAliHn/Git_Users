package com.android.git.gitusers.di

import androidx.lifecycle.LifecycleOwner
import com.android.git.gitusers.domain.GetSearchedGitUsersViewDataInteractor
import com.android.git.gitusers.ui.GitUsersContract
import com.android.git.gitusers.ui.GitUsersPresenter
import com.android.git.gitusers.ui.model.QueryData
import dagger.Module
import dagger.Provides

@Module(includes = [GitUsersRepositoryModule::class])
object GitUsersViewModule {

    @Provides
    fun provideGitUsersPresenter(
        view: GitUsersContract.View,
        lifecycleOwner: LifecycleOwner,
        getSearchedGitUsersViewDataInteractor: GetSearchedGitUsersViewDataInteractor,
        queryData: QueryData
    ): GitUsersContract.Presenter =
        GitUsersPresenter(
            view,
            lifecycleOwner,
            getSearchedGitUsersViewDataInteractor,
            queryData
        )

    @Provides
    fun provideQueryData() = QueryData()
}

