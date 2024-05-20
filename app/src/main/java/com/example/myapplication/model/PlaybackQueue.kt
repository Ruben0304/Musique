package com.example.myapplication.model

class PlaybackQueue {
    val upcomingSongs: MutableList<Song> = mutableListOf()

    fun addSongToQueue(song: Song) {
        // Agregar canción a la cola
    }

    fun removeSongFromQueue(song: Song) {
        // Eliminar canción de la cola
    }

    fun moveSongInQueue(song: Song, newPosition: Int) {
        // Mover canción dentro de la cola
    }

    // Más métodos según sea necesario
}