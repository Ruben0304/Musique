package com.example.myapplication.ui.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.example.myapplication.R
import com.google.android.exoplayer2.upstream.Allocation

class UIManager {

    companion object{
        fun blurBackground(view: View) {
            val context = view.context
            val activity = context as Activity

            // Tomar una captura de pantalla del Activity actual
            val rootView = activity.window.decorView.rootView
            rootView.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(rootView.drawingCache)
            rootView.isDrawingCacheEnabled = false

            // Aplicar el efecto de difuminado al bitmap capturado
            val blurredBitmap = blurBitmap(bitmap, activity)

            // Mostrar el bitmap difuminado en un ImageView que será el fondo del diálogo
            val dialog = Dialog(activity)
            dialog.setContentView(R.layout.layout_fondo_difuminado)
            val background = dialog.findViewById(R.id.background) as ImageView
            background.setImageBitmap(blurredBitmap)
            dialog.show()
        }

        fun blurBitmap(originalBitmap: Bitmap, context: Context): Bitmap {
            // Crear un bitmap mutable para aplicar el efecto de difuminado
            val outputBitmap = Bitmap.createBitmap(originalBitmap)
            val renderScript = RenderScript.create(context)
            val tmpIn = android.renderscript.Allocation.createFromBitmap(renderScript, originalBitmap)
            val tmpOut = android.renderscript.Allocation.createFromBitmap(renderScript, outputBitmap)

            // Usar ScriptIntrinsicBlur de RenderScript para el efecto de difuminado
            val theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
            theIntrinsic.setRadius(25f) // Ajusta el radio del difuminado según necesites
            theIntrinsic.setInput(tmpIn)
            theIntrinsic.forEach(tmpOut)
            tmpOut.copyTo(outputBitmap)
            return outputBitmap
        }


    }

}