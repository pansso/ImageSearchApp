package com.example.data.datasource.local

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): TestAppDataBase{
        return TestAppDataBase.getDBInstance(appContext)
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(db: TestAppDataBase) : BookmarkDAO {
        return db.bookmarkDAO
    }

}