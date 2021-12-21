package com.android.git.gitusers.ui

import androidx.lifecycle.LifecycleOwner
import com.android.git.core.BasePresenter
import com.android.git.core.lifecycle.LifecycleEvent
import com.android.git.gitusers.domain.GetSearchedGitUsersViewDataInteractor
import com.android.git.gitusers.ui.model.QueryData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class GitUsersPresenter(
    private val view: GitUsersContract.View,
    private val lifecycleOwner: LifecycleOwner,
    private val getSearchedGitUsersViewDataInteractor: GetSearchedGitUsersViewDataInteractor,
    private val queryData: QueryData
) : BasePresenter(), GitUsersContract.Presenter {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onStart() {
        super.onStart()
        view.hideProgressBar()
        view.hideRecyclerView()
    }

    override fun subscribeOnGitUsersQuery(query: String, page: Int) =
        addDisposable(getSearchedGitUsersViewDataInteractor(mapToQueryData(query, page))
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showProgressBar() }
            .subscribe(
                {
                    with(view) {
                        hideProgressBar()
                        showRecyclerView()
                        setData(it)
                        hideEmptyPage()
                    }
                },
                {
                    Timber.e("Git users fetching failed. $it")
                    showErrorMessage()
                }
            ), LifecycleEvent.DESTROY)

    private fun mapToQueryData(query: String, page: Int): QueryData {
        return queryData.apply {
            this.query = query
            this.page = page
        }
    }

    private fun showErrorMessage() {
        view.hideProgressBar()
        view.showErrorMessage("Something went wrong. Please try again...")
    }

    override fun nextPage() {
        if (queryData.query.isNotBlank()) {
            view.showProgressBar()
            subscribeOnGitUsersQuery(queryData.query, queryData.page + 1)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleOwner.lifecycle.removeObserver(this)
    }
}
