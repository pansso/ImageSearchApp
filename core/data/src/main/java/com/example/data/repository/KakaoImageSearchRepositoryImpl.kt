package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.api.KakaoApi
import com.example.data.model.KakaoImageResponse
import com.example.data.paging.KakaoPagingSource
import com.example.model.ImageData
import com.example.model.KakaoImageData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class KakaoImageSearchRepositoryImpl @Inject constructor(
    private val kakaoApi: KakaoApi
) : KakaoImageSearchRepository {
    override suspend fun getKakaoImages(query: String,sort:String): Flow<PagingData<ImageData>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { KakaoPagingSource(kakaoApi = kakaoApi, query = query, sort = sort)}
        ).flow
    }
}