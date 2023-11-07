package com.vijaydhoni.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.vijaydhoni.newsapp.databinding.FragmentNewsInfoBinding
import com.vijaydhoni.newsapp.presentation.MainActivity
import com.vijaydhoni.newsapp.presentation.viewModel.NewsViewModel

class NewsInfoFragment : Fragment() {
    private lateinit var binding: FragmentNewsInfoBinding
    private lateinit var viewModel: NewsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewsInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: NewsInfoFragmentArgs by navArgs()
        val article = args.selectedArticle1

        viewModel = (activity as MainActivity).viewModel

        val url = article.url
        binding.webViewNewsInfo.apply {
            webViewClient = WebViewClient()


            if (!url.isNullOrBlank()) {
                loadUrl(url)
            } else {
                Snackbar.make(
                    requireView(),
                    "Invalid or empty URL for the selected article",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }

        binding.floatingActionButton.setOnClickListener {
            viewModel.saveArticle(article)

            Snackbar.make(
                requireView(),
                "Saved Succesfully",
                Snackbar.LENGTH_LONG
            ).show()

        }

    }
}