package com.vijaydhoni.newsapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.vijaydhoni.newsapp.R
import com.vijaydhoni.newsapp.databinding.ActivityMainBinding
import com.vijaydhoni.newsapp.presentation.adapter.NewsHeadLineAdapter
import com.vijaydhoni.newsapp.presentation.viewModel.NewsViewModel
import com.vijaydhoni.newsapp.presentation.viewModel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory

    @Inject
    lateinit var newsHeadLineAdapter: NewsHeadLineAdapter

    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHostFragment.navController
        binding.bnvNews.setupWithNavController(
            navController
        )

    }
}