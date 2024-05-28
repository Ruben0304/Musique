package com.example.myapplication.metadata.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.metadata.dao.AlbumDao
import com.example.myapplication.metadata.dao.ArtistDao
import com.example.myapplication.metadata.dao.SongDao
import com.example.myapplication.metadata.entities.AlbumMetadata
import com.example.myapplication.metadata.entities.ArtistMetadata
import com.example.myapplication.metadata.entities.SongMetadata

class AppDatabase {

    @Database(entities = [ArtistMetadata::class, AlbumMetadata::class, SongMetadata::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun artistaDao(): ArtistDao
        abstract fun albumDao(): AlbumDao
        abstract fun cancionDao(): SongDao
        // Otras configuraciones de la base de datos
    }

}