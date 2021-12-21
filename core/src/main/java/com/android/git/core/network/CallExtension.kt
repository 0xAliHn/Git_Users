package com.android.git.core.network

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * Call extension to handle call then map it to Observable either complete or error
 */
fun <T> Call<T>?.callToObservable(): Observable<T> {
    return Observable.create { emitter ->
        this?.enqueue(object : Callback<T> {
            override fun onResponse(
                call: Call<T?>?,
                response: Response<T?>?,
            ) {
                if (response?.isSuccessful == true) {
                    Timber.d("Call request successful with response")
                    val responseBody: T? = response.body()
                    emitter.onNext(responseBody)
                    emitter.onComplete()
                } else {
                    Timber.e("Got a response but not successful: $response?.errorBody()?.string()")
                    emitter.onError(
                        Throwable(
                            "Got a response but not successful: ".plus(
                                response?.errorBody()?.string()
                            )
                        )
                    )
                }
            }

            override fun onFailure(call: Call<T?>?, t: Throwable?) {
                Timber.e("Call request failed. $t.toString()")
                emitter.onError(t)
            }
        })
    }
}
