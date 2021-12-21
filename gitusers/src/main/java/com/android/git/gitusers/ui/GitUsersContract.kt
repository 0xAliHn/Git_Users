package com.android.git.gitusers.ui

import com.android.git.gitusers.ui.model.SearchedUsersViewData

interface GitUsersContract {
    interface View {
        fun setData(data: List<SearchedUsersViewData>)
        fun hideProgressBar()
        fun showProgressBar()
        fun showErrorMessage(errorMsg: String)
        fun hideEmptyPage()
        fun showRecyclerView()
        fun hideRecyclerView()
    }

    interface Presenter {
        fun subscribeOnGitUsersQuery(query: String, page: Int)
        fun nextPage()
    }
}

