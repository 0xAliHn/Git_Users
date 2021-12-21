package com.android.git.gitusers.data

import com.android.git.core.RxSchedulerExtension
import com.android.git.gitusers.domain.IGitUsersRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Call

@ExtendWith(RxSchedulerExtension::class)
internal class GitUsersRepositoryTest {
    private val apiService: GitUsersApiService = mock()
    private val call = mock<Call<Response>>()

    private val repository: IGitUsersRepository = GitUsersRepository(apiService)


    @Test
    fun `verify apiService is called when getMarketPriceData is invoked`() {
        val query = "q"
        val page = 1
        whenever(apiService.getSearchedGitUsers(any(), any())).thenReturn(call)

        repository.getSearchedGitUsersData(query, page)
            .test()
            .also {
                verify(apiService).getSearchedGitUsers(query, page)
            }
    }
}