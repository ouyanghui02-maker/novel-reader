package com.novel.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.novel.database.dao.*
import com.novel.model.*

@Database(
    entities = [
        Book::class,
        BookSource::class,
        SearchHistory::class,
        BookGroup::class,
        ReadingRecord::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NovelDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun bookSourceDao(): BookSourceDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun bookGroupDao(): BookGroupDao
    abstract fun readingRecordDao(): ReadingRecordDao
}