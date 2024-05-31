package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Song
import com.example.myapplication.api.ISongSearchService
import com.example.myapplication.api.SongSearchService
import com.example.myapplication.model.Track
import com.example.myapplication.repository.SongRepository

import kotlinx.coroutines.launch



class HomeViewModel : ViewModel() {
    private val _tracksLiveData = MutableLiveData<List<Track>>()
    val tracksLiveData: LiveData<List<Track>> = _tracksLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadTracks()
    }

    fun loadTracks() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val tracks = SongRepository.tracks.values.toList()
                _tracksLiveData.value = tracks
            } catch (e: Exception) {
                _tracksLiveData.value = emptyList() // En caso de excepción, asignar una lista vacía
            }
            _isLoading.value = false
        }
    }
}



