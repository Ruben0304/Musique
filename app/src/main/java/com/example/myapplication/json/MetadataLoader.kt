package com.example.myapplication.json

import com.example.myapplication.model.Album
import com.example.myapplication.model.Artist
import com.example.myapplication.model.Track
import com.example.myapplication.model.TrackResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MetadataLoader {

    lateinit var track: Track
    lateinit var album: Album
    lateinit var artistas: List<Artist>
    lateinit var artistas_id: ArrayList<Long>

    val json = """
        {
            "track_id": 2403815945,
            "track_is_readable": true,
            "track_title": "QLONA",
            "track_isrc": "USUG12305257",
            "track_duration": 173,
            "track_size": 3792160,
            "track_release": "2023-08-11",
            "track_artists": [
                {
                    "id": 5297021,
                    "name": "KAROL G",
                    "picture_url": "https://e-cdns-images.dzcdn.net/images/artist/93a3706d1e35078fc3a5e0c1a34f9ae5/500x500-000000-80-0-0.jpg",
                    "role": "Main",
                    "picture_size": 45431
                },
                {
                    "id": 80365122,
                    "name": "Peso Pluma",
                    "picture_url": "https://e-cdns-images.dzcdn.net/images/artist/f8d47cb639eed29075bd5fd147c2d892/500x500-000000-80-0-0.jpg",
                    "role": "Main",
                    "picture_size": 48314
                }
            ],
            "track_album_id": 474422645,
            "track_album_position": 5,
            "track_album_title": "MAÑANA SERÁ BONITO (BICHOTA SEASON)",
            "track_album_genre": 197,
            "track_album_picture_url": "https://e-cdns-images.dzcdn.net/images/cover/eacb2df2f49b1e67266ed70c8debd8d3/500x500-000000-80-0-0.jpg",
            "track_album_picture_size": 66822,
            "track_album_recordtype": "album",
            "track_album_artist": {
                "id": 5297021,
                "name": "KAROL G"
            }
        }
    """.trimIndent()


    private val gson = Gson()
    // Define el tipo de TrackResponse en vez de una lista
    private val trackType = object : TypeToken<TrackResponse>() {}.type
    // Deserializa el JSON en una sola instancia de TrackResponse
    private val trackResponse: TrackResponse = gson.fromJson(json, trackType)



    fun load() {
         album = Album(
            id = trackResponse.albumId,
            title = trackResponse.albumTitle,
            genre = trackResponse.albumGenre,
            pictureUrl = trackResponse.albumPictureUrl,
            pictureSize = trackResponse.albumPictureSize,
            recordType = trackResponse.albumRecordType,
            position = trackResponse.albumPosition,
            artist_id = trackResponse.albumArtist?.id
        )

         artistas_id= ArrayList()
        // Crear la lista de artistas
         artistas = trackResponse.artists.map { artistResponse ->
            artistas_id.add(artistResponse.id)
            Artist(
                id = artistResponse.id,
                name = artistResponse.name,
                pictureUrl = artistResponse.pictureUrl,
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
}