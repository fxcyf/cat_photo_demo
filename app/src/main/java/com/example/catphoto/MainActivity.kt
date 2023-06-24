package com.example.catphoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bytedance.keva.KevaBuilder
import com.facebook.drawee.backends.pipeline.Fresco

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "Created")
        Fresco.initialize(this)
        KevaBuilder.getInstance().setContext(this)
        setContentView(R.layout.activity_main)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "Destroyed")
    }

}