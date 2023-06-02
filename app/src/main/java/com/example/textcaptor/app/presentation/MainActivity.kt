package com.example.textcaptor.app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.textcaptor.R
import com.example.textcaptor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}