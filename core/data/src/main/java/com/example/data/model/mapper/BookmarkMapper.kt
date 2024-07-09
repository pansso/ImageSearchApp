package com.example.data.model.mapper

import com.example.data.model.BookmarkEntity
import com.example.model.BookmarkData

fun BookmarkEntity.toDomain() = BookmarkData(
        imageUrl = imageUrl,
        time = time,
        keyword = keyword
    )


fun BookmarkData.toData() =  BookmarkEntity(
        imageUrl = imageUrl,
        time = time,
        keyword = keyword
    )