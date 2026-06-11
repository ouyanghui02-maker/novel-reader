package com.novel.database.dao

import androidx.room.*
import com.novel.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY lastReadTime DESC")
    fun getAllBooks(): Flow<List<Book>>
    
    @Query("SELECT * FROM books WHERE bookUrl = :bookUrl")
    suspend fun getBookByUrl(bookUrl: String): Book?
    
    @Query("SELECT * FROM books WHERE title LIKE '%' || :keyword || '%' OR author LIKE '%' || :keyword || '%'")
    fun searchBooks(keyword: String): Flow<List<Book>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)
    
    @Update
    suspend fun updateBook(book: Book)
    
    @Delete
    suspend fun deleteBook(book: Book)
    
    @Query("DELETE FROM books WHERE bookUrl = :bookUrl")
    suspend fun deleteBookByUrl(bookUrl: String)
    
    @Query("UPDATE books SET lastReadTime = :time, lastReadChapter = :chapter, lastReadProgress = :progress WHERE bookUrl = :bookUrl")
    suspend fun updateReadProgress(bookUrl: String, time: Long, chapter: String, progress: Float)
}