package com.example.myapplication.ui.search

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.ui.adapters.SearchSongAdapter
import com.example.myapplication.ui.adapters.SongAdapter

class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchSongAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchSongAdapter(requireContext())
        binding.recyclerViewCanciones.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCanciones.adapter = adapter

        // Postergar la obtención del ancho del botón "internet" hasta que la vista esté completamente creada
        val lottieAnimationView = view.findViewById<LottieAnimationView>(R.id.loadingBar)
        lottieAnimationView.setAnimation("loading.json")
        lottieAnimationView.playAnimation()

        setupSearch()
        observeViewModel()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.cancionesLiveData.observe(viewLifecycleOwner, Observer { songs ->
            adapter.updateList(songs)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.recyclerViewCanciones.visibility = if (isLoading) View.GONE else View.VISIBLE
        })
    }

    private fun setupSearch() {
        binding.searchBox.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()
                if (query.isNotEmpty()) {
                    viewModel.searchTracks(query)
                }
                true
            } else {
                false
            }
        }
    }



}