package com.ivanwooll.cakelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ivanwooll.cakelist.data.ApiResponse
import com.ivanwooll.cakelist.data.CakeRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.IO // all operations will be on a the IO thread so we must ensure we call "postValue()" on uiStateLiveData.
                                                                                // We could use withContext(Dispatchers.Main){} but this is cleaner

    private val cakeRepository by lazy { CakeRepository() }

    val cakesLiveData = cakeRepository.cakesLiveData()
    val uiStateLiveData = MutableLiveData<UIState>()


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun fetchCakes() {
        launch {
            cakeRepository.clearCakesTable()
            val response = cakeRepository.fetchCakes()

            when (response) {
                is ApiResponse.Success -> {
                    response.body?.let { list ->
                        if (list.isEmpty()) {
                            uiStateLiveData.postValue(UIState.CakesNotFound)
                        }
                    }
                    uiStateLiveData.postValue(UIState.CakesFound)
                }
                is ApiResponse.Error -> uiStateLiveData.postValue(UIState.ApiNotAvailable)
            }
        }
    }

}

sealed class UIState {
    object ApiNotAvailable : UIState()
    object CakesFound : UIState()
    object CakesNotFound : UIState()
}