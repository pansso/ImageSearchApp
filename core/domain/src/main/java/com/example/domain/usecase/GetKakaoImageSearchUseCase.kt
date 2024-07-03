package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.data.repository.KakaoImageSearchRepository
import com.example.model.ImageData
import com.example.model.KakaoImageData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKakaoImageSearchUseCase @Inject constructor(
    private val kakaoImageSearchRepository: KakaoImageSearchRepository
){
    suspend operator fun invoke(query:String, sort:String) : Flow<PagingData<ImageData>> {
        return kakaoImageSearchRepository.getKakaoImages(query,sort)
    }
}