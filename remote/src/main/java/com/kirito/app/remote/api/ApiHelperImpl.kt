package com.kirito.app.remote.api

import com.kirito.app.common.PhotoResponse
import retrofit2.Call
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override fun searchImage(query: String, page: Int): Call<PhotoResponse?> = apiService.searchImage(query, page)

    override fun loadMorePage(url: String): Call<PhotoResponse?> = apiService.loadMorePage(url)
}