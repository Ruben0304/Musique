package com.example.myapplication.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

import com.example.myapplication.ui.adapters.SongAdapter
import com.example.myapplication.ui.adapters.SongGridAdapter
import com.example.myapplication.ui.charts.ChartsFragment


class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongGridAdapter

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var loadingBar: ProgressBar
    private lateinit var searchButton: ImageButton


    private lateinit var tvBiblioteca: TextView
    private lateinit var tvDescargas: TextView
    private lateinit var tvCharts: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingBar = view.findViewById(R.id.progressBar2)
        searchButton = view.findViewById(R.id.search_button)



        searchButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_search)
        }

        tvBiblioteca = view.findViewById(R.id.textView5)
        tvDescargas = view.findViewById(R.id.textView4)
        tvCharts = view.findViewById(R.id.textView3)

        tvCharts.setOnClickListener {
            goToChartFragment()
        }

        // Configurar los listeners
        setNavigationItemListeners()
        setupRecyclerView(view)
        observeViewModel()
    }


    private fun setNavigationItemListeners() {
        val allTextViews = listOf(tvBiblioteca, tvDescargas)

        allTextViews.forEach { textView ->
            textView.setOnClickListener {
                updateTextStyles(it as TextView, allTextViews)
            }
        }

        // Configuración inicial en la carga de la Activity
        updateTextStyles(tvBiblioteca, allTextViews)  // Pone "Inicio" en negrita por defecto
    }

    private fun updateTextStyles(selectedTextView: TextView, allTextViews: List<TextView>) {
        allTextViews.forEach { textView ->
            if (textView == selectedTextView) {
                // Aplicar negrita al texto seleccionado
                textView.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD)
            } else {
                // Reestablecer a normal a los textos no seleccionados
                textView.setTypeface(Typeface.DEFAULT, Typeface.NORMAL)
            }
        }
    }


    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view_songs)
        // Ahora también pasas this como listener
        adapter = SongGridAdapter()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        homeViewModel.tracksLiveData.observe(viewLifecycleOwner) { tracks ->
            adapter.updateList(tracks)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            updateUIState(isLoading)
        }
    }

    private fun updateUIState(isLoading: Boolean) {
        loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }


    private fun goToChartFragment() {
        if (activity != null) {
            // Comienza la transacción para reemplazar el fragmento
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.nav_host_fragment_activity_main,
                    ChartsFragment.newInstance()
                )  // Usa el ID correcto y crea una nueva instancia de ChartsFragment
                .addToBackStack(null)  // Agrega la transacción a la pila de retroceso para que el usuario pueda regresar al fragmento anterior
                .commit()
        }
    }


}

