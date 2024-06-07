package com.example.myapplication.ui.download_center.empty

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.ui.download_center.no_empty.DownloadCenterFragment

class DownloadCenterFragmentEmpty : Fragment() {

    companion object {
        fun newInstance() = DownloadCenterFragmentEmpty()
    }

    private val viewModel: DownloadCenterEmptyViewModel by viewModels()
    private lateinit var buttonBack: ImageButton
    private lateinit var searchButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_download_center_empty, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar buttonBack y configurar el listener
        buttonBack = view.findViewById(R.id.backButtom)
        searchButton = view.findViewById(R.id.imageButton11)
        buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        val downloadCenterFragment = DownloadCenterFragment.newInstance()
        searchButton.setOnClickListener {
            val downloadCenterFragment = DownloadCenterFragment()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                // Configura las animaciones personalizadas
                setCustomAnimations(
                    R.anim.slide_in_right, // Animación de entrada del nuevo fragmento
                    R.anim.slide_out_left, // Animación de salida del fragmento actual
                    R.anim.slide_in_left,  // Animación de entrada al volver atrás
                    R.anim.slide_out_right // Animación de salida al volver atrás
                )
                // Reemplaza el contenido del contenedor de fragmentos con el nuevo fragmento
                replace(R.id.nav_host_fragment_activity_main, downloadCenterFragment)
                // Añade la transacción a la pila de retroceso
                addToBackStack(null)
                // Confirma la transacción
                commit()
            }
        }
    }
}