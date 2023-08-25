package com.assigment.newsreader.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assigment.newsreader.domain.model.Article

@Entity
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) {
    fun toArticle(): Article {
        return Article(
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

