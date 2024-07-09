package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val imageUrl: String,
    val time : Long = System.currentTimeMillis(),
    val keyword : String
)