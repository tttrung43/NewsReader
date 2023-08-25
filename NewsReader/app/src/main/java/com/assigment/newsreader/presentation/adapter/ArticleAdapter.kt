package com.assigment.newsreader.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.assigment.newsreader.databinding.ItemArticleBinding
import com.assigment.newsreader.domain.model.Article
import com.bumptech.glide.Glide

class ArticleAdapter(
    val data: List<Article>,
    val context: Context,
    val listener: (Article) -> Unit
) : Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder = ViewHolder(binding)
        binding.root.setOnClickListener {
            listener.invoke(data[holder.adapterPosition])
        }
        return holder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    class ViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(article: Article) {
            Glide.with(binding.imvPhoto.context)
                .load(article.urlToImage)
                .into(binding.imvPhoto)
            binding.tvTitle.text = article.title
            binding.tvPublishedDate.text = article.publishedAt
        }
    }
}