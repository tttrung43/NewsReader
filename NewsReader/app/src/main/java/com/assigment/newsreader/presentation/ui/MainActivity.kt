package com.assigment.newsreader.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.assigment.newsreader.databinding.ActivityMainBinding
import com.assigment.newsreader.domain.model.Article
import com.assigment.newsreader.presentation.viewmodel.ArticleViewModel
import com.assigment.newsreader.presentation.adapter.ArticleAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tvTitle)
        loadNews()
    }

    private fun loadNews() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                articleViewModel.state.collect {
                    showLoadingContent(it.isLoading)
                    showArticles(it.articles)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                articleViewModel.eventFlow.collectLatest {
                    if (it is ArticleViewModel.UIEvent.ShowErrorMessage) {
                        Snackbar.make(binding.root, it.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        articleViewModel.fetchNews()
    }

    private fun showLoadingContent(isLoading: Boolean) {
        if (isLoading) {
            binding.contentLoadingBar.show()
        } else {
            binding.contentLoadingBar.hide()
        }
    }

    private fun showArticles(data: List<Article>) {
        val articleAdapter = ArticleAdapter(data, this) { article ->
            ArticleDetailActivity.launch(article, this@MainActivity)
        }
        with(binding.rvArticles) {
            setHasFixedSize(true)
            isMotionEventSplittingEnabled = false
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = articleAdapter
        }
    }
}