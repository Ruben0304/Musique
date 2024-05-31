package com.example.myapplication.model



object PlaylistManager {
    var songsList: MutableList<Track> = mutableListOf()

    fun setSongs(newSongs: List<Track>) {
        songsList.clear()
        songsList.addAll(newSongs)
    }

    fun getSongs(): List<Track> {
        return songsList
    }
}
