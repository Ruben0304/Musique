package com.example.myapplication.service

import android.content.Context
import com.example.myapplication.model.Song
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem

object ReproductorMusica {
    private var cancionActual: Song? = null
    public var idAReproducir : Long? = null

    fun reproducir(cancion: Song) {
        cancionActual = cancion
        AudioPlayer.play(cancion.preview) // Asumiendo que usas URL para reproducir
    }

    fun obtenerCancionActual(): Song? = cancionActual
    fun obtenerIdAReproducir(): Long? = idAReproducir

    // Métodos de control de reproducción delegados a AudioPlayer
    fun pausar() = AudioPlayer.pause()
    fun detener() = AudioPlayer.stop()
    fun reanudar() = AudioPlayer.resume()
    fun cambiarPosicion(posicion: Long) = AudioPlayer.seekTo(posicion)
}

class AudioPlayer {
    companion object {
        private var exoPlayer: ExoPlayer? = null

        fun initializePlayer(context: Context) {
            exoPlayer = ExoPlayer.Builder(context).build()
        }

        fun play(url: String) {
//            val musicPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath + "/Rauw Alejandro - Algo Mágico (Video Oficial).mp3"
//            val mediaItem = MediaItem.fromUri(Uri.parse("file://$musicPath"))
            val mediaItem = MediaItem.fromUri(url)
            exoPlayer?.let {
                it.setMediaItem(mediaItem)
                it.prepare()  // Preparar el reproductor con el MediaItem
                it.playWhenReady = true  // Comenzar la reproducción cuando el reproductor esté preparado
            }

//            val mediaItem = MediaItem.fromUri(url)
//            exoPlayer?.setMediaItem(mediaItem)
//            exoPlayer?.prepare()
//            exoPlayer?.playWhenReady = true
        }

        fun pause() {
            exoPlayer?.playWhenReady = false
        }

        fun resume() {
            exoPlayer?.playWhenReady = true
        }

        fun stop() = exoPlayer?.stop()
        fun release() {
            exoPlayer?.release()
            exoPlayer = null
        }

        fun getCurrentPosition(): Long = exoPlayer?.currentPosition ?: 0
        fun getDuration(): Long = exoPlayer?.duration ?: 0
        fun seekTo(position: Long) = exoPlayer?.seekTo(position)
        fun isPlaying(): Boolean = exoPlayer?.isPlaying ?: false
    }
}

