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
import com.example.myapplication.model.Album

import com.example.myapplication.model.Song
import com.example.myapplication.repository.ArtistRepository
import com.example.myapplication.service.ReproductorMusica
import com.example.myapplication.ui.album.AlbumActivity
import com.example.myapplication.ui.player.Player
import com.example.myapplication.ui.shared.SongMenuActivity


class AlbumAdapter: RecyclerView.Adapter<AlbumAdapter.SongViewHolder>() {
    private var albumList: List<Album> = listOf()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutId =  R.layout.song_item_grid
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val album = albumList[position]
        holder.bind(album)
    }

  override fun getItemCount() = albumList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newSongs: List<Album> ) {
        this.albumList = newSongs
        notifyDataSetChanged()
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imagenCancion)
        private val titleTextView: TextView = itemView.findViewById(R.id.tituloCancion)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistaCancion)

        fun bind(album: Album) {
            titleTextView.text = album.title
            artistTextView.text = ArtistRepository.artists[album.artist_id]?.name

            Glide.with(imageView.context)
                .load(album.pictureUrl)
                .into(imageView)

            imageView.setOnClickListener {
                val intent = AlbumActivity.newIntent(imageView.context, album.id)
                imageView.context.startActivity(intent)
            }

        }
    }
}