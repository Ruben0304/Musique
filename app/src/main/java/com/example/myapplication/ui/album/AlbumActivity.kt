package com.example.myapplication.ui.album

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
import com.example.myapplication.repository.AlbumRepository
import com.example.myapplication.repository.ArtistRepository
import com.example.myapplication.ui.adapters.SongAdapter


class AlbumActivity : AppCompatActivity() {


    companion object {
        private const val ARG_ALBUM_ID = "arg_album_id"

        fun newIntent(
            context: Context, album_id: Long
        ): Intent {
            val intent = Intent(context, AlbumActivity::class.java)
            intent.putExtra(ARG_ALBUM_ID, album_id)
            return intent
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var txtNombre: TextView
    private lateinit var txtArtista: TextView
    private lateinit var adapter: SongAdapter
    private lateinit var album: Album
    private val viewModel: AlbumViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        supportActionBar?.hide()
        setContentView(R.layout.activity_album)


        //inicializar datos
        val album_id = intent.getLongExtra(ARG_ALBUM_ID, -1L) // Recibe el ID del item
        album = AlbumRepository.albums[album_id]!!

        //inicializar vistas
        txtNombre = findViewById(R.id.txtNombre)
        txtArtista = findViewById(R.id.txtArtista)
        recyclerView = findViewById(R.id.recyclerViewCanciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SongAdapter()
        val album_cover = findViewById<ImageView>(R.id.imageView4dffdf)

        txtNombre.text = album.title
        txtArtista.text = ArtistRepository.artists[album.artist_id]?.name ?: "Artista desconocido"

        Glide.with(this)
            .load(album.pictureUrl)
            .into(album_cover)

        val backButton = findViewById<ImageView>(R.id.imageView5)
        backButton.setOnClickListener {
            finish()
        }



        viewModel.fetchSongsById(album_id)
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

