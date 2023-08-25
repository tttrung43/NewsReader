package com.assigment.newsreader.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.assigment.newsreader.databinding.ActivityArticleDetailBinding
import com.assigment.newsreader.domain.model.Article
import com.bumptech.glide.Glide

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding
    private var article: Article? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        getArticle()
        article?.let {
            showContent(it)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun getArticle() {
        article = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getParcelableExtra(ARTICLE, Article::class.java)
        else
            intent.getParcelableExtra(ARTICLE)
    }

    private fun showContent(article: Article) {
        Glide.with(this)
            .load(article.urlToImage)
            .into(binding.imvPhoto)
        binding.tvTitle.text = article.title
        binding.tvAuthor.text = article.author
        binding.tvContent.text = article.content
        binding.tvSource.text = article.url
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {

        const val ARTICLE = "article_item"
        fun launch(article: Article, context: Context) {
            val detailIntent = Intent(context, ArticleDetailActivity::class.java)
                .apply {
                    putExtra(ARTICLE, article)
                }
            context.startActivity(detailIntent)
        }
    }
}