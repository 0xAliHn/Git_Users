package com.android.git.gitusers.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchedUsers(
    @Json(name = "login") val login: String?,
    @Json(name = "avatar_url") val avatar_url: String?,
    @Json(name = "url") val url: String?
)

@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "items") val items: List<SearchedUsers>
)
