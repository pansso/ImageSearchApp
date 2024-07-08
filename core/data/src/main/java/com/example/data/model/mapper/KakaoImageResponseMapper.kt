package com.example.data.model.mapper

import com.example.data.model.KakaoImageResponse
import com.example.model.ImageData
import com.example.model.KakaoImageData


internal fun KakaoImageResponse.Document.toData(): ImageData =
    ImageData(
        collection = collection,
        thumbnailUrl = thumbnail_url,
        imageUrl = image_url,
        width = width,
        height = height,
        displaySitename = display_sitename,
        docUrl = doc_url,
        datetime = datetime
    )
