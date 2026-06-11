package com.novel.database.dao

import androidx.room.*
import com.novel.model.ReadingRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface ReadingRecordDao {
    @Query("SELECT * FROM reading_records WHERE bookUrl = :bookUrl ORDER BY readTime DESC")
    fun getReadingRecordsByBook(bookUrl: String): Flow<List<ReadingRecord>>
    
    @Query("SELECT * FROM reading_records ORDER BY readTime DESC LIMIT 20")
    fun getRecentReadingRecords(): Flow<List<ReadingRecord>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReadingRecord(record: ReadingRecord)
    
    @Delete
    suspend fun deleteReadingRecord(record: ReadingRecord)
    
    @Query("DELETE FROM reading_records WHERE bookUrl = :bookUrl")
    suspend fun deleteReadingRecordsByBook(bookUrl: String)
    
    @Query("SELECT SUM(readTime) FROM reading_records WHERE bookUrl = :bookUrl")
    suspend fun getTotalReadingTime(bookUrl: String): Long?
    
    @Query("SELECT SUM(readTime) FROM reading_records")
    suspend fun getTotalReadingTimeAll(): Long?
}