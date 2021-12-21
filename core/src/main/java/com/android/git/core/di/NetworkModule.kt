package com.android.git.core.di


import com.android.git.core.BuildConfig
import com.android.git.core.network.ApiFactory
import com.android.git.core.network.RetrofitApiFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.github.com/search/"
private const val CONNECT_TIMEOUT_IN_MS = 30000

@Module
object NetworkModule {

    private val moshi = Moshi.Builder()
        .build()

    @PerApplication
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT_IN_MS.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi object
     */

    @PerApplication
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @PerApplication
    @Provides
    fun provideApiFactory(retrofit: Retrofit): ApiFactory {
        return RetrofitApiFactory(retrofit)
    }

}
