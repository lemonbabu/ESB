package com.texon.engineeringsmartbook.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VideoPlayerActivityViewModel: ViewModel() {
    // Create a LiveData with a String
    private val currentData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        currentData.value = ""
    }

    fun setData(data: String){
        currentData.value = data
    }

    fun getData(): LiveData<String> {
        return currentData
    }
}