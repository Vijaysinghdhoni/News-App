package com.vijaydhoni.newsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vijaydhoni.newsapp.data.util.Resource
import com.vijaydhoni.newsapp.databinding.FragmentNewsBinding
import com.vijaydhoni.newsapp.presentation.MainActivity
import com.vijaydhoni.newsapp.presentation.adapter.NewsHeadLineAdapter
import com.vijaydhoni.newsapp.presentation.viewModel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsHeadLineAdapter

    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0
    private val country = "us"
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsHeadLineAdapter

        newsAdapter.setOnclickListner { article ->
            Log.d(
                "MYTAG",
                "${article.source} ${article.url.toString()} ${article.title} ${article.author}  ${article.urlToImage}  ${article.content}  ${article.description}  ${article.publishedAt}"
            )

            val bundle = Bundle().apply {
                putSerializable("selectedArticle1", article)
            }

            findNavController().navigate(
                R.id.action_newsFragment_to_newsInfoFragment,
                bundle
            )


        }

        initRecylerView()
        viewNewsList()
        setSearchedFunctionality()
    }

    private fun setSearchedFunctionality() {

        binding.newsSearchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getSearchedNews("us", p0.toString(), page)
                viewSearchedNews()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.getSearchedNews("us", p0.toString(), page)
                    viewSearchedNews()
                }
                return false
            }

        })

        binding.newsSearchView.setOnCloseListener {
            initRecylerView()
            viewNewsList()
            false
        }

    }

    private fun viewNewsList() {
        viewModel.getNewsHeadLines(country, page)

        viewModel.newsHeadLines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)

                        pages = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                        } else {
                            it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Snackbar.make(
                            activity!!,
                            binding.root,
                            "An Error occurred : $it",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun initRecylerView() {
        binding.newsRecylerView.adapter = newsAdapter
        binding.newsRecylerView.layoutManager = LinearLayoutManager(activity)
        binding.newsRecylerView.addOnScrollListener(this@NewsFragment.onScrollListner)
    }

    private fun showProgressBar() {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        binding.progressBar.visibility = View.INVISIBLE
    }

    private val onScrollListner = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.newsRecylerView.layoutManager as LinearLayoutManager

            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            Log.d(
                "MyTag",
                "sizeOflist is $sizeOfCurrentList visibleItems are $visibleItems topPosition is $topPosition"
            )

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling

            if (shouldPaginate) {
                page++
                viewModel.getNewsHeadLines(country, page)
                isScrolling = false
            }
        }
    }

    private fun viewSearchedNews() {

        viewModel.searchedNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)

                        pages = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                        } else {
                            it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Snackbar.make(
                            activity!!,
                            binding.root,
                            "An Error occurred : $it",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

    }

}