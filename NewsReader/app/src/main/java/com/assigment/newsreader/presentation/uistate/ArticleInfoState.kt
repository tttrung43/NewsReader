package com.assigment.newsreader.presentation.uistate

import com.assigment.newsreader.domain.model.Article

data class ArticleInfoState(
    var articles: List<Article> = emptyList(),
    var isLoading: Boolean = false
)
