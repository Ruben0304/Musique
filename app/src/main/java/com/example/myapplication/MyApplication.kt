package com.example.myapplication

import android.app.Application
import com.example.myapplication.json.MetadataLoader
import com.example.myapplication.repository.AlbumRepository
import com.example.myapplication.repository.ArtistRepository
import com.example.myapplication.repository.SongRepository
import com.hoko.blur.BuildConfig

import timber.log.Timber


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Inicialización de Timber para logs en la aplicación
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

         val  metadataLoader = MetadataLoader()
         metadataLoader.load()
         SongRepository.tracks.putIfAbsent(metadataLoader.track.id, metadataLoader.track)
         AlbumRepository.albums.putIfAbsent(metadataLoader.album.id, metadataLoader.album)

        metadataLoader.artistas.forEach {
            ArtistRepository.artists.putIfAbsent(it.id, it)
        }





        // Configuraciones globales, inicialización de librerías, etc.
        // Por ejemplo, inicialización de Analytics, Crashlytics, etc.
    }
}