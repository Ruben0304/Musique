package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrackViewModel : ViewModel() {
    private val _currentTrackImage = MutableLiveData<String>()
    val currentTrackImage: LiveData<String> get() = _currentTrackImage

    fun updateTrackImage(imageUrl: String) {
        _currentTrackImage.value = imageUrl
    }
}