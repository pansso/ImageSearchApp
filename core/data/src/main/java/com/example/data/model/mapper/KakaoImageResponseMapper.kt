package com.example.data.model.mapper

import com.example.data.model.KakaoImageResponse
import com.example.model.ImageData
import com.example.model.KakaoImageData


internal fun KakaoImageResponse.Document.toData() : ImageData =
    ImageData(
        collection = collection,
        thumbnailUrl = thumbnailUrl,
        imageUrl = imageUrl,
        width =  width,
        height = height,
        displaySitename = displaySitename,
        docUrl = docUrl,
        datetime = datetime
    )
