package com.example.myapplication.api
import com.example.myapplication.model.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException




class SongSearchService : ISongSearchService {
    private val client = OkHttpClient()
    private val gson = Gson()

    override suspend fun fetchTracks(query: String): List<Song>? = withContext(Dispatchers.IO) {
        try {
            val url = "https://script.google.com/macros/s/AKfycbylYkJGlnrGT7b3Fu3voVpviFIYs1q92VHnkJwMnxA0JzOonytMqZ59mAgxu5_oPjSrDw/exec?action=search&q=$query"
            val request = Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                responseBody?.let {
                    // Deserializa la respuesta completa incluyendo los datos de las pistas
                    val trackResponse = gson.fromJson(it, object : TypeToken<List<Song>>() {}.type) as List<Song>
                    return@withContext trackResponse
                }
            }
            return@withContext null
        } catch (e: IOException) {
            // Log error, inform the user, or handle the IOException
            return@withContext null
        }
    }
}

interface ISongSearchService {
    suspend fun fetchTracks(query: String): List<Song>?
}
