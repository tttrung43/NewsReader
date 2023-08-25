package com.assigment.newsreader.domain.repository

import com.assigment.newsreader.common.Resource
import com.assigment.newsreader.data.remote.dto.ArticleDto
import com.assigment.newsreader.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun fetchNews(): Flow<Resource<List<Article>>>
}