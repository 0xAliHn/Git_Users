package com.android.git.core.network

import retrofit2.Retrofit

internal class RetrofitApiFactory(private val retrofitClient: Retrofit) : ApiFactory {
    override fun <T> create(service: Class<T>): T = retrofitClient.create(service)
}
