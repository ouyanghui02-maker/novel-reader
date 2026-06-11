package com.novel.database.dao

import androidx.room.*
import com.novel.model.SearchHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY searchTime DESC LIMIT 20")
    fun getRecentSearchHistory(): Flow<List<SearchHistory>>
    
    @Query("SELECT * FROM search_history WHERE keyword LIKE '%' || :keyword || '%' ORDER BY searchTime DESC")
    fun searchHistory(keyword: String): Flow<List<SearchHistory>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistory: SearchHistory)
    
    @Delete
    suspend fun deleteSearchHistory(searchHistory: SearchHistory)
    
    @Query("DELETE FROM search_history")
    suspend fun clearAllSearchHistory()
}