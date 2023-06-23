package com.example.catphoto.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.catphoto.databinding.FragmentPhotosBinding
import com.facebook.drawee.backends.pipeline.Fresco

private const val TAG = "PhotosFragment"
class PhotosFragment : Fragment() {

    private val viewModel: PhotosViewModel by viewModels()
    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        _binding = FragmentPhotosBinding.inflate(inflater)
        Log.d(TAG, "FragmentPhotosBinding.inflate(inflater)")

        binding.lifecycleOwner = this
        Log.d(TAG, "lifecycleOwner")

        binding.viewModel = viewModel
        Log.d(TAG, "bind viewModel")

        binding.recyclerView.adapter = PhotosAdapter()
        Log.d(TAG, "bind adapter")

        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItem == totalItemCount - 1) {
                    viewModel.fetchImageUrls()
                }
            }
        })


        return binding.root
    }


}