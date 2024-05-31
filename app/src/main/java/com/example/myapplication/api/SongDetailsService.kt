package com.example.myapplication.api



import com.example.myapplication.model.TrackResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class SongDetailsService {
    private val gson = Gson()
     suspend fun fetchSongInfo(ID: Long?): TrackResponse? = withContext(Dispatchers.IO) {

        val client = OkHttpClient()
        try {
            val request = Request.Builder()
                .url("https://script.google.com/macros/s/AKfycbylYkJGlnrGT7b3Fu3voVpviFIYs1q92VHnkJwMnxA0JzOonytMqZ59mAgxu5_oPjSrDw/exec?action=trackmeta&trackid=$ID")
                .addHeader("Accept", "application/json")
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                responseBody?.let {
                    return@withContext gson.fromJson(it, TrackResponse::class.java)
                }
            }
            return@withContext null
        } catch (e: IOException) {
            // Log error, inform the user, or handle the IOException
            return@withContext null
        }
        return@withContext null
    }
}