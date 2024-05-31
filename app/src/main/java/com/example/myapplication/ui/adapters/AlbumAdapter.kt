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


class AlbumAdapter: RecyclerView.Adapter<AlbumAdapter.SongViewHolder>() {
//    private var albumList: List<Album> = listOf()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutId =  R.layout.song_item_grid
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
//        val album = albumList[position]
//        holder.bind(album)
    }

//   override fun getItemCount() = albumList.size

    override fun getItemCount() =3


//    @SuppressLint("NotifyDataSetChanged")
//    fun updateList(newSongs: List<Album>, useListLayout: Boolean, useButton: Boolean, ) {
////        this.songsList = newSongs
////        this.useListLayout = useListLayout
////        this.useButton = useButton
//        notifyDataSetChanged()
//    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imagenCancion)
        private val titleTextView: TextView = itemView.findViewById(R.id.tituloCancion)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistaCancion)
        private val btnDescargar: ImageButton? = itemView.findViewById(R.id.imageButton4)
        private val progress: ProgressBar? = itemView.findViewById(R.id.progressBar)


//        fun bind(album: Album) {
//            titleTextView.text = album.title
////            artistTextView.text = song.artist_name
//            artistTextView.text = album.title
//
//            Glide.with(imageView.context)
////                .load(song.cover_small)
//                .load(song.album.coverMedium)
//                .into(imageView)
//
//            imageView.setOnClickListener {
////                ReproductorMusica.idAReproducir = song.id
//                ReproductorMusica.reproducir(song)
//                val intent = Intent(imageView.context, Player::class.java)
//
//
//                imageView.context.startActivity(intent)
//            }
//
//
//
//        }




    }


}