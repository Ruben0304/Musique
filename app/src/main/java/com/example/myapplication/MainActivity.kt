package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.example.myapplication.databinding.ActivityMainBinding

import com.example.myapplication.service.AudioPlayer
import com.example.myapplication.service.ReproductorMusica
import com.example.myapplication.ui.player.Player
import jp.wasabeef.glide.transformations.BlurTransformation


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val trackViewModel: TrackViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AudioPlayer.initializePlayer(this)
//        hideSystemUI()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val includedLayout: View = findViewById(R.id.include_playing_bar)


        // Observa el LiveData para cambios en la imagen de la pista actual
        val cardView: CardView = findViewById(R.id.playing_now_card)
        val currentTrackImage: ImageView = findViewById(R.id.current_track_image)
        val menuNav: View = includedLayout.findViewById(R.id.menu_nav)
        val caverMenuImg: ImageView = menuNav.findViewById(R.id.imageView3)
        val currentTrackImageM: ImageView = menuNav.findViewById(R.id.current_track_image)

// Observa el LiveData para cambios en la imagen de la pista actual
        trackViewModel.currentTrackImage.observe(this, Observer { imageUrl ->
            Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 10)))
                .into(currentTrackImage)

            Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(35, 20)))
                .into(caverMenuImg)

            Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(currentTrackImageM)

//            // Después de cargar la imagen, utiliza Palette para extraer el color predominante
//            currentTrackImage.post {
//                val bitmap = (currentTrackImage.drawable as BitmapDrawable).bitmap
//                Palette.from(bitmap).generate { palette ->
//                    val dominantColor = palette?.getDominantColor(Color.RED) ?: Color.RED
//
//                    // Crea un nuevo GradientDrawable con el color predominante
//                    val drawable = GradientDrawable().apply {
//                        shape = GradientDrawable.RECTANGLE
//                        setColor(dominantColor) // Establece el color de fondo
//                        cornerRadius = 50f // Debe coincidir con el radio de la tarjeta
//                    }
//
//                    // Establece el nuevo drawable como fondo de cardView
//                    cardView.background = drawable
//                }
//            }
        })

        val btnAmpliar: ImageButton = includedLayout.findViewById(R.id.btnAmpliar)


        val menuDown: ImageButton = menuNav.findViewById(R.id.imageButton3)

        menuDown.setOnClickListener {

            cardView.visibility = View.VISIBLE

            cardView.alpha = 0f

            ViewCompat.animate(cardView)
                .alpha(1f)
                .setDuration(200)
                .setListener(null)
                .start()

            // Ocultar con animación de desvanecimiento
            ViewCompat.animate(menuNav)
                .alpha(0f)
                .setDuration(200)
                .setListener(object : ViewPropertyAnimatorListener {
                    override fun onAnimationStart(view: View) {
                        // No-op
                    }

                    override fun onAnimationEnd(view: View) {
                        menuNav.visibility = View.GONE

                    }

                    override fun onAnimationCancel(view: View) {
                        // No-op
                    }
                })
                .start()
        }

        btnAmpliar.setOnClickListener {
            // Mostrar con animación de desvanecimiento
            cardView.visibility = View.GONE
            menuNav.visibility = View.VISIBLE

            menuNav.alpha = 0f

            ViewCompat.animate(menuNav)
                .alpha(1f)
                .setDuration(200)
                .setListener(null)
                .start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun hideSystemUI() {
        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                )
    }
}