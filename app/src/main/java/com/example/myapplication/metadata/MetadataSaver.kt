package com.example.myapplication.metadata

import android.content.Context
import com.example.myapplication.repository.AlbumRepository
import com.example.myapplication.repository.ArtistRepository
import com.example.myapplication.repository.SongRepository
import com.google.gson.Gson
import java.io.File
import java.io.FileWriter
import java.io.IOException

class MetadataSaver(private val context: Context) {
    private val gson = Gson()

    fun saveAll() {
        try {
            saveAlbums()
            saveArtists()
            saveTracks()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun saveAlbums() = saveToFile("albums.json", AlbumRepository.albums)
    private fun saveArtists() = saveToFile("artists.json", ArtistRepository.artists)
    private fun saveTracks() = saveToFile("tracks.json", SongRepository.tracks)

    private fun <T> saveToFile(fileName: String, map: HashMap<Long, T>) {
        val gson = Gson()
        val jsonString = gson.toJson(map)

        // Get the internal storage directory
        val file = File(context.filesDir, fileName)

        // Write the JSON string to the file
        file.writeText(jsonString)
    }
}