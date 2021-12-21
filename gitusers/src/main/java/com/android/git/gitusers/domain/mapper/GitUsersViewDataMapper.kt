package com.android.git.gitusers.domain.mapper

import com.android.git.gitusers.data.SearchedUsers
import com.android.git.gitusers.ui.model.SearchedUsersViewData

internal object GitUsersViewDataMapper {
    fun mapToViewData(searchedUsers: List<SearchedUsers>?): List<SearchedUsersViewData> {
        val list = mutableListOf<SearchedUsersViewData>()
        return searchedUsers?.let { it ->
            it.forEach {
                list.add(
                    SearchedUsersViewData(
                        login = it.login.orEmpty(),
                        avatar_url = it.avatar_url.orEmpty(),
                        url = it.url.orEmpty()
                    )
                )
            }
            return list
        } ?: list
    }
}