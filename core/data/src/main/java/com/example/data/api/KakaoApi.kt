package com.example.data.api

import com.example.data.model.KakaoImageResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApi {
    @GET("v2/search/image")
    suspend fun getKakaoImages(
        @Header("Authorization") authorization: String = "KakaoAK ",
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ): KakaoImageResponse
}
