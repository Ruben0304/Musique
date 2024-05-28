package com.example.myapplication.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.util.Base64
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class DownloadService : Service() {
    private val binder = DownloadBinder()
    private val downloadQueue = mutableListOf<DownloadTask>()
    private var currentDownloadJob: Job? = null
    private val client = OkHttpClient()

    companion object {
        private var instance: DownloadService? = null
        fun getInstance(): DownloadService? = instance
    }

    inner class DownloadBinder : Binder() {
        fun getService(): DownloadService = this@DownloadService
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    data class DownloadTask(val url: String, val filePath: String, var isPaused: Boolean = false)

    fun addDownloadTask(url: String, filePath: String) {
        downloadQueue.add(DownloadTask(url, filePath))
        if (currentDownloadJob == null || currentDownloadJob?.isCompleted == true) {
            startNextDownload()
        }
    }

    fun pauseCurrentDownload() {
        currentDownloadJob?.cancel()
        downloadQueue.firstOrNull()?.isPaused = true
    }

    fun resumeDownload() {
        downloadQueue.firstOrNull()?.isPaused = false
        if (currentDownloadJob == null || currentDownloadJob?.isCompleted == true) {
            startNextDownload()
        }
    }

    private fun startNextDownload() {
        if (downloadQueue.isNotEmpty()) {
            val task = downloadQueue.removeAt(0)
            if (!task.isPaused) {
                currentDownloadJob = CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val base64String = fetchSongBase64(task.url)
                        if (base64String != null) {
                            saveSongToFile(base64String, task.filePath)
                            // Mostrar un Toast al finalizar la descarga
                            showToast("Descarga completa")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        startNextDownload()
                    }
                }
                // Mostrar un Toast al iniciar la descarga
                showToast("Descargando...")
            } else {
                downloadQueue.add(0, task)  // Re-add to the front of the queue if paused
            }
        }
    }

    private fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }


    private suspend fun fetchSongBase64(url: String): String? {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder().url(url).build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                response.body?.string()
            }
        }
    }

    private suspend fun saveSongToFile(base64String: String, filePath: String) {
        withContext(Dispatchers.IO) {
            val decodedBytes = Base64.getDecoder().decode(base64String)
            val file = File(filePath)

            // Crear la carpeta si no existe
            val directory = File(Environment.getExternalStorageDirectory(), "PlaygramDownloads")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            FileOutputStream(file).use { fos ->
                fos.write(decodedBytes)
            }
        }
    }

}

