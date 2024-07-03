package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.model.KakaoImageResponse
import com.example.model.ImageData
import com.example.model.KakaoImageData
import kotlinx.coroutines.flow.Flow

interface KakaoImageSearchRepository {

    suspend fun getKakaoImages(query: String,sort:String): Flow<PagingData<ImageData>>
}