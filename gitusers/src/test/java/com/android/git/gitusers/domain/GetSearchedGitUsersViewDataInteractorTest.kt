package com.android.git.gitusers.domain

import com.android.git.core.RxSchedulerExtension
import com.android.git.core.RxSchedulerExtension.Companion.triggerActions
import com.android.git.gitusers.data.SearchedUsers
import com.android.git.gitusers.domain.mapper.GitUsersViewDataMapper
import com.android.git.gitusers.ui.model.QueryData
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Observable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(RxSchedulerExtension::class)
internal class GetSearchedGitUsersViewDataInteractorTest {
    private val repository: IGitUsersRepository = mock()

    private val interactor: GetSearchedGitUsersViewDataInteractor = GetSearchedGitUsersViewDataInteractor(repository)

    private val query = "a"
    private val page = 1
    private val queryData = QueryData("a", 1)
    private val searchedUsers = listOf<SearchedUsers>(
        SearchedUsers(login = "aaa", avatar_url = "avatar_url", url = "url")
    )
    private val searchedUsersViewData = GitUsersViewDataMapper.mapToViewData(searchedUsers)

    @Test
    fun `verify getSearchedGitUsersData() is invoked and domain data is mapped to view data`() {
        whenever(repository.getSearchedGitUsersData(query, page)).thenReturn(Observable.just(searchedUsers))

        interactor(queryData).test()
            .apply { triggerActions() }
            .assertResult(searchedUsersViewData)
    }

    @Test
    fun `verify getSearchedGitUsersData() is invoked which emits null or error status then complete without data`() {
        whenever(repository.getSearchedGitUsersData(query, page)).thenReturn(Observable.just(listOf<SearchedUsers>()))

        interactor(queryData).test()
            .apply { triggerActions() }
            .assertNoErrors()
            .assertComplete()
    }

    @Test
    fun `verify getSearchedGitUsersData() is invoked which emits exception then should not complete`() {
        val error = Exception("error")

        whenever(repository.getSearchedGitUsersData(query, page)).thenReturn(Observable.error(error))

        interactor(queryData).test()
            .apply { triggerActions() }
            .assertError(error)
            .assertNoValues()
            .assertNotComplete()
    }
}