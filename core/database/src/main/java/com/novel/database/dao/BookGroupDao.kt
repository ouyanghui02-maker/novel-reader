package com.novel.database.dao

import androidx.room.*
import com.novel.model.BookGroup
import kotlinx.coroutines.flow.Flow

@Dao
interface BookGroupDao {
    @Query("SELECT * FROM book_groups ORDER BY sort")
    fun getAllBookGroups(): Flow<List<BookGroup>>
    
    @Query("SELECT * FROM book_groups WHERE id = :id")
    suspend fun getBookGroupById(id: Long): BookGroup?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookGroup(bookGroup: BookGroup)
    
    @Update
    suspend fun updateBookGroup(bookGroup: BookGroup)
    
    @Delete
    suspend fun deleteBookGroup(bookGroup: BookGroup)
    
    @Query("DELETE FROM book_groups WHERE id = :id")
    suspend fun deleteBookGroupById(id: Long)
}