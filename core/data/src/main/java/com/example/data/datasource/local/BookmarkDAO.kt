package com.example.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.BookmarkEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BookmarkDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(bookmarkEntity: BookmarkEntity)

    @Query("SELECT * FROM bookmarks")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("DELETE FROM bookmarks WHERE imageUrl = :imageUrl")
    fun deleteBookmarkUrl(imageUrl: String)

    @Query("DELETE FROM bookmarks WHERE imageUrl IN (:imageUrlList)")
    fun deleteBookmarkUrlList(imageUrlList: List<String>)
    @Delete
    fun deleteBookmark(bookmark: BookmarkEntity)

    @Delete
    fun deleteBookmarkList(bookmarks: List<BookmarkEntity>)

}