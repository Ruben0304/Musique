package com.example.myapplication.metadata

import android.content.Context
import android.widget.Toast
import com.example.myapplication.model.Album
import com.example.myapplication.model.Artist
import com.example.myapplication.model.Track
import com.example.myapplication.repository.AlbumRepository
import com.example.myapplication.repository.ArtistRepository
import com.example.myapplication.repository.SongRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import kotlin.reflect.KType
import kotlin.reflect.typeOf


class MetadataLoaderFromJsons (private val context: Context) {
    private val gson = Gson()

    fun loadAll() {
        AlbumRepository.albums = loadAlbums()
        ArtistRepository.artists = loadArtists()
        SongRepository.tracks = loadTracks()
    }

    private fun loadAlbums(): HashMap<Long, Album> {
        return loadFromFile("albums.json", object : TypeToken<HashMap<Long, Album>>() {}.type)
    }

    private fun loadArtists(): HashMap<Long, Artist> {
        return loadFromFile("artists.json", object : TypeToken<HashMap<Long, Artist>>() {}.type)
    }

    private fun loadTracks(): HashMap<Long, Track> {
        return loadFromFile("tracks.json", object : TypeToken<HashMap<Long, Track>>() {}.type)
    }

    private fun <T> loadFromFile(fileName: String, typeOfT: java.lang.reflect.Type): T {
        val file = File(context.filesDir, fileName)
        if (file.exists()) {
            val jsonString = file.readText()
            return gson.fromJson(jsonString, typeOfT) ?: run {
                showErrorToast("Failed to parse JSON from $fileName")
                throw IllegalArgumentException("Error parsing JSON from $fileName")
            }
        } else {
            showErrorToast("No existing file found for $fileName. Initializing empty structure.")
            return hashMapOf<Long, T>() as T
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}