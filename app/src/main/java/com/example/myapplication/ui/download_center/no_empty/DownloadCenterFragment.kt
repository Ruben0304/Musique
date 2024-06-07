package com.example.myapplication.ui.download_center.no_empty

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentDownloadCenterBinding
import com.example.myapplication.databinding.FragmentSearchBinding
import com.example.myapplication.ui.adapters.SearchSongAdapter
import com.example.myapplication.ui.adapters.SongAdapter
import com.example.myapplication.ui.adapters.SongDownloadsAdapter

class DownloadCenterFragment : Fragment() {

    companion object {
        fun newInstance() = DownloadCenterFragment()
    }

    private val viewModel: DownloadCenterViewModel by viewModels()

    private var _binding: FragmentDownloadCenterBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchSongAdapter
    private lateinit var adapterDownloaders: SongDownloadsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadCenterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchSongAdapter(requireContext())
        adapterDownloaders = SongDownloadsAdapter()

        // Configurar ambos RecyclerView con sus respectivos adapters y layout managers
        binding.recyclerViewCanciones.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCanciones.adapter = adapter

        binding.recyclerViewDescargados.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDescargados.adapter = adapterDownloaders

        binding.imageButton2.setOnClickListener {
            switchRecyclerViews()
        }

        setupSearch()
        observeTracks()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun switchRecyclerViews() {
        // Animación para ocultar recyclerViewCanciones y mostrar recyclerViewDescargados
        if (binding.recyclerViewCanciones.visibility == View.VISIBLE) {
            binding.recyclerViewCanciones.animate()
                .alpha(0f)
                .translationY(-binding.recyclerViewCanciones.height.toFloat())
                .setDuration(500)
                .withEndAction {
                    binding.recyclerViewCanciones.visibility = View.GONE
                    binding.recyclerViewCanciones.alpha = 1f
                    binding.recyclerViewCanciones.translationY = 0f
                }

            binding.recyclerViewDescargados.alpha = 0f
            binding.recyclerViewDescargados.translationY = binding.recyclerViewDescargados.height.toFloat()
            binding.recyclerViewDescargados.visibility = View.VISIBLE
            binding.recyclerViewDescargados.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .start()
        } else {
            // Animación para ocultar recyclerViewDescargados y mostrar recyclerViewCanciones
            binding.recyclerViewDescargados.animate()
                .alpha(0f)
                .translationY(binding.recyclerViewDescargados.height.toFloat())
                .setDuration(500)
                .withEndAction {
                    binding.recyclerViewDescargados.visibility = View.GONE
                    binding.recyclerViewDescargados.alpha = 1f
                    binding.recyclerViewDescargados.translationY = 0f
                }

            binding.recyclerViewCanciones.alpha = 0f
            binding.recyclerViewCanciones.translationY = -binding.recyclerViewCanciones.height.toFloat()
            binding.recyclerViewCanciones.visibility = View.VISIBLE
            binding.recyclerViewCanciones.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(500)
                .start()
        }
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

    private fun observeTracks() {
        viewModel.tracksLiveData.observe(viewLifecycleOwner) { tracks ->
            adapterDownloaders.updateList(tracks)
        }
    }

    private fun setupSearch() {
        binding.searchBox.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = v.text.toString()
                if (query.isNotEmpty()) {
                    binding.recyclerViewDescargados.animate()
                        .alpha(0f)
                        .translationY(binding.recyclerViewDescargados.height.toFloat())
                        .setDuration(500)
                        .withEndAction {
                            binding.recyclerViewDescargados.visibility = View.GONE
                            binding.recyclerViewDescargados.alpha = 1f
                            binding.recyclerViewDescargados.translationY = 0f
                        }

                    binding.recyclerViewCanciones.alpha = 0f
                    binding.recyclerViewCanciones.translationY = -binding.recyclerViewCanciones.height.toFloat()
                    binding.recyclerViewCanciones.visibility = View.VISIBLE
                    binding.recyclerViewCanciones.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(500)
                        .start()
                    viewModel.searchTracks(query)
                }
                true
            } else {
                false
            }
        }
    }
}
