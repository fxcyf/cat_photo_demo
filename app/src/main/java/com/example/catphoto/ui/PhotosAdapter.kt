package com.example.catphoto.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.catphoto.databinding.ListItemBinding
import com.example.catphoto.network.CatPhoto

private const val TAG = "PhotosAdapter"

class PhotosAdapter :
    ListAdapter<CatPhoto, PhotosAdapter.CatPhotoViewHolder>(DiffCallback) {

    class CatPhotoViewHolder(private var binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(catPhoto: CatPhoto) {
            Log.d(TAG, "CatPhotoViewHolder bind")
            binding.photo = catPhoto
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<CatPhoto>() {
        override fun areItemsTheSame(oldItem: CatPhoto, newItem: CatPhoto): Boolean {
            Log.d(TAG, "DiffCallback areItemsTheSame")

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CatPhoto, newItem: CatPhoto): Boolean {
            Log.d(TAG, "DiffCallback areContentsTheSame")

            return oldItem.url == newItem.url
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CatPhotoViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        return CatPhotoViewHolder(ListItemBinding.inflate(
            LayoutInflater.from(parent.context)
        ))
    }

    override fun onBindViewHolder(
        holder: CatPhotoViewHolder,
        position: Int
    ) {
        Log.d(TAG, "onBindViewHolder")

        val catPhoto = getItem(position)
        holder.bind(catPhoto)
    }
}