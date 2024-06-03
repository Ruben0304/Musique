package com.example.myapplication.repository

import com.example.myapplication.model.Album
import com.example.myapplication.model.Artist
import com.example.myapplication.model.Track

object AlbumRepository {
     var albums: HashMap<Long, Album> = HashMap()

     fun  getSongPicture(albumId: Long): String? {
          return albums[albumId]?.pictureUrl
     }

}


