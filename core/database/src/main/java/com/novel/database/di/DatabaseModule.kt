package com.novel.database.di

import android.content.Context
import androidx.room.Room
import com.novel.database.DatabaseMigrations
import com.novel.database.NovelDatabase
import com.novel.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideNovelDatabase(@ApplicationContext context: Context): NovelDatabase {
        return Room.databaseBuilder(
            context,
            NovelDatabase::class.java,
            "novel_reader.db"
        )
            .addMigrations(
                DatabaseMigrations.MIGRATION_1_2,
                DatabaseMigrations.MIGRATION_2_3,
                DatabaseMigrations.MIGRATION_3_4
            )
            .build()
    }
    
    @Provides
    fun provideBookDao(database: NovelDatabase): BookDao {
        return database.bookDao()
    }
    
    @Provides
    fun provideBookSourceDao(database: NovelDatabase): BookSourceDao {
        return database.bookSourceDao()
    }
    
    @Provides
    fun provideSearchHistoryDao(database: NovelDatabase): SearchHistoryDao {
        return database.searchHistoryDao()
    }
    
    @Provides
    fun provideBookGroupDao(database: NovelDatabase): BookGroupDao {
        return database.bookGroupDao()
    }
    
    @Provides
    fun provideReadingRecordDao(database: NovelDatabase): ReadingRecordDao {
        return database.readingRecordDao()
    }
}