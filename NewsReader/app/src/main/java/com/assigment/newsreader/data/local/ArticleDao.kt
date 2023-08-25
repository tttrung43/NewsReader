package com.assigment.newsreader.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assigment.newsreader.data.local.entity.ArticleEntity
import retrofit2.http.GET

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articleentity")
    suspend fun getAllArticles(): List<ArticleEntity>

    @Query("DELETE FROM articleentity")
    suspend fun deleteAllArticles()
}