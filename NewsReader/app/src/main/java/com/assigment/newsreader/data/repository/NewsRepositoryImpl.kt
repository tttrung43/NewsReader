package com.assigment.newsreader.data.repository

import com.assigment.newsreader.common.Resource
import com.assigment.newsreader.data.local.NewsDatabase
import com.assigment.newsreader.data.remote.NewsService
import com.assigment.newsreader.domain.model.Article
import com.assigment.newsreader.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class NewsRepositoryImpl(
    private val api: NewsService,
    private val db: NewsDatabase
) : NewsRepository {
    override fun fetchNews(): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())
        val articles = db.articleDao.getAllArticles().map { it.toArticle() }
        emit(Resource.Loading(data = articles))

        try {
            val articleResponse = api.fetchNews()
            val articleDtos = articleResponse.articles
            db.articleDao.deleteAllArticles()
            db.articleDao.insertArticles(articleDtos.map { it.toArticleEntity() })

        } catch (ex: IOException) {
            emit(Resource.Error(data = articles, "No network connection."))
        } catch (ex: Exception) {
            emit(Resource.Error(data = articles, "Something went wrong."))
        }

        val newArticles = db.articleDao.getAllArticles()
        emit(Resource.Success(data = newArticles.map { it.toArticle() }))
    }
}