package com.example.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.annotation.RequiresApi

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.metadata.MetadataLoaderFromJsons


import com.example.myapplication.service.AudioPlayer
import com.example.myapplication.service.DownloadService
import com.example.myapplication.ui.charts.ChartsFragment
import com.example.myapplication.ui.download_center.empty.DownloadCenterFragmentEmpty
import com.example.myapplication.ui.home.HomeFragment
import jp.wasabeef.glide.transformations.BlurTransformation


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val trackViewModel: TrackViewModel by viewModels()
    private lateinit var vibrator: Vibrator
    private lateinit var chartLink: LinearLayout
    private lateinit var homeLink: LinearLayout
    private lateinit var itemDownloads: LinearLayout


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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
        val presionarPlaylist: View = includedLayout.findViewById(R.id.presionarParaPlaylist)
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

        })

        val btnAmpliar: ImageButton = includedLayout.findViewById(R.id.btnAmpliar)


        val menuDown: ImageButton = menuNav.findViewById(R.id.imageButton3)

        presionarPlaylist.setOnClickListener {
            findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_home_to_playlist)
        }


        menuDown.setOnClickListener {
            expandView(cardView)
            collapseView(menuNav.findViewById(R.id.playing_now_card_amplied))

        }

        btnAmpliar.setOnClickListener {
            expandView(menuNav.findViewById(R.id.playing_now_card_amplied))
            collapseView(cardView)
        }
// Iniciar el servicio
        Intent(this, DownloadService::class.java).also { intent ->
            startService(intent)
        }

        val chartFragment = ChartsFragment()
        val homeFramgent = HomeFragment()
        val downloadsFragment = DownloadCenterFragmentEmpty()

        chartLink = menuNav.findViewById(R.id.itemChart)
        homeLink = menuNav.findViewById(R.id.itemLibrary)
        itemDownloads = menuNav.findViewById(R.id.itemDownloads)

        // Establece el OnClickListener para el elemento de menú
        chartLink.setOnClickListener {

            // Realiza la transacción de fragmento usando el FragmentManager
            supportFragmentManager.beginTransaction().apply {
                // Reemplaza el contenido del contenedor de fragmentos con el nuevo fragmento
                replace(R.id.nav_host_fragment_activity_main, chartFragment)
                // Opcionalmente, puedes añadir la transacción a la pila de retroceso si deseas que el usuario pueda volver atrás
                addToBackStack(null)
                // Confirma la transacción
                commit()
            }
            menuDown.performClick()
        }

        homeLink.setOnClickListener {

            // Realiza la transacción de fragmento usando el FragmentManager
            supportFragmentManager.beginTransaction().apply {
                // Reemplaza el contenido del contenedor de fragmentos con el nuevo fragmento
                replace(R.id.nav_host_fragment_activity_main, homeFramgent)
                // Opcionalmente, puedes añadir la transacción a la pila de retroceso si deseas que el usuario pueda volver atrás
                addToBackStack(null)
                // Confirma la transacción
                commit()
            }
            menuDown.performClick()
        }

        itemDownloads.setOnClickListener {

            // Realiza la transacción de fragmento usando el FragmentManager
            supportFragmentManager.beginTransaction().apply {
                // Reemplaza el contenido del contenedor de fragmentos con el nuevo fragmento
                replace(R.id.nav_host_fragment_activity_main, downloadsFragment)
                // Opcionalmente, puedes añadir la transacción a la pila de retroceso si deseas que el usuario pueda volver atrás
                addToBackStack(null)
                // Confirma la transacción
                commit()
            }
            menuDown.performClick()
        }



        val metadataLoaderFromJsons = MetadataLoaderFromJsons(this)
        metadataLoaderFromJsons.loadAll()

//        val  metadataDownloader = MetadataDownloader(this,FetchMetadata(), MetadataSaver(this))
//        metadataDownloader.download(2403815945)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun hideSystemUI() {
        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                )
    }

    private fun expandView(view: View) {
        view.apply {
            visibility = View.VISIBLE
            alpha = 0f
            scaleX = 0.5f
            scaleY = 0.5f
            animate()
                .alpha(1f)
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(250)
                .setInterpolator(OvershootInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .setInterpolator(BounceInterpolator())
                            .setListener(null)
                        vibratePhone()
                    }
                })
        }
    }

    private fun collapseView(view: View) {
        view.apply {
            animate()
                .alpha(0f)
                .scaleX(0.5f)
                .scaleY(0.5f)
                .setDuration(300)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.GONE
                        vibratePhone()
                    }
                })
        }
    }

    private fun vibratePhone() {
        if (vibrator.hasVibrator()) {
            val vibrationEffect =
                VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        }
    }
}