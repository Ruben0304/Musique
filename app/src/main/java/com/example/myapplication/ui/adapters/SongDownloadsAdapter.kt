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
import com.example.myapplication.model.Track
import com.example.myapplication.repository.AlbumRepository
import com.example.myapplication.repository.ArtistRepository
import com.example.myapplication.service.DownloadService
import com.example.myapplication.service.ReproductorMusica
import com.example.myapplication.ui.player.Player
import com.example.myapplication.ui.shared.MenuComponent
import com.example.myapplication.ui.shared.SongMenuActivity


class SongDownloadsAdapter : RecyclerView.Adapter<SongDownloadsAdapter.SongViewHolder>() {
    private var songsList: List<Track> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutId = R.layout.song_item_download_center
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songsList[position]
        holder.bind(song)
    }

    override fun getItemCount() = songsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newSongs: List<Track>) {
        this.songsList = newSongs
        notifyDataSetChanged()
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imagenCancion)
        private val titleTextView: TextView = itemView.findViewById(R.id.tituloCancion)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistaCancion)
        private val btnDescargar: ImageButton = itemView.findViewById(R.id.imageButton4)
        private lateinit var cover_url: String
        private lateinit var artists: String


        fun bind(song: Track) {

            artists = ArtistRepository.getArtistNamesByIds(song.artists)
            cover_url = AlbumRepository.getSongPicture(song.album_id).toString()

            titleTextView.text = song.title
            artistTextView.text = artists

            Glide.with(imageView.context)
                .load(cover_url)
                .into(imageView)

            imageView.setOnClickListener {
                ReproductorMusica.reproducir(song)
                val intent = Intent(imageView.context, Player::class.java)
                imageView.context.startActivity(intent)
            }


            btnDescargar.setOnClickListener {
                val location = IntArray(2)
                btnDescargar.getLocationOnScreen(location)
                val y = location[1]
                val intent = SongMenuActivity.newIntent(btnDescargar.context, song.title, artists, cover_url, y.toFloat())
                btnDescargar.context.startActivity(intent)
            }


        }
    }
}
