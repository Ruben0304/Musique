package com.example.myapplication.model



object PlaylistManager {
    var songsList: MutableList<Song> = mutableListOf()

    fun setSongs(newSongs: List<Song>) {
        songsList.clear()
        songsList.addAll(newSongs)
    }

    fun getSongs(): List<Song> {
        return songsList
    }
}
