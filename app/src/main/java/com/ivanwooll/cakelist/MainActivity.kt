package com.ivanwooll.cakelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ivanwooll.cakelist.extensions.createViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { createViewModel<MainViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.uiStateLiveData.observe(this, Observer { uiState ->
            val flipperChild = when (uiState) {
                UIState.CakesFound -> 0
                UIState.CakesNotFound -> 1
                UIState.ApiNotAvailable -> 2
            }
            viewFlipper.displayedChild = flipperChild
        })

        viewModel.cakesLiveData.observe(this, Observer { list ->

        })

        viewModel.fetchCakes()
    }
}
