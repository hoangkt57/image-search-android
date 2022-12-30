package com.kirito.app.common

import com.google.gson.annotations.SerializedName


data class PhotoResponse(
    @SerializedName("next_page")
    val nextPage: String?,
    @SerializedName("prev_page")
    val prevPage: String?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("per_page")
    val perPage: Int?,
    @SerializedName("photos")
    val photos: List<Photo?>?,
    @SerializedName("total_results")
    val totalResults: Int?
) {

    data class Photo(
        @SerializedName("alt")
        val alt: String?,
        @SerializedName("avg_color")
        val avgColor: String?,
        @SerializedName("height")
        val height: Int?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("liked")
        val liked: Boolean?,
        @SerializedName("photographer")
        val photographer: String?,
        @SerializedName("photographer_id")
        val photographerId: Int?,
        @SerializedName("photographer_url")
        val photographerUrl: String?,
        @SerializedName("src")
        val src: Src?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("width")
        val width: Int?
    )

    data class Src(
        @SerializedName("landscape")
        val landscape: String?,
        @SerializedName("large")
        val large: String?,
        @SerializedName("large2x")
        val large2x: String?,
        @SerializedName("medium")
        val medium: String?,
        @SerializedName("original")
        val original: String?,
        @SerializedName("portrait")
        val portrait: String?,
        @SerializedName("small")
        val small: String?,
        @SerializedName("tiny")
        val tiny: String?
    )
}