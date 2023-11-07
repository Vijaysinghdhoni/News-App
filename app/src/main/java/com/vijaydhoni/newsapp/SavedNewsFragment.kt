package com.vijaydhoni.newsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vijaydhoni.newsapp.databinding.FragmentSavedNewsBinding
import com.vijaydhoni.newsapp.presentation.MainActivity
import com.vijaydhoni.newsapp.presentation.adapter.NewsHeadLineAdapter
import com.vijaydhoni.newsapp.presentation.viewModel.NewsViewModel


class SavedNewsFragment : Fragment() {
    private lateinit var binding: FragmentSavedNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsHeadLineAdapter: NewsHeadLineAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        newsHeadLineAdapter = (activity as MainActivity).newsHeadLineAdapter

        newsHeadLineAdapter.setOnclickListner { article ->
            Log.d(
                "MYTAG",
                "${article.source} ${article.url.toString()} ${article.title} ${article.author}  ${article.urlToImage}  ${article.content}  ${article.description}  ${article.publishedAt}"
            )

            val bundle = Bundle().apply {
                putSerializable("selectedArticle1", article)
            }

            findNavController().navigate(
              R.id.action_savedNewsFragment_to_newsInfoFragment,
                bundle
            )
        }

        initRecyclerView()

        viewModel.getSavedNewsFromDB().observe(viewLifecycleOwner) {
            newsHeadLineAdapter.differ.submitList(it)
        }

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val positon = viewHolder.adapterPosition
                val article = newsHeadLineAdapter.differ.currentList[positon]
                viewModel.deleteSavedArticles(article)
                Snackbar.make(view, "Deleted Successfully", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewModel.saveArticle(article)
                        }
                    }.show()
            }

        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.newsRecylerView)
        }

    }

    private fun initRecyclerView() {
        binding.newsRecylerView.apply {
            adapter = newsHeadLineAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}