package com.example.myapplication.ui.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.model.Track
import com.example.myapplication.repository.AlbumRepository
import com.example.myapplication.repository.ArtistRepository
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SharedCardGenerator() {

    companion object {
        suspend fun createShareImage(context: Context, song: Track): Bitmap = withContext(Dispatchers.Main) {
            // Inflar y preparar el layout en el hilo principal
            val inflater = LayoutInflater.from(context)
            val shareView = inflater.inflate(R.layout.share_card, null)
            val backgroundView = shareView.findViewById<ImageView>(R.id.background)
            val cover = shareView.findViewById<ImageView>(R.id.coverSong)
            val titleView = shareView.findViewById<TextView>(R.id.txtTitulo)
            val artistView = shareView.findViewById<TextView>(R.id.txtArtista)

            titleView.text = song.title
            artistView.text = ArtistRepository.getArtistNamesByIds(song.artists)

            // Asegúrate de cargar la imagen de fondo de manera eficiente
            val bitmap = withContext(Dispatchers.IO) {
                Glide.with(context)
                    .asBitmap()
                    .load(AlbumRepository.getSongPicture(song.album_id)) // Utiliza la URL o recurso correcto
                    .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 10)))
                    .submit() // Ancho y alto para Instagram Story
                    .get() // Carga de forma sincrónica dentro de Dispatchers.IO
            }

            val bitmap2 = withContext(Dispatchers.IO) {
                Glide.with(context)
                    .asBitmap()
                    .load(AlbumRepository.getSongPicture(song.album_id)) // Utiliza la URL o recurso correcto
                    .apply(RequestOptions().transform(RoundedCorners(40))) // Aplica un borde redondeado con un radio de 20
                    .submit() // Puedes especificar el tamaño o dejarlo para que Glide determine las dimensiones
                    .get() // Carga de forma sincrónica dentro de Dispatchers.IO
            }



            // Configura el fondo inmediatamente después de cargar la imagen
            backgroundView.setImageBitmap(bitmap)
            cover.setImageBitmap(bitmap2)

            shareView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            shareView.layout(0, 0, shareView.measuredWidth, shareView.measuredHeight)

            val finalBitmap = Bitmap.createBitmap(shareView.measuredWidth, shareView.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(finalBitmap)
            shareView.draw(canvas)

            return@withContext finalBitmap
        }

        suspend fun saveImageToStorage(context: Context, bitmap: Bitmap, fileName: String) {
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "$fileName.jpg")
            try {
                withContext(Dispatchers.IO) {
                    FileOutputStream(file).use { out ->
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                        out.flush()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}