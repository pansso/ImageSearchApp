package com.example.data.repository

import com.example.data.datasource.local.BookmarkDAO
import com.example.data.model.mapper.toData
import com.example.data.model.mapper.toDomain
import com.example.model.BookmarkData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDAO: BookmarkDAO
) : BookmarkRepository {
    override suspend fun insertBookmark(bookmark: BookmarkData) {
        bookmarkDAO.insertBookmark(bookmark.toData())
    }

//    override suspend fun getBookmark(url: String): BookmarkData {
//        return bookmarkDAO.getBookmark(url).toDomain()
//    }

    override fun getAllBookmarks(): Flow<List<BookmarkData>> {
        return bookmarkDAO.getAllBookmarks().map { entities -> entities.map { it.toDomain() } }
    }

    override fun deleteBookmark(bookmark: BookmarkData) {
        return bookmarkDAO.deleteBookmark(bookmark = bookmark.toData())
    }

    override fun deleteBookmarkList(bookmark: List<BookmarkData>) {
        return bookmarkDAO.deleteBookmarkList(bookmarks = bookmark.map { it.toData() })
    }

    override fun deleteBookmarkUrl(imageUrl: String) {
        return bookmarkDAO.deleteBookmarkUrl(imageUrl)
    }

    override fun deleteBookmarkUrlList(imageUrlList: List<String>) {
        return bookmarkDAO.deleteBookmarkUrlList(imageUrlList)
    }

}
