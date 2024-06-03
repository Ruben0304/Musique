package com.example.myapplication.repository

import com.example.myapplication.model.Artist

object ArtistRepository {
    var artists: HashMap<Long, Artist> = HashMap()

    fun getArtistNamesByIds(artistIds: List<Long>): String {
        return artistIds.mapNotNull { artists[it]?.name }
            .joinToString(separator = ", ")
    }
}
