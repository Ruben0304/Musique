package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Song
import com.example.myapplication.service.ISongSearchService
import com.example.myapplication.service.SongSearchService

import kotlinx.coroutines.launch



class HomeViewModel(
) : ViewModel() {

    private val songService: ISongSearchService = SongSearchService()
    private val _cancionesLiveData = MutableLiveData<List<Song>>()
    val cancionesLiveData: LiveData<List<Song>> = _cancionesLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        // Iniciar una búsqueda inicial o cargar datos iniciales
        searchTracks("Saiko")
    }

    fun searchTracks(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val songItems = songService.fetchTracks(query)
                _cancionesLiveData.value = songItems ?: emptyList()
                _isLoading.value = false
            } catch (e: Exception) {
                // Manejar la excepción, como mostrar un mensaje de error
                _isLoading.value = false
            }
        }
    }
}


