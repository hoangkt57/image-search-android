package com.kirito.app.data

import com.kirito.app.common.Photo
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor() : DataRepository {

    private val photos = arrayListOf<Photo>()

    override fun savePhotos(photos: List<Photo>) {
        this.photos.clear()
        this.photos.addAll(photos)
    }

    override fun getPhotos(): List<Photo> = ArrayList(photos)
}