package com.ivanwooll.cakelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ivanwooll.cakelist.extensions.createViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { createViewModel<MainViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
