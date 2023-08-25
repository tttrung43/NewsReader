package com.assigment.newsreader.data.remote.dto

import com.assigment.newsreader.data.local.entity.ArticleEntity

data class ArticleDto(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceDto?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) {
    fun toArticleEntity(): ArticleEntity {
        return ArticleEntity(
            author = author,
            content = content,
            description = description,
            publishedAt = publishedAt,
            title = title,
            url = url,
            urlToImage = urlToImage
        )
    }
}