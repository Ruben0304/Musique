package com.example.myapplication.metadata

import android.content.Context
import android.widget.Toast
import com.example.myapplication.api.FetchMetadata
import com.example.myapplication.repository.AlbumRepository
import com.example.myapplication.repository.ArtistRepository
import com.example.myapplication.repository.SongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MetadataDownloader(
    private val context: Context,
    private val fetchMetadataService: FetchMetadata,
    private val metadataSaver: MetadataSaver
) {
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    fun download(songId: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            val json = fetchMetadataService.fetchSongJson(songId)
            if (json == null) {
                println("Error fetching song metadata")
                return@launch
            }

            val metadataLoader = MetadataLoader(context,json)
            metadataLoader.load() // Carga los datos en las estructuras
            SongRepository.tracks.putIfAbsent(metadataLoader.track.id, metadataLoader.track)
            AlbumRepository.albums.putIfAbsent(metadataLoader.album.id, metadataLoader.album)
            metadataLoader.artistas.forEach { artist ->
                ArtistRepository.artists.putIfAbsent(artist.id, artist)
            }


            coroutineScope.launch(Dispatchers.Main) {
                metadataSaver.saveAll() // Guarda todas las estructuras en archivos JSON
//                showToast("Metadata saved successfully")
                println("Metadata saved successfully")
            }
        }
    }

//    private fun showToast(message: String) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//    }
}