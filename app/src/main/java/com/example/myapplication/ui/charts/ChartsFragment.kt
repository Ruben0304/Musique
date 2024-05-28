package com.example.myapplication.ui.charts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.adapters.ChartAdapter
import com.example.myapplication.ui.adapters.ChartItem

class ChartsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChartAdapter

    companion object {
        fun newInstance() = ChartsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_charts, container, false)
        recyclerView = view.findViewById(R.id.recyclerCh)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ChartAdapter(listOf(
            ChartItem(1, "https://th.bing.com/th/id/R.6b4a6a37ed4ce2202008f2b5ca785608?rik=1r3nb4aGd2UfgA&pid=ImgRaw&r=0", "Artist 1"),
            ChartItem(1, "https://th.bing.com/th/id/R.6b4a6a37ed4ce2202008f2b5ca785608?rik=1r3nb4aGd2UfgA&pid=ImgRaw&r=0", "Artist 1"),
            ChartItem(1, "https://th.bing.com/th/id/R.6b4a6a37ed4ce2202008f2b5ca785608?rik=1r3nb4aGd2UfgA&pid=ImgRaw&r=0", "Artist 1"),
            ChartItem(1, "https://th.bing.com/th/id/R.6b4a6a37ed4ce2202008f2b5ca785608?rik=1r3nb4aGd2UfgA&pid=ImgRaw&r=0", "Artist 1"),
            ChartItem(1, "https://th.bing.com/th/id/R.6b4a6a37ed4ce2202008f2b5ca785608?rik=1r3nb4aGd2UfgA&pid=ImgRaw&r=0", "Artist 1")
        ))
        recyclerView.adapter = adapter
        return view
    }



}
