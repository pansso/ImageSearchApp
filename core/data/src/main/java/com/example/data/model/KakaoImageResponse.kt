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
        val display_sitename: String?,
        @SerialName("doc_url")
        val doc_url: String?,
        @SerialName("height")
        val height: Int?,
        @SerialName("image_url")
        val image_url: String?,
        @SerialName("thumbnail_url")
        val thumbnail_url: String?,
        @SerialName("width")
        val width: Int?
    )

    @Serializable
    data class Meta(
        @SerialName("is_end")
        val is_end: Boolean?,
        @SerialName("pageable_count")
        val pageable_count: Int?,
        @SerialName("total_count")
        val total_count: Int?
    )
}