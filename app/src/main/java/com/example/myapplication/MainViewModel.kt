package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    private val users = MutableLiveData<List<ResponseItem>>()
    private val loading = MutableLiveData<Boolean>()
    var job: Job? = null

    fun getUsers(): LiveData<List<ResponseItem>> {
        return users
    }

    fun getLoadingState(): LiveData<Boolean> {
        return loading
    }

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun loadResults(searchString: String) {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAllMovies(searchString)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    users.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }



}