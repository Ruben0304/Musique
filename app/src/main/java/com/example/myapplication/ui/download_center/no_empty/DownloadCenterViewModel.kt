package com.example.myapplication.ui.download_center.no_empty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.SongSearcher
import com.example.myapplication.model.Song
import com.example.myapplication.model.Track
import com.example.myapplication.repository.SongRepository
import kotlinx.coroutines.launch

class DownloadCenterViewModel : ViewModel() {
    private val _cancionesLiveData = MutableLiveData<List<Song>>()

    private val _tracksLiveData = MutableLiveData<List<Track>>()
    val tracksLiveData: LiveData<List<Track>> = _tracksLiveData
    val cancionesLiveData: LiveData<List<Song>> get() = _cancionesLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val songService = SongSearcher()

    init {
        loadTracks()
    }

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

    fun loadTracks() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val tracks = SongRepository.tracks.values.toList()
                _tracksLiveData.postValue(tracks)
            } catch (e: Exception) {
                _tracksLiveData.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}