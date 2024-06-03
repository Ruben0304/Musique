package com.example.myapplication.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Album
import com.example.myapplication.model.Artist
import com.example.myapplication.model.Song

import com.example.myapplication.model.Track
import com.example.myapplication.repository.AlbumRepository
import com.example.myapplication.repository.ArtistRepository
import com.example.myapplication.repository.SongRepository

import kotlinx.coroutines.launch



class HomeViewModel : ViewModel() {
    private val _tracksLiveData = MutableLiveData<List<Track>>()
    val tracksLiveData: LiveData<List<Track>> = _tracksLiveData

    private val _albumsLiveData = MutableLiveData<List<Album>>()
    val albumsLiveData: LiveData<List<Album>> = _albumsLiveData

    private val _artistsLiveData = MutableLiveData<List<Artist>>()
    val artistsLiveData: LiveData<List<Artist>> = _artistsLiveData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadAllData()
    }

    fun loadAllData() {
        loadTracks()
        loadAlbums()
        loadArtists()
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

    fun loadAlbums() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val albums = AlbumRepository.albums.values.toList()
                _albumsLiveData.postValue(albums)
            } catch (e: Exception) {
                _albumsLiveData.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun loadArtists() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val artists = ArtistRepository.artists.values.toList()
                _artistsLiveData.postValue(artists)
            } catch (e: Exception) {
                _artistsLiveData.postValue(emptyList())
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}



