package com.android.git.gitusers.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.android.git.core.RxSchedulerExtension
import com.android.git.core.RxSchedulerExtension.Companion.triggerActions
import com.android.git.gitusers.data.SearchedUsers
import com.android.git.gitusers.domain.GetSearchedGitUsersViewDataInteractor
import com.android.git.gitusers.domain.mapper.GitUsersViewDataMapper
import com.android.git.gitusers.ui.model.QueryData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Observable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(RxSchedulerExtension::class)
internal class GitUsersPresenterTest {

    private val view: GitUsersContract.View = mock()
    private val lifecycle: Lifecycle = mock()
    private val lifecycleOwner: LifecycleOwner = mock {
        on { lifecycle } doReturn lifecycle
    }
    private val getSearchedGitUsersViewDataInteractor: GetSearchedGitUsersViewDataInteractor = mock()
    private val queryData: QueryData = mock()

    private val presenter = GitUsersPresenter(
        view,
        lifecycleOwner,
        getSearchedGitUsersViewDataInteractor,
        queryData
    )

    private val searchedUsers = listOf<SearchedUsers>(
        SearchedUsers(login = "aaa", avatar_url = "avatar_url", url = "url")
    )

    private val searchedUsersViewData = GitUsersViewDataMapper.mapToViewData(searchedUsers)

    private val query = "a"
    private val page = 1

    @Test
    fun `when onStart() is called - verify getSearchedGitUsersViewDataInteractor is invoked which emit view data then verify setData() is called`() {
        whenever(getSearchedGitUsersViewDataInteractor(queryData)).thenReturn(Observable.just(searchedUsersViewData))

        presenter.subscribeOnGitUsersQuery(query, page)
            .also { triggerActions() }

        verify(getSearchedGitUsersViewDataInteractor)(queryData)
        inOrder(view) {
            verify(view).hideProgressBar()
            verify(view).showRecyclerView()
            verify(view).setData(searchedUsersViewData)
            verify(view).hideEmptyPage()
            verifyNoMoreInteractions()
        }
    }

    @Test
    fun `when onStart() is called - verify getSearchedGitUsersViewDataInteractor is invoked which emit error then show showErrorMessage() view`() {
        val error = Exception("Error")

        whenever(getSearchedGitUsersViewDataInteractor(queryData)).thenReturn(Observable.error(error))

        presenter.subscribeOnGitUsersQuery(query, page)
            .also { triggerActions() }

        verify(getSearchedGitUsersViewDataInteractor)(queryData)
        inOrder(view) {
            verify(view).hideProgressBar()
            verify(view).showErrorMessage("Something went wrong. Please try again...")
            verifyNoMoreInteractions()
        }
    }
}