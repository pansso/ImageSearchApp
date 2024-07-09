package com.example.data.datasource

import com.example.data.repository.BookmarkRepository
import com.example.data.repository.BookmarkRepositoryImpl
import com.example.data.repository.KakaoImageSearchRepository
import com.example.data.repository.KakaoImageSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    abstract fun bindsKakaoImageSearchRepository(
        repositoryImpl: KakaoImageSearchRepositoryImpl
    ) : KakaoImageSearchRepository

    @Binds
    abstract fun bindBookmarkRepository(
        repositoryImpl: BookmarkRepositoryImpl
    ) : BookmarkRepository
}