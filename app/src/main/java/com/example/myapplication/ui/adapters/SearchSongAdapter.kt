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

class SearchSongAdapter : RecyclerView.Adapter<SearchSongAdapter.SongViewHolder>() {

        private var songsList: List<Song> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
            val layoutId = R.layout.song_item_list
            val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
            return SongViewHolder(view)
        }

        override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
            val song = songsList[position]
            holder.bind(song)
        }

        override fun getItemCount() = songsList.size

        @SuppressLint("NotifyDataSetChanged")
        fun updateList(newSongs: List<Song>) {
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
                artistTextView.text = song.artist_name

                Glide.with(imageView.context)
                    .load(song.cover_small)
                    .into(imageView)



                btnDescargar?.let { button ->
                    button.setOnClickListener {
                        val downloadService = DownloadService.getInstance()
                        val url = ""
                        val filePath = "${itemView.context.getExternalFilesDir(null)}/${song.title}.mp3"
                        downloadService?.addDownloadTask(url, filePath)
                    }
                }
            }
        }


}