package com.example.model


data class BookmarkData(
    val imageUrl: String,
    val time : Long = System.currentTimeMillis(),
    val keyword : String
)