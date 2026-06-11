package com.novel.database.dao

import androidx.room.*
import com.novel.model.BookSource
import kotlinx.coroutines.flow.Flow

@Dao
interface BookSourceDao {
    @Query("SELECT * FROM book_sources ORDER BY bookSourceName")
    fun getAllBookSources(): Flow<List<BookSource>>
    
    @Query("SELECT * FROM book_sources WHERE enabled = 1 ORDER BY bookSourceName")
    fun getEnabledBookSources(): Flow<List<BookSource>>
    
    @Query("SELECT * FROM book_sources WHERE bookSourceUrl = :url")
    suspend fun getBookSourceByUrl(url: String): BookSource?
    
    @Query("SELECT * FROM book_sources WHERE bookSourceName LIKE '%' || :keyword || '%' OR bookSourceUrl LIKE '%' || :keyword || '%'")
    fun searchBookSources(keyword: String): Flow<List<BookSource>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookSource(bookSource: BookSource)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookSources(bookSources: List<BookSource>)
    
    @Update
    suspend fun updateBookSource(bookSource: BookSource)
    
    @Delete
    suspend fun deleteBookSource(bookSource: BookSource)
    
    @Query("DELETE FROM book_sources WHERE bookSourceUrl = :url")
    suspend fun deleteBookSourceByUrl(url: String)
    
    @Query("UPDATE book_sources SET enabled = :enabled WHERE bookSourceUrl = :url")
    suspend fun updateBookSourceEnabled(url: String, enabled: Boolean)
}