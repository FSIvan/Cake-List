package com.ivanwooll.cakelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.ivanwooll.cakelist.extensions.createViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), CakesAdapter.Callback {
    private val viewModel by lazy { createViewModel<MainViewModel>() }

    private val cakesAdapter by lazy { CakesAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewCakes.adapter = cakesAdapter

        viewModel.uiStateLiveData.observe(this, Observer { uiState ->
            val flipperChild = when (uiState) {
                UIState.CakesFound -> 0
                UIState.CakesNotFound -> 1
                UIState.ApiNotAvailable -> 2
            }
            viewFlipper.displayedChild = flipperChild
            swipeRefreshCakes.isRefreshing = false
        })

        viewModel.cakesLiveData.observe(this, Observer { list ->
            println(list.size)
            cakesAdapter.updateItems(list)
        })

        textViewTryAgainCake.setOnClickListener {
            viewModel.fetchCakes()
        }

        textViewTryAgainApiError.setOnClickListener {
            viewModel.fetchCakes()
        }

        swipeRefreshCakes.setOnRefreshListener { viewModel.fetchCakes() }
    }

    override fun onItemClick(description: String) {
        Snackbar.make(
            findViewById(android.R.id.content),
            description,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
