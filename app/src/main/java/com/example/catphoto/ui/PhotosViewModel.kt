package com.example.catphoto.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytedance.keva.Keva
import com.bytedance.keva.KevaConstants
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

    private val savedPhotosKeva: Keva =
        Keva.getRepo("cat_photos", KevaConstants.MODE_SINGLE_PROCESS)
    private val favPhotosKeva: Keva =
        Keva.getRepo("cat_favorites", KevaConstants.MODE_SINGLE_PROCESS)

    init {
        fetchSavedPhotoUrls()
        fetchNewPhotoUrls()
    }

    private fun fetchSavedPhotoUrls() {
        Log.d(TAG, "Begin fetch saved photo urls")
        _photos.value = savedPhotosKeva.all.map { entry ->
            CatPhoto(id = entry.key, url = entry.value as String)
        }
        _status.value = CatApiStatus.DONE
        Log.d(TAG, "fetch saved photos ${_photos.value.toString()}")
    }

    fun fetchNewPhotoUrls() {
        Log.d(TAG, "Begin fetch new photo urls")
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
                Log.d(TAG, "Fetch failed ${e.toString()}")
            }
        }
    }

    fun refreshPhotoUrls() {
        Log.d(TAG, "Begin refresh photo urls")
        viewModelScope.launch {
            _status.value = CatApiStatus.LOADING
            try {
                _photos.value = CatApi.retrofitService.getPhotos()
                _status.value = CatApiStatus.DONE
                Log.d(TAG, "Fetch finished ${_photos.toString()}")
            } catch (e: Exception) {
                _status.value = CatApiStatus.ERROR
                Log.d(TAG, "Fetch failed ${e.toString()}")
            }
        }
    }

    fun saveAsFavorite(context: Context, photo: CatPhoto) {
        Log.d(TAG, "saveAsFavorite ${photo.toString()}")
        favPhotosKeva.storeString(photo.id, photo.url)
        Toast.makeText(context, "Saved to Favorites", Toast.LENGTH_SHORT).show()
    }


    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared")
        savedPhotosKeva.clear()
        val currentPhotos = _photos.value ?: emptyList<CatPhoto>()
        if (currentPhotos.size >= 5) {
            val lastFivePhotos = currentPhotos.takeLast(5)
            for (photo in lastFivePhotos) {
                savedPhotosKeva.storeString(photo.id, photo.url)
            }
            Log.d(TAG, "Save last five photos ${lastFivePhotos.toString()}")
        }
    }
}