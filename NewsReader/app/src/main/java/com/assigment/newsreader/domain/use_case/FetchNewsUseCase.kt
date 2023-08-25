package com.assigment.newsreader.domain.use_case

import com.assigment.newsreader.common.Resource
import com.assigment.newsreader.domain.model.Article
import com.assigment.newsreader.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class FetchNewsUseCase(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<Resource<List<Article>>> {
        return repository.fetchNews()
    }
}