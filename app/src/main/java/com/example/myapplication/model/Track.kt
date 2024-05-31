package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("track_id") val id: Long,
    @SerializedName("track_is_readable") val isReadable: Boolean,
    @SerializedName("track_title") val title: String,
    @SerializedName("track_isrc") val isrc: String,
    @SerializedName("track_duration") val duration: Int,
    @SerializedName("track_size") val size: Int,
    @SerializedName("track_release") val release: String,
    @SerializedName("track_artists") val artists: List<Artist>,
    @SerializedName("track_album_id") val albumId: Long,
    @SerializedName("track_album_position") val albumPosition: Int?,
    @SerializedName("track_album_title") val albumTitle: String?,
    @SerializedName("track_album_genre") val albumGenre: Int?,
    @SerializedName("track_album_picture_url") val albumPictureUrl: String,
    @SerializedName("track_album_picture_size") val albumPictureSize: Int,
    @SerializedName("track_album_recordtype") val albumRecordType: String?,
    @SerializedName("track_album_artist") val albumArtist: AlbumArtist?
)

data class Artist(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("picture_url") val pictureUrl: String,
    @SerializedName("role") val role: String,
    @SerializedName("picture_size") val pictureSize: Int
)

data class AlbumArtist(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)

data class Album(
    val id: Long,
    val title: String?,
    val genre: Int?,
    val pictureUrl: String,
    val position: Int?,
    val pictureSize: Int,
    val recordType: String?,
    val artist_id: Long?
)


data class Track(
    val id: Long,
    val isReadable: Boolean,
    val title: String,
    val isrc: String,
    val duration: Int,
    val size: Int,
    val release: String,
    val artists: List<Long>,
    val album_id: Long
)