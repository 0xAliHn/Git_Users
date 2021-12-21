package com.android.git.gitusers.domain

import com.android.git.core.Interactor
import com.android.git.gitusers.domain.mapper.GitUsersViewDataMapper
import com.android.git.gitusers.ui.model.QueryData
import com.android.git.gitusers.ui.model.SearchedUsersViewData
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetSearchedGitUsersViewDataInteractor @Inject constructor(
    private val repository: IGitUsersRepository
) : Interactor<QueryData, List<SearchedUsersViewData>>() {
    override fun run(params: QueryData): Observable<List<SearchedUsersViewData>> =
        repository.getSearchedGitUsersData(params.query, params.page)
            .map { GitUsersViewDataMapper.mapToViewData(it) }
}