package com.example.myapplication.repository

import com.example.myapplication.model.Track

object SongRepository {
    var tracks: HashMap<Long, Track> = HashMap()


    fun addTrack(track: Track) {
        tracks[track.id] = track
    }

    fun getTracksByArtistId(artistId: Long): List<Track> {
        return tracks.values.filter { it.artists.contains(artistId) }
    }
    fun getTracksByAlbumId(albumId: Long): List<Track> {
        return tracks.values.filter { it.album_id == albumId }
    }
}