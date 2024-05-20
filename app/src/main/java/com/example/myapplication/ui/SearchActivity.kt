package com.example.myapplication.ui

import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView


import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ui.home.HomeViewModel


class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: SearchView
    private val viewModel: HomeViewModel by viewModels()




}