package com.novel.source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novel.database.dao.BookSourceDao
import com.novel.model.BookSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    private val bookSourceDao: BookSourceDao
) : ViewModel() {
    
    val bookSources: Flow<List<BookSource>> = bookSourceDao.getAllBookSources()
    
    fun addSource(source: BookSource) {
        viewModelScope.launch {
            bookSourceDao.insertBookSource(source)
        }
    }
    
    fun deleteSource(source: BookSource) {
        viewModelScope.launch {
            bookSourceDao.deleteBookSource(source)
        }
    }
    
    fun toggleSource(source: BookSource) {
        viewModelScope.launch {
            bookSourceDao.updateBookSourceEnabled(source.bookSourceUrl, !source.enabled)
        }
    }
}