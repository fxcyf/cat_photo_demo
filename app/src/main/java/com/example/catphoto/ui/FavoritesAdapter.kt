package com.example.catphoto.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.catphoto.databinding.ListItemBinding
import com.example.catphoto.network.CatPhoto

private const val TAG = "FavoritesAdapter"

class FavoritesAdapter(private val viewModel: FavoritesViewModel) :
    ListAdapter<CatPhoto, FavoritesAdapter.CatFavoriteViewHolder>(DiffCallback) {

    class CatFavoriteViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(catPhoto: CatPhoto) {
            Log.d(TAG, "CatFavoriteViewHolder bind")
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
    ): CatFavoriteViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        return CatFavoriteViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: CatFavoriteViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        val image = getItem(position)
        holder.bind(image)

        val gestureDetector = GestureDetector(
            holder.itemView.context,
            object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    // Handle double-tap gesture
                    Log.d(TAG, "Double clicked")
                    viewModel.removeFavorite(holder.itemView.context, image.id)
                    return super.onDoubleTap(e)
                }
            })

        holder.itemView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

}