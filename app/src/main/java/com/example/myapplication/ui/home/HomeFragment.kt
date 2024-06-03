package com.example.myapplication.ui.home

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Album
import com.example.myapplication.model.Song
import com.example.myapplication.model.Track
import com.example.myapplication.ui.adapters.AlbumAdapter
import com.example.myapplication.ui.adapters.ArtistAdapter
import com.example.myapplication.ui.adapters.SearchSongAdapter
import com.example.myapplication.ui.adapters.SongAdapter

import com.example.myapplication.ui.adapters.SongGridAdapter
import com.example.myapplication.ui.charts.ChartsFragment
import java.util.Timer
import java.util.TimerTask



class HomeFragment : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongAdapter
    private lateinit var adapterAlbum: AlbumAdapter
    private lateinit var adapterArtist: ArtistAdapter

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
        setupViews(view)
        setupRecyclerViewSongs(view)  // Initial view setup
        observeTracks()  // Start with tracks view


        // Crear un Timer que se ejecute cada 2 segundos
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                if (isAdded) {
                    // Actualizar los datos en el hilo principal
                    requireActivity().runOnUiThread {
                        homeViewModel.loadAllData()
                    }
                }
            }
        }, 0, 2000)
    }

    private fun setupViews(view: View) {
        loadingBar = view.findViewById(R.id.progressBar2)
        searchButton = view.findViewById(R.id.search_button)
        recyclerView = view.findViewById(R.id.recycler_view_songs)

        searchButton.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_search)
        }

        tvBiblioteca = view.findViewById(R.id.textView5)
        tvDescargas = view.findViewById(R.id.textView4)
        tvCharts = view.findViewById(R.id.textView3)

        tvBiblioteca.setOnClickListener {
            setupRecyclerViewSongs(view)
            observeTracks()
        }

        tvDescargas.setOnClickListener {
            setupRecyclerViewAlbums(view)
            observeAlbums()
        }

        tvCharts.setOnClickListener {
            setupRecyclerViewArtists(view)
            observeArtists()
        }

        // Initially select "Biblioteca"
        tvBiblioteca.performClick()



    }

    private fun setupRecyclerViewSongs(view: View) {
        adapter = SongAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupRecyclerViewAlbums(view: View) {
        adapterAlbum = AlbumAdapter()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapterAlbum
    }

    private fun setupRecyclerViewArtists(view: View) {
        adapterArtist = ArtistAdapter()
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = adapterArtist

    }

     fun observeTracks() {
        homeViewModel.tracksLiveData.observe(viewLifecycleOwner) { tracks ->
            adapter.updateList(tracks)
        }
        observeLoadingState()
    }

     fun observeAlbums() {
        homeViewModel.albumsLiveData.observe(viewLifecycleOwner) { albums ->
            adapterAlbum.updateList(albums)
        }
        observeLoadingState()
    }

     fun observeArtists() {
        homeViewModel.artistsLiveData.observe(viewLifecycleOwner) { artists ->
            adapterArtist.updateList(artists)
        }
        observeLoadingState()
    }

    private fun observeLoadingState() {
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            updateUIState(isLoading)
        }
    }

    private fun updateUIState(isLoading: Boolean) {
        loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }




}


