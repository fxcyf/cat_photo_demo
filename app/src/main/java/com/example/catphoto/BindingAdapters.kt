package com.example.catphoto

import android.media.Image
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.Disposable
import coil.size.Scale
import com.example.catphoto.network.CatPhoto
import com.example.catphoto.ui.CatApiStatus
import com.example.catphoto.ui.FavoritesAdapter
import com.example.catphoto.ui.PhotosAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.google.android.material.internal.ViewUtils.dpToPx

private const val TAG = "BindingAdapters"

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CatPhoto>?) {
    Log.d(TAG, "bindRecyclerView data {${data.toString()}")
    val adapter = recyclerView.adapter as PhotosAdapter
    adapter.submitList(data)
}

@BindingAdapter("favData")
fun bindFavoriteList(recyclerView: RecyclerView, data: List<CatPhoto>?) {
    Log.d(TAG, "bindFavoriteList data {${data.toString()}")
    val adapter = recyclerView.adapter as FavoritesAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: SimpleDraweeView, imgUrl: String?) {
    Log.d(TAG, "bindImage url $imgUrl")

    val layoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    imgView.layoutParams = layoutParams
    imgView.scaleType = ImageView.ScaleType.CENTER_CROP

    val hierarchy = GenericDraweeHierarchyBuilder(imgView.context.resources)
        .apply {
            setPlaceholderImage(R.drawable.loading_animation)
            setFailureImage(R.drawable.ic_broken_image)
        }
        .build()

    imgView.hierarchy = hierarchy

    imgUrl?.let {
        val imageUri = Uri.parse(imgUrl)
        imgView.setImageURI(imageUri, imgView)
//        imgView.load(imageUri) {
//            placeholder(R.drawable.loading_animation)
//            error(R.drawable.ic_broken_image)
//        }
    }
}

@BindingAdapter("catApiStatus")
fun bindStatus(statusImageView: ImageView, status: CatApiStatus) {
    when (status) {
        CatApiStatus.LOADING -> {
            Log.d(TAG, "bindStatus LOADING")

            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }

        CatApiStatus.ERROR -> {
            Log.d(TAG, "bindStatus ERROR")

            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }

        CatApiStatus.DONE -> {
            Log.d(TAG, "bindStatus DONE")

            statusImageView.visibility = View.GONE
        }
    }
}
