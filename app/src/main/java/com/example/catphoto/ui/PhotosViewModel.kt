package com.example.catphoto.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catphoto.network.CatApi
import com.example.catphoto.network.CatPhoto
import kotlinx.coroutines.launch

enum class CatApiStatus { LOADING, ERROR, DONE }
private const val TAG = "PhotosViewModel"
class PhotosViewModel : ViewModel() {
    private val _status = MutableLiveData<CatApiStatus>()
    val status: LiveData<CatApiStatus> = _status

    private val _photos = MutableLiveData<List<CatPhoto>>()
    val photos: LiveData<List<CatPhoto>> = _photos

    init {
        fetchImageUrls()
    }

    fun fetchImageUrls() {
        Log.d(TAG, "Begin fetch image urls")
        viewModelScope.launch {
            _status.value = CatApiStatus.LOADING
            try {
                val currentPhotos = _photos.value ?: emptyList<CatPhoto>()
                val newPhotos = CatApi.retrofitService.getPhotos()

                val updatedPhotos = ArrayList(currentPhotos)
                updatedPhotos.addAll(newPhotos)

                _photos.value = updatedPhotos
                _status.value = CatApiStatus.DONE
                Log.d(TAG, "Fetch finished ${_photos.toString()}")
            } catch (e: Exception) {
                _status.value = CatApiStatus.ERROR
//                _photos.value = listOf()
                Log.d(TAG, "Fetch failed ${e.toString()}")
            }
        }
    }
}