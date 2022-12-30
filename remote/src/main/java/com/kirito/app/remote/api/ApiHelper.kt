package com.kirito.app.remote.api

import com.kirito.app.common.PhotoResponse
import retrofit2.Call

interface ApiHelper {

    fun searchImage(query: String, page: Int): Call<PhotoResponse?>

    fun loadMorePage(url: String): Call<PhotoResponse?>
}