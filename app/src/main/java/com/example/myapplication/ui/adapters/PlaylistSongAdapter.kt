package com.example.myapplication.ui.adapters

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.model.PlaylistManager
import com.example.myapplication.model.Song
import com.example.myapplication.service.ReproductorMusica

@SuppressLint("StaticFieldLeak")
object PlaylistSongAdapter : RecyclerView.Adapter<PlaylistSongAdapter.SongViewHolder>() {

    private var songsList: MutableList<Song> = PlaylistManager.getSongs().toMutableList()
    var currentExpandedImg: ImageView? = null
    var currentExpandedView: CardView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutId = R.layout.song_item_playlist
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songsList[position]
        holder.bind(song)
    }

    override fun getItemCount() = songsList.size

    fun updateList(newSongs: List<Song>) {
        PlaylistManager.songsList = newSongs.toMutableList()
        songsList = newSongs.toMutableList()
        notifyDataSetChanged()
    }


    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imagenCancion)
        private val titleTextView: TextView = itemView.findViewById(R.id.tituloCancion)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistaCancion)
        private val btnCancelar: ImageButton? = itemView.findViewById(R.id.imageButton4)
        private val cardView: CardView? = itemView.findViewById(R.id.cardView)

        fun bind(song: Song) {
            titleTextView.text = song.title
            artistTextView.text = song.artist.name


            Glide.with(imageView.context)
                .load(song.album.coverMedium)
                .apply(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).fitCenter())
                .into(imageView)

            titleTextView.setOnClickListener {

                if (currentExpandedView != cardView) {

                    // Animación para reducir el tamaño del CardView a su estado original
                    currentExpandedView?.let { view ->
                        val startWidth = view.width
                        val startHeight = view.height
                        val endWidth = 30
                        val endHeight = 30

                        val widthAnimator = ValueAnimator.ofInt(startWidth, endWidth)
                        widthAnimator.addUpdateListener { animator ->
                            val value = animator.animatedValue as Int
                            view.layoutParams = view.layoutParams.apply {
                                width = value
                            }
                            view.requestLayout()
                        }

                        val heightAnimator = ValueAnimator.ofInt(startHeight, endHeight)
                        heightAnimator.addUpdateListener { animator ->
                            val value = animator.animatedValue as Int
                            view.layoutParams = view.layoutParams.apply {
                                height = value
                            }
                            view.requestLayout()
                        }

                        val animatorSet = AnimatorSet()
                        animatorSet.playTogether(widthAnimator, heightAnimator)
                        animatorSet.duration = 300
                        animatorSet.interpolator = DecelerateInterpolator()
                        animatorSet.start()
                    }

                    if (currentExpandedImg != null) {
                        currentExpandedImg!!.visibility = View.GONE
                    }

                    ReproductorMusica.reproducir(song)
                    imageView.visibility = View.VISIBLE

                    val startWidth = cardView?.width ?: 0
                    val startHeight = cardView?.height ?: 0
                    val endWidth = 100
                    val endHeight = 100

                    val widthAnimator = ValueAnimator.ofInt(startWidth, endWidth)
                    widthAnimator.addUpdateListener { animator ->
                        val value = animator.animatedValue as Int
                        cardView?.layoutParams = cardView?.layoutParams?.apply {
                            width = value
                        }
                        cardView?.requestLayout()
                    }

                    val heightAnimator = ValueAnimator.ofInt(startHeight, endHeight)
                    heightAnimator.addUpdateListener { animator ->
                        val value = animator.animatedValue as Int
                        cardView?.layoutParams = cardView?.layoutParams?.apply {
                            height = value
                        }
                        cardView?.requestLayout()
                    }

                    val imageAnimator = ValueAnimator.ofFloat(1.0f, 1.0f)
                    imageAnimator.addUpdateListener { animator ->
                        val value = animator.animatedValue as Float
                        imageView.scaleX = value
                        imageView.scaleY = value
                        imageView.layoutParams = imageView.layoutParams.apply {
                            width = endWidth
                            height = endHeight
                        }
                        imageView.requestLayout()
                    }

                    val animatorSet = AnimatorSet()
                    animatorSet.playTogether(widthAnimator, heightAnimator, imageAnimator)
                    animatorSet.duration = 300
                    animatorSet.interpolator = DecelerateInterpolator()
                    animatorSet.start()
                }

                currentExpandedView = cardView
                currentExpandedImg = imageView

            }

            btnCancelar?.setOnClickListener {
                // Lógica para cancelar la reproducción
            }
        }
    }

    fun removeItem(position: Int) {
        if (position in PlaylistManager.songsList.indices) {
            PlaylistManager.songsList.removeAt(position)
            songsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    fun moveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition in PlaylistManager.songsList.indices && toPosition in PlaylistManager.songsList.indices) {
            val item = PlaylistManager.songsList.removeAt(fromPosition)
            PlaylistManager.songsList.add(toPosition, item)
            val adapterItem = songsList.removeAt(fromPosition)
            songsList.add(toPosition, adapterItem)
            notifyItemMoved(fromPosition, toPosition)
        }
    }
}
