package com.android.git.core.network

interface ApiFactory {
    fun <T> create(service: Class<T>): T
}
