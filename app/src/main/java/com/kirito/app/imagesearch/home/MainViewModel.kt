package com.kirito.app.imagesearch.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kirito.app.common.Photo
import com.kirito.app.common.PhotoResponse
import com.kirito.app.data.DataRepository
import com.kirito.app.remote.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val dataRepository: DataRepository,
    private val remoteRepository: RemoteRepository
) : AndroidViewModel(application) {

    companion object {
        private val TAG = MainViewModel::class.java.name
        private var isRunning = false
    }

    private val customDispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
    val photos = MutableLiveData<List<Photo>>()
    private var nextPage: String? = null
    private var queryData: String? = null

    fun savePhotos() {
        val list = photos.value ?: return
        dataRepository.savePhotos(list)
    }

    fun updatePhotos() {
        val list = dataRepository.getPhotos()
        Log.d(TAG, "updatePhotos - ${list.size} -- ${photos.value?.size}")
        if (list.size != photos.value?.size) {
            photos.postValue(list)
        }
        dataRepository.savePhotos(emptyList())
    }

    private fun mapResponseToPhoto(response: PhotoResponse): List<Photo> {
        val result = arrayListOf<Photo>()
        response.photos?.forEach { photo ->
            val id = photo?.id ?: return@forEach
            val width = photo.width ?: 0
            val height = photo.height ?: 0
            val photographer = photo.photographer ?: ""
            val originalUrl = photo.src?.original ?: ""
            val large2xUrl = photo.src?.large2x ?: ""
            result.add(
                Photo(
                    id = id,
                    width = width,
                    height = height,
                    photographer = photographer,
                    originalUrl = originalUrl,
                    large2xUrl = large2xUrl
                )
            )
        }
        return result
    }

    fun searchPhoto(query: String) {
        if (queryData == query) {
            return
        }
        queryData = query
        viewModelScope.launch(customDispatcher) {
            photos.postValue(emptyList())
            val response = remoteRepository.searchImage(query)
            if (response == null) {
                Log.e(TAG, "searchPhoto - response is null")
                return@launch
            }
            val photoList = mapResponseToPhoto(response)
            nextPage = response.nextPage
            withContext(Dispatchers.Main) {
                photos.value = photoList
            }
            loadMorePage(true)
            loadMorePage(true)
        }
    }

    fun loadMorePage(isForce: Boolean = false) {
        if (isRunning && !isForce) {
            Log.d(TAG, "loadMorePage - isRunning")
            return
        }
        viewModelScope.launch(customDispatcher) {
            isRunning = true
            val nextPage = nextPage
            if (nextPage.isNullOrEmpty()) {
                Log.e(TAG, "loadMorePage - next page is empty")
                return@launch
            }
            val response = remoteRepository.loadMorePage(nextPage)
            if (response == null) {
                Log.e(TAG, "searchPhoto - response is null")
                return@launch
            }
            val photoList = mapResponseToPhoto(response)
            this@MainViewModel.nextPage = response.nextPage

            val list = photos.value
            if (list.isNullOrEmpty()) {
                Log.e(TAG, "loadMorePage - list empty")
                return@launch
            }
            val newPhotoList = list.toMutableList()
            newPhotoList.addAll(photoList)
            photos.postValue(newPhotoList)
            Log.d(TAG, "loadMorePage - end - $nextPage")
            isRunning = false
        }
    }
}