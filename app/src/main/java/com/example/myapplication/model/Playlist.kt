package com.example.myapplication.model

data class Playlist(
    val id: String,
    val name: String,
    val description: String?,
    val songs: MutableList<Song>
)
