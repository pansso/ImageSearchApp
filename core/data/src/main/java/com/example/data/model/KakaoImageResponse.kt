package com.example.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoImageResponse(
    @SerialName("documents")
    val documents: List<Document?>?,
    @SerialName("meta")
    val meta: Meta?
) {
    @Serializable
    data class Document(
        @SerialName("collection")
        val collection: String?,
        @SerialName("datetime")
        val datetime: String?,
        @SerialName("display_sitename")
        val displaySitename: String?,
        @SerialName("doc_url")
        val docUrl: String?,
        @SerialName("height")
        val height: Int?,
        @SerialName("image_url")
        val imageUrl: String?,
        @SerialName("thumbnail_url")
        val thumbnailUrl: String?,
        @SerialName("width")
        val width: Int?
    )

    @Serializable
    data class Meta(
        @SerialName("is_end")
        val isEnd: Boolean?,
        @SerialName("pageable_count")
        val pageableCount: Int?,
        @SerialName("total_count")
        val totalCount: Int?
    )
}