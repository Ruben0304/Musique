package com.example.myapplication.ui.home

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

import com.example.myapplication.model.Song
import com.example.myapplication.ui.album.Album
import com.example.myapplication.ui.charts.ChartsFragment



class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongAdapter
    private lateinit var scroll: ScrollView
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var loadingBar: ProgressBar

//navegacion
    private lateinit var tvInicio: TextView
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





        // Inicializar los TextViews
        tvInicio = view.findViewById(R.id.textView333)
        tvBiblioteca = view.findViewById(R.id.textView5)
        tvDescargas = view.findViewById(R.id.textView4)
        tvCharts = view.findViewById(R.id.textView3)

        tvCharts.setOnClickListener {
            goToChartFragment()
        }

        // Configurar los listeners
       setNavigationItemListeners()
        setupRecyclerView(view)
//        setupSearch(view)
        observeViewModel()
    }


    private fun setNavigationItemListeners() {
        val allTextViews = listOf(tvInicio, tvBiblioteca, tvDescargas)

        allTextViews.forEach { textView ->
            textView.setOnClickListener {
                updateTextStyles(it as TextView, allTextViews)
            }
        }

        // Configuración inicial en la carga de la Activity
        updateTextStyles(tvInicio, allTextViews)  // Pone "Inicio" en negrita por defecto
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
        adapter = SongAdapter(false, false)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        homeViewModel.cancionesLiveData.observe(viewLifecycleOwner) { songs ->
            adapter.updateList(songs, adapter.useListLayout, adapter.useButton)
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

//    private fun setupSearch(view: View) {
//        val editTextSearch: EditText = view.findViewById(R.id.etSearch)
//        editTextSearch.setOnEditorActionListener { v, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                val query = v.text.toString()
//                if (query.isNotEmpty()) {
//                    homeViewModel.searchTracks(query)
//
//                    // Configura el RecyclerView para que ocupe todo el espacio disponible (match_parent)
//                    val inflater = LayoutInflater.from(view.context)
//
//                    // Inflar el nuevo ConstraintLayout desde search_fragment.xml
//                    val newConstraintLayout = inflater.inflate(R.layout.fragment_search, scroll, false) as ConstraintLayout
//
//                    // Remover todos los hijos actuales del ScrollView
//                    scroll.removeAllViews()
//
//                    // Añadir el nuevo ConstraintLayout al ScrollView
//                    scroll.addView(newConstraintLayout)
//                    val recyclerView2 = newConstraintLayout.findViewById<RecyclerView>(R.id.recyclerViewCanciones)
//                    adapter.updateList(emptyList(), true, true)  // Cambia a modo lista
//                    recyclerView2.layoutManager = LinearLayoutManager(requireContext())
//                    recyclerView2.adapter = adapter
//                }
//                true
//            } else {
//                false
//            }
//        }
//    }

    private fun goToChartFragment() {
        if (activity != null) {
            // Comienza la transacción para reemplazar el fragmento
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, ChartsFragment.newInstance())  // Usa el ID correcto y crea una nueva instancia de ChartsFragment
                .addToBackStack(null)  // Agrega la transacción a la pila de retroceso para que el usuario pueda regresar al fragmento anterior
                .commit()
        }
    }







}

