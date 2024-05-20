package com.example.myapplication.ui.player

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.model.Album
import com.example.myapplication.model.Song
import com.example.myapplication.service.AudioPlayer
import com.example.myapplication.service.ReproductorMusica
import com.example.myapplication.ui.util.SharedCardGenerator
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class Player : AppCompatActivity() {

    private lateinit var viewModel: PlayerViewModel

    private lateinit var textViewp: TextView
    private lateinit var txtTitulo: TextView
    private lateinit var txtArtista: TextView
    private lateinit var textViewv: TextView
    private lateinit var textViewx: TextView
    private lateinit var textViewwww: TextView
    private lateinit var textVieww: TextView
    private lateinit var txtDuracion: TextView
    private lateinit var seekBar: SeekBar
    private val handler = Handler(Looper.getMainLooper())
    private val updateSeekBar = object : Runnable {
        override fun run() {
            val currentPosition = AudioPlayer.getCurrentPosition()
            val totalDuration = AudioPlayer.getDuration()
            if (totalDuration > 0) {
                val progress = ((currentPosition.toDouble() / totalDuration.toDouble()) * 100).toInt()
                seekBar.progress = progress
                updateTextViews(currentPosition)  // Asegúrate de pasar el valor correcto directamente
            }
            handler.postDelayed(this, 1000)
        }
    }

    private fun updateUI(songInfo: Song) {
        val listaRepfdf = findViewById<CardView>(R.id.listaRepfdf)
        songInfo?.let {
            txtTitulo.text = it.title
            txtArtista.text = it.artist.name
            txtDuracion.text = formatTime(it.duration.toLong())
            Glide.with(this)
                .load(it.album.coverBig) // Reemplaza con tu drawable o uri
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25,10))) // Ajusta el radio y el muestreo
                .into(findViewById(R.id.background))

            Glide.with(this)
                .load(it.album.coverBig) // Reemplaza con tu drawable o uri
                .into(findViewById(R.id.cover))


            listaRepfdf.setOnClickListener {
                lifecycleScope.launch {
                    songInfo?.let { cancion ->
                        val bitmap = SharedCardGenerator.createShareImage(this@Player, cancion)
                        val fileName = "SharedImage_${System.currentTimeMillis()}"
                        saveImageToGallery(this@Player, bitmap, fileName)
                        // Mostrar un Toast para confirmar el guardado
                        runOnUiThread {
                            Toast.makeText(this@Player, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


        }

        //configurar play/pause
        val playPauseButton = findViewById<ImageButton>(R.id.play_pause)
        playPauseButton.setImageResource(R.drawable.icon_pause) // Cambia a icono de pause
//        if (AudioPlayer.isPlaying()) {
//            playPauseButton.setImageResource(R.drawable.icon_pause) // Cambia a icono de pause
//        } else {
//            playPauseButton.setImageResource(R.drawable.player_control_play)  // Cambia a icono de play
//        }


        playPauseButton.setOnClickListener {
            if (AudioPlayer.isPlaying()) {
                AudioPlayer.pause()
                playPauseButton.setImageResource(R.drawable.player_control_play)  // Cambia a icono de play
            } else {
                AudioPlayer.resume()
                playPauseButton.setImageResource(R.drawable.icon_pause) // Cambia a icono de pause
            }
        }

        handler.post(updateSeekBar)
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override  fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        supportActionBar?.hide()
        viewModel = PlayerViewModel()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        handler.post(updateSeekBar)
        hideSystemUI()
        setupViews()


//        viewModel.songInfo.observe(this, Observer { songInfo ->
//            if (songInfo != null) {
//                updateUI(songInfo)
//            }
//        })

        ReproductorMusica.obtenerCancionActual()?.let { updateUI(it) }




    }


    private fun setupViews() {
        txtTitulo = findViewById(R.id.txtTitulo)
        txtArtista = findViewById(R.id.txtArtista)
        txtDuracion = findViewById(R.id.txtDuracion)
        textViewp = findViewById(R.id.textViewp)
        textViewv = findViewById(R.id.txtArtist)
        textViewx = findViewById(R.id.textViewx)
        textViewwww = findViewById(R.id.textViewwww)
        textVieww = findViewById(R.id.textVieww)
        seekBar = findViewById(R.id.seekBar2)






        checkAndUpdateDuration()
        // Ajustar el máximo del SeekBar y el texto inicial del txtDuracion






        //  Configura los TextViews inicialmente
        updateTextViews(0)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // Calcula la nueva posición de la pista basada en el progreso del seekBar
                    val totalDuration = AudioPlayer.getDuration()
                    val newPosition = (progress.toDouble() / 100 * totalDuration).toLong()

                    // Actualiza los textViews relacionados con el tiempo de reproducción
                    updateTextViews(newPosition)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Detiene la actualización automática del seekBar para evitar saltos mientras el usuario ajusta el seekBar
                handler.removeCallbacks(updateSeekBar)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Busca a la nueva posición cuando el usuario suelta el seekBar
                val totalDuration = AudioPlayer.getDuration()
                val newPosition = (seekBar.progress.toDouble() / 100 * totalDuration).toLong()
                AudioPlayer.seekTo(newPosition)

                // Reanuda la actualización automática del seekBar después de que el usuario termina de ajustar
                handler.post(updateSeekBar)
            }
        })

        txtArtista.setOnClickListener {
            // Obtener el texto actual de txtArtista
            val artistName = txtArtista.text.toString()

            // Crear un Intent para iniciar ArtistDetailsActivity
            val intent = Intent(this, Album::class.java).apply {
                // Pasar el nombre del artista como extra en el Intent

            }
            startActivity(intent)
        }





    }

    private fun updateTextViews(progress: Long) {
        textViewx.text = formatTime(progress)  // Tiempo actual

        val timeLeftOne = maxOf(0L, progress - 1000)  // Un segundo antes
        textViewv.text = formatTime(timeLeftOne)  // Verifica que textViewv es el correcto

        val timeLeftTwo = maxOf(0L, progress - 2000)  // Dos segundos antes
        textViewp.text = formatTime(timeLeftTwo)  // Verifica que textViewp es el correcto

        val timeRightOne = progress + 1000  // Un segundo después
        textViewwww.text = formatTime(timeRightOne)  // Verifica que textViewwww es el correcto

        val timeRightTwo = progress + 2000  // Dos segundos después
        textVieww.text = formatTime(timeRightTwo)  // Verifica que textVieww es el correcto
    }



    fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun checkAndUpdateDuration() {
        lifecycleScope.launch {
            var duration = AudioPlayer.getDuration()
            while (duration <= 0) {  // Continúa verificando hasta que la duración sea válida
                delay(500)  // Espera medio segundo antes de volver a comprobar
                duration = AudioPlayer.getDuration()
            }
            txtDuracion.text = formatTime(duration)  // Actualiza el TextView cuando la duración es válida
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun hideSystemUI() {
        window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    fun saveImageToGallery(context: Context, bitmap: Bitmap, fileName: String) {
        val imageOutStream: OutputStream?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$fileName.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            imageOutStream = uri?.let { context.contentResolver.openOutputStream(it) }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
            val image = File(imagesDir, "$fileName.jpg")
            imageOutStream = FileOutputStream(image)
        }

        imageOutStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            it.flush()
        }
    }



}





