package com.example.myapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.RequiresApi

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.databinding.ActivityMainBinding

import com.example.myapplication.service.AudioPlayer
import com.example.myapplication.service.ReproductorMusica
import com.example.myapplication.ui.player.Player
import jp.wasabeef.glide.transformations.BlurTransformation


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AudioPlayer.initializePlayer(this)
        hideSystemUI()




        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController


        // Encuentra el layout que contiene el botón
        val includedLayout: View = findViewById(R.id.include_playing_bar)

        val current_track_image: ImageView = includedLayout.findViewById(R.id.current_track_image)



        val btnAmpliar: ImageButton = includedLayout.findViewById(R.id.btnAmpliar)


        // Encuentra el layout del menú que quieres mostrar/ocultar
        val menuNav: View = includedLayout.findViewById(R.id.menu_nav)

        val imgTransp: ImageView = menuNav.findViewById(R.id.imageView8)


        Glide.with(includedLayout)
            .load(R.drawable.album_cover) // Reemplaza con tu drawable o uri
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25,10))) // Ajusta el radio y el muestreo
            .into(current_track_image)

        btnAmpliar.setOnClickListener {
            // Cambiar la visibilidad del menú
            imgTransp.visibility = View.VISIBLE
            menuNav.visibility =   if (menuNav.visibility == View.VISIBLE) View.GONE else View.VISIBLE

        }



        // Asumiendo que estás dentro de una Activity
//        btnAmpliar.setOnClickListener {
//
//            if (ReproductorMusica.obtenerCancionActual() != null) {
//                val intent = Intent(this, Player::class.java)
//                startActivity(intent)
//                overridePendingTransition(
//                    com.google.android.material.R.anim.abc_popup_enter,
//                    com.google.android.material.R.anim.abc_popup_exit
//                )
//            }
//            else{
//                // Mostrar un diálogo indicando que no hay música reproduciéndose
//                val builder = AlertDialog.Builder(this)
//                builder.setTitle("Sin reproducción")
//                builder.setMessage("Actualmente no se está reproduciendo ninguna canción.")
//                builder.setPositiveButton("OK") { dialog, which ->
//                    dialog.dismiss()
//                }
//                val dialog: AlertDialog = builder.create()
//                dialog.show()
//            }
//
//        }



    }
    @RequiresApi(Build.VERSION_CODES.P)
    fun hideSystemUI() {
        window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.decorView.systemUiVisibility = (


                 View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                )
    }

}
