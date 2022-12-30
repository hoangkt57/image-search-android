package com.kirito.app.data

import com.kirito.app.common.Photo

interface DataRepository {

    fun savePhotos(photos: List<Photo>)

    fun getPhotos(): List<Photo>
}