package com.kirito.app.imagesearch.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kirito.app.common.Photo
import com.kirito.app.data.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    private val dataRepository: DataRepository
) : AndroidViewModel(application) {

    companion object {
        private val TAG = DetailViewModel::class.java.name
    }

    val photos = MutableLiveData<List<Photo>>()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.Default) {
            photos.postValue(dataRepository.getPhotos())
        }
    }

    fun updatePhotos() {
        val list = photos.value ?: return
        Log.d(TAG, "updatePhotos - ${list.size}")
        dataRepository.savePhotos(list)
    }

    fun findCurrentPosition(photoId: Int): Int {
        val list = photos.value ?: return 0
        list.forEachIndexed { index, it ->
            if (photoId == it.id) {
                return index
            }
        }
        return 0
    }

    fun deletePhoto(position: Int) {
        val list = photos.value?.toMutableList() ?: return
        list.removeAt(position)
        photos.value = list
    }
}