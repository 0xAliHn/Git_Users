package com.android.git.gitusers.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A public interface that exposes the [getSearchedGitUsers] fun
 * Currently we are returning call so that in future we don't need to change API Service whatever we migrate with either RxJava or Coroutines or something else
 */
interface GitUsersApiService {

    @GET("users?")
    fun getSearchedGitUsers(@Query("q") query: String, @Query("page") page: Int): Call<Response>?

}