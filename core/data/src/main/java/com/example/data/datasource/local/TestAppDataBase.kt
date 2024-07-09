package com.example.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.BookmarkEntity

@Database(entities = [BookmarkEntity::class], version = 1, exportSchema = false)
abstract class TestAppDataBase() : RoomDatabase() {

    abstract val bookmarkDAO: BookmarkDAO

    companion object {
        private const val db_name = "lezhin_test_db"

        @Volatile
        private var INSTANCE: TestAppDataBase? = null

        fun getDBInstance(context: Context): TestAppDataBase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
            }
        }

        private fun buildDataBase(context: Context): TestAppDataBase {
            return Room.databaseBuilder(
                context = context,
                klass = TestAppDataBase::class.java,
                name = db_name
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
