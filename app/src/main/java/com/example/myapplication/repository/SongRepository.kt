package com.example.myapplication.repository

import com.example.myapplication.model.Song
import com.example.myapplication.model.SongInfo
import com.example.myapplication.service.ISongDetailsService
import com.example.myapplication.service.ISongSearchService
import com.example.myapplication.service.SongDetailsService
import com.example.myapplication.service.SongSearchService


class SongRepository (
    private val songSearchService: ISongSearchService = SongSearchService(),
    private val songDetailsService: ISongDetailsService = SongDetailsService()
) {
    suspend fun searchSongs(query: String): List<Song>? {
        return songSearchService.fetchTracks(query)
    }

    suspend fun getSongDetails(id: Long): SongInfo? {
        return songDetailsService.fetchSongInfo(id)
    }
}