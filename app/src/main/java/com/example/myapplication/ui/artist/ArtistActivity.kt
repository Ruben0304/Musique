package com.example.myapplication.ui.artist

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import com.example.myapplication.R
import com.example.myapplication.model.Album
import com.example.myapplication.model.Artist
import com.example.myapplication.repository.AlbumRepository
import com.example.myapplication.repository.ArtistRepository
import com.example.myapplication.ui.adapters.SongAdapter


class ArtistActivity : AppCompatActivity() {


    companion object {
        private const val ARG_ARTIST_ID = "arg_artist_id"

        fun newIntent(
            context: Context, artist_id: Long
        ): Intent {
            val intent = Intent(context, ArtistActivity::class.java)
            intent.putExtra(ARG_ARTIST_ID, artist_id)
            return intent
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var txtNombre: TextView
    private lateinit var adapter: SongAdapter
    private lateinit var artist: Artist
    private val viewModel: ArtistViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        supportActionBar?.hide()
        setContentView(R.layout.activity_artist)


        //inicializar datos
        val artist_id = intent.getLongExtra(ARG_ARTIST_ID, -1L) // Recibe el ID del item
        artist = ArtistRepository.artists[artist_id]!!

        //inicializar vistas
        txtNombre = findViewById(R.id.txtNombre)
        recyclerView = findViewById(R.id.recyclerViewCanciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SongAdapter()
        val artist_cover = findViewById<ImageView>(R.id.imageView4dffdf)

        txtNombre.text = artist.name

        Glide.with(this)
            .load(artist.pictureUrl)
            .into(artist_cover)

        val backButton = findViewById<ImageView>(R.id.imageView5)
        backButton.setOnClickListener {
            finish()
        }



        viewModel.fetchSongsById(artist_id)
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
                adapter.updateList(songs)

        }

    }



}

