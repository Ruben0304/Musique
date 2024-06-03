package com.example.myapplication.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class FetchMetadata {
    private val client = OkHttpClient()
    private val baseUrl = "https://script.google.com/macros/s/AKfycbylYkJGlnrGT7b3Fu3voVpviFIYs1q92VHnkJwMnxA0JzOonytMqZ59mAgxu5_oPjSrDw/exec"

    suspend fun fetchSongJson(ID: Long?): String? = withContext(Dispatchers.IO) {
        if (ID == null) return@withContext null

        val url = "$baseUrl?action=trackmeta&trackid=$ID"
        val request = Request.Builder()
            .url(url)
            .addHeader("Accept", "application/json")
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    return@withContext response.body?.string()
                } else {
                    // Opcional: manejo de respuestas no exitosas
                    return@withContext null
                }
            }
        } catch (e: IOException) {
            // Log error or handle the IOException
            return@withContext null
        }
    }
}