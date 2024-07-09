package com.example.domain.usecase

import com.example.data.repository.BookmarkRepository
import com.example.model.BookmarkData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    suspend fun insertBookmark(bookmarkData: BookmarkData) {
        return bookmarkRepository.insertBookmark(bookmarkData)
    }

    fun getAllBookmarks(): Flow<List<BookmarkData>> {
        return bookmarkRepository.getAllBookmarks()
    }

    fun deleteBookmark(bookmarkData: BookmarkData){
        return bookmarkRepository.deleteBookmark(bookmarkData)
    }

    fun deleteBookmarkList(bookmarkList : List<BookmarkData>){
        return bookmarkRepository.deleteBookmarkList(bookmarkList)
    }

    fun deleteBookmarkUrl(imageUrl:String){
        return bookmarkRepository.deleteBookmarkUrl(imageUrl)
    }

    fun deleteBookmarkUrlList(imageUrlList: List<String>){
        return bookmarkRepository.deleteBookmarkUrlList(imageUrlList)
    }

}