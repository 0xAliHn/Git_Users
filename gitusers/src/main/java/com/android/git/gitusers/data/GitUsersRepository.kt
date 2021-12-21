package com.android.git.gitusers.data

import com.android.git.core.network.callToObservable
import com.android.git.gitusers.domain.IGitUsersRepository
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import javax.inject.Inject

class GitUsersRepository @Inject constructor(
    private val apiService: GitUsersApiService
) : IGitUsersRepository {
    override fun getSearchedGitUsersData(query: String, page: Int): Observable<List<SearchedUsers>> {
        val call: Call<Response>? = apiService.getSearchedGitUsers(query, page)
        return call.callToObservable().map { it.items }
    }
}
