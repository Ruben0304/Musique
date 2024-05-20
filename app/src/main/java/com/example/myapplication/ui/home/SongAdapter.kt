package com.example.myapplication.ui.home

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


class SongAdapter(
    var useListLayout: Boolean,
    var useButton: Boolean,
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    private var songsList: List<Song> = listOf()


    companion object {
        private const val TYPE_LIST = 0
        private const val TYPE_GRID = 1
    }

    interface SongItemListener {
        fun onSongClicked(song: Song)
        fun onMenuClicked(song: Song)
        fun showSongMenu(song: Song)
    }


    override fun getItemViewType(position: Int): Int {
        return if (useListLayout) TYPE_LIST else TYPE_GRID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutId = if (viewType == TYPE_LIST) R.layout.song_item_list else R.layout.song_item_grid
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songsList[position]
        holder.bind(song,useButton)
    }

    override fun getItemCount() = songsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newSongs: List<Song>, useListLayout: Boolean, useButton: Boolean, ) {
        this.songsList = newSongs
        this.useListLayout = useListLayout
        this.useButton = useButton
        notifyDataSetChanged()
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imagenCancion)
        private val titleTextView: TextView = itemView.findViewById(R.id.tituloCancion)
        private val artistTextView: TextView = itemView.findViewById(R.id.artistaCancion)
        private val btnDescargar: ImageButton? = itemView.findViewById(R.id.imageButton4)
        private val progress: ProgressBar? = itemView.findViewById(R.id.progressBar)


        fun bind(song: Song, useButton: Boolean) {
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

            if (!useButton)
                btnDescargar?.visibility  = View.GONE
            else
                btnDescargar?.visibility  = View.VISIBLE




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

//        private fun ImageButton.apply3DTouchEffectWithFeedback() {
//            // Configurar el vibrador con compatibilidad hacia atrás
//            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
//                vibratorManager.defaultVibrator
//            } else {
//                @Suppress("DEPRECATION")
//                context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//            }
//
//            // Propiedades para la animación de escala
//            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.95f)
//            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.95f)
//
//            // Animador para el efecto de presión
//            val animatorPress = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY).apply {
//                duration = 200
//                repeatCount = 1
//                repeatMode = ObjectAnimator.REVERSE
//            }
//
//            // Establecer el listener de clic para el botón
//            setOnClickListener {
//                animatorPress.start() // Iniciar la animación
//                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
//                // Aquí puedes agregar el código adicional que desees ejecutar después de la animación y la vibración
//            }
//        }


    }


}