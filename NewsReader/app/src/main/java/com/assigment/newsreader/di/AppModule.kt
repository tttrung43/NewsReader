package com.assigment.newsreader.di

import android.content.Context
import androidx.room.Room
import com.assigment.newsreader.BuildConfig
import com.assigment.newsreader.common.Constants
import com.assigment.newsreader.data.local.NewsDatabase
import com.assigment.newsreader.data.remote.NewsService
import com.assigment.newsreader.data.repository.NewsRepositoryImpl
import com.assigment.newsreader.domain.repository.NewsRepository
import com.assigment.newsreader.domain.use_case.FetchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room
            .databaseBuilder(context, NewsDatabase::class.java, Constants.DB_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsService(): NewsService {
        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        if (BuildConfig.DEBUG) {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logInterceptor)
                .build()
            builder.client(okHttpClient)
        }

        return builder.build()
            .create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(api: NewsService, db: NewsDatabase): NewsRepository {
        return NewsRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideFetchNewsUseCase(newsRepository: NewsRepository): FetchNewsUseCase {
        return FetchNewsUseCase(newsRepository)
    }
}