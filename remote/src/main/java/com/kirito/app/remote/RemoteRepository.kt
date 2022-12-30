package com.kirito.app.remote

import com.kirito.app.common.PhotoResponse

interface RemoteRepository {
    fun searchImage(query: String, page: Int = 1): PhotoResponse?
    fun loadMorePage(url: String): PhotoResponse?
}