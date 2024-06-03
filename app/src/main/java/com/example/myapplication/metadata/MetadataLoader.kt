package com.example.myapplication.metadata

import android.content.Context
import com.example.myapplication.model.Album
import com.example.myapplication.model.Artist
import com.example.myapplication.model.Track
import com.example.myapplication.model.TrackResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.buffer
import okio.sink
import java.io.File

class MetadataLoader(private val context: Context, json: String) {

    lateinit var track: Track
    lateinit var album: Album
    lateinit var artistas: List<Artist>
    lateinit var artistas_id: ArrayList<Long>

    private val gson = Gson()
    private val trackType = object : TypeToken<TrackResponse>() {}.type
    private val trackResponse: TrackResponse = gson.fromJson(json, trackType)
    private val client = OkHttpClient()

    suspend fun load() {
        val albumPictureUrl = saveImageLocally(trackResponse.albumPictureUrl, "album_${trackResponse.albumId}.jpg")
        album = Album(
            id = trackResponse.albumId,
            title = trackResponse.albumTitle,
            genre = trackResponse.albumGenre,
            pictureUrl = albumPictureUrl.toString(),
            pictureSize = trackResponse.albumPictureSize,
            recordType = trackResponse.albumRecordType,
            position = trackResponse.albumPosition,
            artist_id = trackResponse.albumArtist?.id
        )

        artistas_id = ArrayList()
        artistas = trackResponse.artists.map { artistResponse ->
            val artistPictureUrl = saveImageLocally(artistResponse.pictureUrl, "artist_${artistResponse.id}.jpg")
            artistas_id.add(artistResponse.id)
            Artist(
                id = artistResponse.id,
                name = artistResponse.name,
                pictureUrl = artistPictureUrl.toString(),
                role = artistResponse.role,
                pictureSize = artistResponse.pictureSize
            )
        }

        track = Track(
            id = trackResponse.id,
            title = trackResponse.title,
            isReadable = trackResponse.isReadable,
            isrc = trackResponse.isrc,
            duration = trackResponse.duration,
            size = trackResponse.size,
            release = trackResponse.release,
            artists = artistas_id,
            album_id = trackResponse.albumId
        )
    }

    private suspend fun saveImageLocally(imageUrl: String, fileName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(imageUrl).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val directory = File(context.filesDir, "images")
                    if (!directory.exists()) {
                        directory.mkdir()
                    }
                    val file = File(directory, fileName)
                    val sink = file.sink().buffer()
                    response.body?.source()?.use { source ->
                        sink.writeAll(source)
                    }
                    sink.close()
                    return@withContext file.absolutePath
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return@withContext null
        }
    }

}