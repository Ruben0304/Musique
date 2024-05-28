package com.example.myapplication.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.Song
import com.example.myapplication.service.ReproductorMusica
import com.example.myapplication.ui.player.Player
import com.example.myapplication.ui.shared.SongMenuActivity


class SongGridAdapter: RecyclerView.Adapter<SongGridAdapter.SongViewHolder>() {
    private var songsList: List<Song> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutId =  R.layout.song_item_grid
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songsList[position]
        holder.bind(song)
    }

    override fun getItemCount() = songsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newSongs: List<Song>, useListLayout: Boolean, useButton: Boolean, ) {
        this.songsList = newSongs
        notifyDataSetChanged()
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imagenCancion)
        private val titleTextView: TextView = itemView.findViewById(R.id.tituloCancion)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistaCancion)
        private val btnDescargar: ImageButton? = itemView.findViewById(R.id.imageButton4)
        private val progress: ProgressBar? = itemView.findViewById(R.id.progressBar)


        fun bind(song: Song) {
            titleTextView.text = song.title
//            artistTextView.text = song.artist_name
            artistTextView.text = song.artist.name

            Glide.with(imageView.context)
//                .load(song.cover_small)
                .load(song.album.coverMedium)
                .into(imageView)

            imageView.setOnClickListener {
//                ReproductorMusica.idAReproducir = song.id
                ReproductorMusica.reproducir(song)
                val intent = Intent(imageView.context, Player::class.java)


                imageView.context.startActivity(intent)
            }

            btnDescargar?.let { button ->
                button.setOnClickListener {


                    val location = IntArray(2)
                    // Obtener la posición del botón en la pantalla
                    button.getLocationOnScreen(location)
                    val y = location[1]

                    val intent = itemView.context?.let {
//                        SongMenuActivity.newIntent(it, song.title, song.artist_name, song.cover_small)
                        SongMenuActivity.newIntent(it, song.title, song.artist.name, song.album.coverMedium,y.toFloat())

                    }
                    itemView.context?.startActivity(intent)
                }
            }


        }

    }
}