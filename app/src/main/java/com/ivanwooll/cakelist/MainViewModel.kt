package com.ivanwooll.cakelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ivanwooll.cakelist.data.ApiResponse
import com.ivanwooll.cakelist.data.CakeRepository
import com.ivanwooll.cakelist.data.api.models.ApiCake
import com.ivanwooll.cakelist.data.room.Cake
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(private val cakeRepository: CakeRepository) : ViewModel(), CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.IO // all operations will be on a the IO thread so we must ensure we call "postValue()" on uiStateLiveData.
    // We could use withContext(Dispatchers.Main){} but this is cleaner

    val cakesLiveData = cakeRepository.cakesLiveData()
    val uiStateLiveData = MutableLiveData<UIState>()

    init {
        fetchCakes()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun fetchCakes() {
        launch {
            clearCakes()
            val response = cakeRepository.fetchCakes()
            handleFetchCakesRersponse(response)
        }
    }

    private fun handleFetchCakesRersponse(response: ApiResponse<List<ApiCake>>) {
        when (response) {
            is ApiResponse.Success -> {
                response.body?.let { list ->
                    // val emptyList = emptyList<Cake>() // uncomment and pass emptyList to below if else statement to test Ui state for empty list
                    if (list.isEmpty()) {
                        uiStateLiveData.postValue(UIState.CakesNotFound)
                    } else {
                        uiStateLiveData.postValue(UIState.CakesFound)
                    }
                }
            }
            is ApiResponse.Error -> uiStateLiveData.postValue(UIState.ApiNotAvailable)
        }
    }

    private fun clearCakes() {
        cakeRepository.clearCakesTable()
    }

    class ViewModelFactory(private val cakeRepository: CakeRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(cakeRepository) as T
        }
    }

}

sealed class UIState {
    object ApiNotAvailable : UIState()
    object CakesFound : UIState()
    object CakesNotFound : UIState()
}