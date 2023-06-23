package com.example.catphoto.data

import com.example.catphoto.R
import com.example.catphoto.model.Photo

class Datasource {
    fun loadPhotos(): List<Photo> {
        return listOf(
            Photo(R.drawable.co),
            Photo(R.drawable._di),
            Photo(R.drawable._nu),
            Photo(R.drawable._gd),
            Photo(R.drawable._s7),
            Photo(R.drawable.bdg),
            Photo(R.drawable.e03),
            Photo(R.drawable.mjaxmtu4nq),
            Photo(R.drawable.vxb2jdnoo),
            Photo(R.drawable.d78)
        )
    }
}