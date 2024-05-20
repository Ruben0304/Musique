package com.example.myapplication.ui.shared

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.myapplication.R

class MenuComponent(context: Context, attrs: AttributeSet?): LinearLayout(context, attrs) {
    init {
        // Inflar el layout del menú
        LayoutInflater.from(context).inflate(R.layout.menu_nav, this, true)

//         Configurar la lógica de clic para la primera imagen
        val firstImageView: ImageView = findViewById(R.id.imageView8)
        firstImageView.setOnClickListener {
            this.visibility =  View.GONE
            firstImageView.visibility  = View.GONE
        }


    }
}
