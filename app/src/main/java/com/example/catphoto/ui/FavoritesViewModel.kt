package com.example.catphoto.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bytedance.keva.Keva
import com.bytedance.keva.KevaConstants
import com.example.catphoto.network.CatPhoto

private const val TAG = "FavoritesViewModel"

class FavoritesViewModel : ViewModel() {
    private val _photos = MutableLiveData<List<CatPhoto>>()
    val photos: LiveData<List<CatPhoto>> = _photos

    private val keva: Keva = Keva.getRepo("cat_favorites", KevaConstants.MODE_SINGLE_PROCESS)

    init {
        fetchFavoritePhotoUrls()
    }

    fun fetchFavoritePhotoUrls() {
        Log.d(TAG, "Begin fetch favorite photo urls")
        _photos.value = keva.all.map { entry ->
            CatPhoto(id = entry.key, url = entry.value as String)
        }
    }

    fun removeFavorite(context: Context, id: String) {
        keva.erase(id)
        Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show()
    }
}