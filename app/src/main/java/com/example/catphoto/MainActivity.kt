package com.example.catphoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco


class MainActivity : AppCompatActivity() {
//    private lateinit var viewModel: PhotosViewModel
//    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_main)

//        viewModel = ViewModelProvider(this).get(PhotosViewModel::class.java)
//        observeData()
//        viewModel.fetchImageUrls()

//        val photoDataset = Datasource().loadPhotos()

//        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
//        recyclerView.setHasFixedSize(true)

    }

//    private fun observeData() {
//        viewModel.photos.observe(this, Observer { photos ->
//            recyclerView.adapter = ItemAdapter(this, photos)
//        })
//    }
}