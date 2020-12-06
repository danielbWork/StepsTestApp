package com.example.stepstestsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stepstestsapp.R

/**
 * Only used to hold a navFragment for the navigation library
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}