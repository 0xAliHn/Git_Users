package com.android.git.gitusers.domain.mapper

import com.android.git.gitusers.data.SearchedUsers
import com.android.git.gitusers.ui.model.SearchedUsersViewData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SearchedUsersViewDataMapperTest {

    private val searchedUsers = listOf<SearchedUsers>(
        SearchedUsers(login = "aaa", avatar_url = "avatar_url", url = "url")
    )

    private val expectedResult =
        SearchedUsersViewData(
            login = "aaa",
            avatar_url = "avatar_url",
            url = "url"
        )

    @Test
    fun mapToViewData() {
        val actualResult = GitUsersViewDataMapper.mapToViewData(searchedUsers)

        assertEquals(expectedResult.login, actualResult[0].login)
        assertEquals(expectedResult.avatar_url, actualResult[0].avatar_url)
        assertEquals(expectedResult.url, actualResult[0].url)
    }
}