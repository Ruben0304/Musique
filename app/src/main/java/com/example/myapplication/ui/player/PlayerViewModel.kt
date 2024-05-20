package com.example.myapplication.ui.player

import androidx.lifecycle.ViewModel

class PlayerViewModel() : ViewModel() {
//    private val songDetailsService: ISongDetailsService = SongDetailsService()
//    // LiveData para mantener la información de la canción actual.
//    private val _songInfo = MutableLiveData<Song?>()
//    val songInfo: LiveData<Song?> = _songInfo
//
//    // Método para solicitar la información de la canción desde un servicio externo.
//    fun fetchSongDetails(trackId: Long) {
//        // viewModelScope asegura que las coroutines se cancelen cuando el ViewModel se limpie.
//        viewModelScope.launch {
//            // Llamar al servicio para obtener la información de la canción de manera asíncrona.
//            val songDetails = songDetailsService.fetchSongInfo(trackId)
//            // Postear los resultados en el LiveData para que la UI pueda reaccionar a los cambios.
//            _songInfo.postValue(songDetails)
//            // Si se obtiene una canción válida, reproducirla usando el reproductor global.
//            songDetails?.let {
//                ReproductorMusica.reproducir(it)
//            }
//        }
//    }
}
