package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Songs(
val id: Long,
val title: String,
val duration: Int,
val cover_small: String,
val artist_name: String
)


data class SongInfo(
    val track_id: Long,
    val track_is_readable: Boolean,
    val track_title: String,
    val track_isrc: String,
    val track_duration: Int,
    val track_size: Long,
    val track_release: String,
    val track_artists: List<Artist>,
    val track_album_id: Long,
    val track_album_position: Int,
    val track_album_title: String,
    val track_album_genre: Int,
    val track_album_picture_url: String,
    val track_album_picture_size: Int,
    val track_album_recordtype: String,
    val track_album_artist: AlbumArtist
)



// Definición de las clases de datos
data class TrackResponse(
    @SerializedName("data") val data: List<Song>,
    @SerializedName("total") val total: Int,
    @SerializedName("next") val next: String
)

data class Song(
    @SerializedName("id") val id: Long,
    @SerializedName("readable") val readable: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("title_short") val titleShort: String,
    @SerializedName("title_version") val titleVersion: String,
    @SerializedName("link") val link: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("rank") val rank: Int,
    @SerializedName("explicit_lyrics") val explicitLyrics: Boolean,
    @SerializedName("explicit_content_lyrics") val explicitContentLyrics: Int,
    @SerializedName("explicit_content_cover") val explicitContentCover: Int,
    @SerializedName("preview") val preview: String,
    @SerializedName("md5_image") val md5Image: String,
    @SerializedName("artist") val artist: Artist,
    @SerializedName("album") val album: Album,
    @SerializedName("type") val type: String
)

data class Artist(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("link") val link: String,
    @SerializedName("picture") val picture: String,
    @SerializedName("picture_small") val pictureSmall: String,
    @SerializedName("picture_medium") val pictureMedium: String,
    @SerializedName("picture_big") val pictureBig: String,
    @SerializedName("picture_xl") val pictureXl: String,
    @SerializedName("tracklist") val tracklist: String,
    @SerializedName("type") val type: String
)

data class Album(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("cover") val cover: String,
    @SerializedName("cover_small") val coverSmall: String,
    @SerializedName("cover_medium") val coverMedium: String,
    @SerializedName("cover_big") val coverBig: String,
    @SerializedName("cover_xl") val coverXl: String,
    @SerializedName("md5_image") val md5Image: String,
    @SerializedName("tracklist") val tracklist: String,
    @SerializedName("type") val type: String
)

