package com.example.data.repository

import com.example.data.model.BookmarkEntity
import com.example.model.BookmarkData
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun insertBookmark(bookmark: BookmarkData)

    fun getAllBookmarks() : Flow<List<BookmarkData>>

    fun deleteBookmark(bookmark: BookmarkData)

    fun deleteBookmarkList(bookmark : List<BookmarkData>)

    fun deleteBookmarkUrl(imageUrl:String)

    fun deleteBookmarkUrlList(imageUrlList: List<String>)

//    suspend fun getBookmark(url:String) : BookmarkData
}