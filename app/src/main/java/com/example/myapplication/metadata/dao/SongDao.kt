package com.example.myapplication.metadata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.metadata.entities.SongMetadata

@Dao
interface SongDao {
    @Insert
    suspend fun insertarCancion(cancion: SongMetadata): Long

    @Query("SELECT * FROM canciones WHERE albumId = :albumId")
    suspend fun obtenerCancionesPorAlbum(albumId: Long): List<SongMetadata>
    // Otros métodos como actualizar o eliminar
}