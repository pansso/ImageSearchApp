package com.example.model


data class KakaoImageData(
    val documents: List<Document?>?,
    val meta: Meta?
) {
    data class Document(
        val collection: String?,
        val datetime: String?,
        val displaySitename: String?,
        val docUrl: String?,
        val height: Int?,
        val imageUrl: String?,
        val thumbnailUrl: String?,
        val width: Int?
    )

    data class Meta(
        val isEnd: Boolean?,
        val pageableCount: Int?,
        val totalCount: Int?
    )
}