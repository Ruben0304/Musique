package com.example.myapplication.ui.artist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.SongSearcher
import com.example.myapplication.model.Song
import com.example.myapplication.model.Track
import com.example.myapplication.repository.AlbumRepository
import com.example.myapplication.repository.SongRepository
import kotlinx.coroutines.launch


class ArtistViewModel : ViewModel() {


    // LiveData que almacena la lista de canciones
    private val _cancionesLiveData = MutableLiveData<List<Track>>()
    val cancionesLiveData: MutableLiveData<List<Track>> = _cancionesLiveData



    // Función para buscar canciones basada en el ID
    fun fetchSongsById(artistId: Long) {
        viewModelScope.launch {
            try {
                val songItems = SongRepository.getTracksByArtistId(artistId)
                _cancionesLiveData.value = songItems
            } catch (e: Exception) {
                // Manejo de errores, podrías agregar un LiveData para errores si lo consideras necesario
            }
        }
    }
}

