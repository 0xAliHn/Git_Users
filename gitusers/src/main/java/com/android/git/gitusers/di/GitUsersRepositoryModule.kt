package com.android.git.gitusers.di

import com.android.git.core.network.ApiFactory
import com.android.git.gitusers.data.GitUsersApiService
import com.android.git.gitusers.data.GitUsersRepository
import com.android.git.gitusers.domain.IGitUsersRepository
import dagger.Module
import dagger.Provides

@Module
object GitUsersRepositoryModule {
    @Provides
    fun provideGitUsersRepository(apiFactory: ApiFactory): IGitUsersRepository =
        GitUsersRepository(apiFactory.create(GitUsersApiService::class.java))
}