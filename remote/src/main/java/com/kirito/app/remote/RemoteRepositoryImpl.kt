package com.kirito.app.remote

import com.kirito.app.common.PhotoResponse
import com.kirito.app.remote.api.ApiHelper
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : RemoteRepository {

    override fun searchImage(query: String, page: Int) = apiHelper.searchImage(query, page).execute().body()
    override fun loadMorePage(url: String): PhotoResponse? = apiHelper.loadMorePage(url).execute().body()
}