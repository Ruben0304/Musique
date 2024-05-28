package com.example.myapplication.metadata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.metadata.entities.ArtistMetadata

@Dao
interface ArtistDao {
    @Insert
    suspend fun insertarArtista(artista: ArtistMetadata): Long

    @Query("SELECT * FROM artistas")
    suspend fun obtenerArtistas(): List<ArtistMetadata>
    // Otros métodos como actualizar o eliminar
}