package com.assigment.newsreader.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assigment.newsreader.common.Constants
import com.assigment.newsreader.data.local.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = Constants.DB_VERSION
)
abstract class NewsDatabase : RoomDatabase() {

    abstract val articleDao: ArticleDao

}