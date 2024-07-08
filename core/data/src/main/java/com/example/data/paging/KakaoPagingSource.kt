package com.example.data.paging

import android.net.http.HttpException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.KakaoApi
import com.example.data.model.KakaoImageResponse
import com.example.data.model.mapper.toData
import com.example.model.ImageData
import com.example.model.KakaoImageData
import timber.log.Timber

private const val PAGE = 1

class KakaoPagingSource(
    private val kakaoApi: KakaoApi,
    private val query: String,
    private val sort: String
) : PagingSource<Int, ImageData>() {

    override fun getRefreshKey(state: PagingState<Int, ImageData>): Int? {
        return state.anchorPosition?.let { it ->
            state.closestPageToPosition(anchorPosition = it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition = it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageData> {
        val currentPage = params.key ?: PAGE
        return try {
            val response = kakaoApi.getKakaoImages(query = query, page = currentPage, sort = sort)
            val documents = response.documents?.filterNotNull().orEmpty()
            val result = documents.map { it.toData() }
            LoadResult.Page(
                data = result,
                prevKey = (currentPage - 1).takeIf { it > 0 },
                nextKey = (currentPage + 1).takeIf { documents.isNotEmpty() }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
//            Timber.e( "sjh PagingSource: Error loading data ${e.localizedMessage}")
        }
    }
}