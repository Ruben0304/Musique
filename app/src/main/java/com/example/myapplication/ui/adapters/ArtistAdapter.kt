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
import com.example.myapplication.model.Artist

import com.example.myapplication.model.Song
import com.example.myapplication.service.ReproductorMusica
import com.example.myapplication.ui.album.AlbumActivity
import com.example.myapplication.ui.artist.ArtistActivity
import com.example.myapplication.ui.player.Player
import com.example.myapplication.ui.shared.SongMenuActivity


class ArtistAdapter: RecyclerView.Adapter<ArtistAdapter.SongViewHolder>() {
    private var artistList: List<Artist> = listOf()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutId =  R.layout.artist_item_grid
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val artist = artistList[position]
        holder.bind(artist)
    }

   override fun getItemCount() = artistList.size




    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newSongs: List<Artist>) {
       this.artistList = newSongs
        notifyDataSetChanged()
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imagenArtista)
        private val titleTextView: TextView = itemView.findViewById(R.id.tituloArtista)


        fun bind(artist: Artist) {
            titleTextView.text = artist.name


            Glide.with(imageView.context)
                .load(artist.pictureUrl)
                .into(imageView)

            imageView.setOnClickListener {
                val intent = ArtistActivity.newIntent(imageView.context, artist.id)
                imageView.context.startActivity(intent)
            }
        }

    }


}