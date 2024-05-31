package com.example.myapplication.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Song
import com.example.myapplication.api.ISongSearchService
import com.example.myapplication.api.SongSearchService
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _cancionesLiveData = MutableLiveData<List<Song>>()
    val cancionesLiveData: LiveData<List<Song>> get() = _cancionesLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val songService: ISongSearchService = SongSearchService()

    fun searchTracks(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val songItems = songService.fetchTracks(query)
                _cancionesLiveData.value = songItems ?: emptyList()
            } catch (e: Exception) {
                // Handle exception, e.g., show a toast or a snackbar
                _cancionesLiveData.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}