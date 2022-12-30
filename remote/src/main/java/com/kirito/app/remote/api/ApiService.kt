package com.kirito.app.remote.api

import com.kirito.app.common.PAGE_SIZE
import com.kirito.app.common.PhotoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("v1/search")
    fun searchImage(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = PAGE_SIZE
    ): Call<PhotoResponse?>

    @GET
    fun loadMorePage(@Url url: String): Call<PhotoResponse?>
}