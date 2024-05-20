package com.example.myapplication.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Song
import com.example.myapplication.service.ISongSearchService
import com.example.myapplication.service.SongSearchService
import kotlinx.coroutines.launch


class AlbumViewModel (
    private val songService: ISongSearchService = SongSearchService()
) : ViewModel() {


    // LiveData que almacena la lista de canciones
    private val _cancionesLiveData = MutableLiveData<List<Song>?>()
    val cancionesLiveData: MutableLiveData<List<Song>?> = _cancionesLiveData

    // LiveData para manejar los errores o estados de carga
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        // Iniciar una búsqueda inicial o cargar datos iniciales
        fetchSongsById(878787)
    }

    // Función para buscar canciones basada en el ID
    fun fetchSongsById(albumId: Long) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val songItems = songService.fetchTracks("Nadie sabe lo que va a pasar mañana")
                _cancionesLiveData.value = songItems ?: emptyList()
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _isLoading.postValue(false)
                // Manejo de errores, podrías agregar un LiveData para errores si lo consideras necesario
            }
        }
    }
}

