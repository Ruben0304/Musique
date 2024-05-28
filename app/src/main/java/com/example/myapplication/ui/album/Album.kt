package com.example.myapplication.ui.album

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.ui.adapters.SongAdapter
import jp.wasabeef.glide.transformations.BlurTransformation


class Album : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var txtNombre: TextView
    private lateinit var adapter: SongAdapter
    private val viewModel: AlbumViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideSystemUI()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        supportActionBar?.hide()
        setContentView(R.layout.activity_artist_album)

        val itemId = intent.getLongExtra("id", -1L) // Recibe el ID del item

        txtNombre = findViewById(R.id.txtNombre)
        recyclerView = findViewById(R.id.recyclerViewCanciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SongAdapter()
        val imgCovre = findViewById<ImageView>(R.id.imageView4dffdf)
        val imgtrans = findViewById<ImageView>(R.id.imageView4fdf)
        val drawableId = resources.getIdentifier("grupo_10391", "drawable", packageName)
        val drawableId2 = resources.getIdentifier("album_cover", "drawable", packageName)
        Glide.with(this)
//            .load("https://is1-ssl.mzstatic.com/image/thumb/Video116/v4/a3/87/a1/a387a1e7-ec57-a97b-5161-6a29180fbcdb/Job9319765c-f2fa-4be5-8a9c-2f26ff99627e-157945606-PreviewImage_preview_image_nonvideo_sdr-Time1698718820409.png/316x316bb.webp")
            .load(drawableId2)
            .into(imgCovre)
        Glide.with(this)
            .load(drawableId)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(40,50)))
            .into(imgtrans)


        val backButton = findViewById<ImageView>(R.id.imageView5)
        backButton.setOnClickListener {
            finish()
        }

        setupRecyclerView()
        observeViewModel()




    }


    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewCanciones)
        // Ahora también pasas this como listener
        adapter = SongAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


    private fun observeViewModel() {
        viewModel.cancionesLiveData.observe(this) { songs ->
            if (songs != null) {
                adapter.updateList(songs)
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun hideSystemUI() {
        window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}

