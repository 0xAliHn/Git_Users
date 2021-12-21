package com.android.git.gitusers.domain

import com.android.git.gitusers.data.SearchedUsers
import io.reactivex.rxjava3.core.Observable

/**
 * Contract between domain and data layer
 */
interface IGitUsersRepository {
    fun getSearchedGitUsersData(query: String, page: Int): Observable<List<SearchedUsers>>
}
