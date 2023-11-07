package com.vijaydhoni.newsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vijaydhoni.newsapp.data.model.Article
import com.vijaydhoni.newsapp.databinding.NewsItemListBinding

class NewsHeadLineAdapter : RecyclerView.Adapter<NewsHeadLineAdapter.MyViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            NewsItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }


    inner class MyViewHolder(private val binding: NewsItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.tvNewstitle.text = article.title
            binding.tvNewsDiscription.text = article.description
            binding.tvNewsPublished.text = article.publishedAt
           binding.tvNewsSource.text = article.source?.name

            Glide.with(binding.newsImage)
                .load(article.urlToImage)
                .into(binding.newsImage)

            binding.root.setOnClickListener {

                onClickListner?.let {
                      it(article)
                }

            }

        }

    }

    private var onClickListner :((Article) -> Unit)? = null

    fun setOnclickListner(listner : (Article) ->Unit) {

        onClickListner = listner
    }

}