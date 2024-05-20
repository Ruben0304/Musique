package com.example.myapplication.ui.shared

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BlurMaskFilter.Blur
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.ui.util.UIManager
import com.hoko.blur.HokoBlur
import com.hoko.blur.api.IBlurProcessor


class SongMenuActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE) // Solicita no tener título antes de setContentView
        setContentView(R.layout.song_menu)
        supportActionBar?.hide() // Oculta la barra de acción



        // Aplicar el efecto de difuminado al fondo



        window.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(0.5f)
            addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN) // Asegurar pantalla completa
        }

        // Recuperar los datos pasados a la actividad
        val title = intent.getStringExtra(ARG_TITLE) ?: ""
        val artist = intent.getStringExtra(ARG_ARTIST) ?: ""
        val coverUrl = intent.getStringExtra(ARG_COVER_URL) ?: ""
        // Recuperar la posición 'y' como un Float
        val y = intent.getFloatExtra(Y, 0f)

        // Obtener la referencia al CardView
        val cardView = findViewById<CardView>(R.id.menu_card)

        // Usar post para esperar a que la vista se haya renderizado y medido
        cardView.post {
            // Ahora puedes obtener la altura del CardView
            val dialogHeight = cardView.height

            // Ajustar la posición 'y' para que el centro del diálogo esté alineado con el botón
            val adjustedY = y - (dialogHeight / 2)

            // Crear un nuevo WindowManager.LayoutParams y ajustar la posición 'y'
            val params = window.attributes
            params.gravity = Gravity.TOP
            params.y = adjustedY.toInt() // Establecer la posición ajustada 'y'

            // Aplicar los parámetros actualizados a la ventana
            window.attributes = params
        }

        // Configurar las vistas
        findViewById<TextView>(R.id.tituloCancion).text = title
        findViewById<TextView>(R.id.artistaCancion).text = artist


        Glide.with(this).load(coverUrl).into(findViewById<ImageView>(R.id.imagenCancion))
    }

    companion object {
        const val ARG_TITLE = "arg_title"
        const val ARG_ARTIST = "arg_artist"
        const val ARG_COVER_URL = "arg_cover_url"
        const val Y = "y"

        fun newIntent(
            context: Context, title: String, artist: String, coverUrl: String,
            y: Float
        ): Intent {
            val intent = Intent(context, SongMenuActivity::class.java)
            intent.putExtra(ARG_TITLE, title)
            intent.putExtra(ARG_ARTIST, artist)
            intent.putExtra(ARG_COVER_URL, coverUrl)
            intent.putExtra(Y, y)

            return intent
        }
    }

    private fun applyBlurToBackground() {
        // Obtener el bitmap del fondo actual de la actividad
        val decorView = window.decorView
        decorView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(decorView.drawingCache)
        decorView.isDrawingCacheEnabled = false

        // Configurar el procesador de difuminado
        val blurProcessor = HokoBlur.with(this)
            .scheme(HokoBlur.SCHEME_NATIVE) // Usa el esquema nativo
            .mode(HokoBlur.MODE_STACK) // Usa el modo de pila
            .radius(10) // Define el radio del difuminado
            .sampleFactor(2.0f) // Factor de escala para el tamaño de la imagen
            .forceCopy(false) // Si es true, evita modificar el bitmap original
            .processor()

        // Aplicar el efecto de difuminado al bitmap
        val blurredBitmap = blurProcessor.blur(bitmap)

        // Establecer el bitmap difuminado como fondo de la actividad
        val background = findViewById<ImageView>(R.id.background) // Asegúrate de que este ID es correcto
        background.setImageBitmap(blurredBitmap)
    }



}
