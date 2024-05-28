package com.example.myapplication.metadata.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.metadata.entities.AlbumMetadata

@Dao
interface AlbumDao {
    @Insert
    suspend fun insertarAlbum(album: AlbumMetadata): Long

    @Query("SELECT * FROM albumes WHERE artistaId = :artistaId")
    suspend fun obtenerAlbumesPorArtista(artistaId: Long): List<AlbumMetadata>
    // Otros métodos como actualizar o eliminar
}