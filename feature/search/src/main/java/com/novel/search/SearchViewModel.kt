package com.novel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novel.database.dao.BookDao
import com.novel.database.dao.BookSourceDao
import com.novel.database.dao.SearchHistoryDao
import com.novel.model.Book
import com.novel.model.BookSource
import com.novel.model.SearchHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val bookDao: BookDao,
    private val bookSourceDao: BookSourceDao,
    private val searchHistoryDao: SearchHistoryDao
) : ViewModel() {
    
    private val _searchResults = MutableStateFlow<List<Book>>(emptyList())
    val searchResults: StateFlow<List<Book>> = _searchResults
    
    val searchHistory: Flow<List<SearchHistory>> = searchHistoryDao.getRecentSearchHistory()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    fun searchBooks(keyword: String) {
        if (keyword.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        
        viewModelScope.launch {
            _isLoading.value = true
            
            // 保存搜索历史
            searchHistoryDao.insertSearchHistory(
                SearchHistory(keyword = keyword)
            )
            
            // TODO: Implement multi-source search
            // For now, just search local books
            val localResults = bookDao.searchBooks(keyword)
            localResults.collect { books ->
                _searchResults.value = books
                _isLoading.value = false
            }
        }
    }
    
    fun clearSearchHistory() {
        viewModelScope.launch {
            searchHistoryDao.clearAllSearchHistory()
        }
    }
}