package com.novel.reader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novel.database.dao.BookDao
import com.novel.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    private val bookDao: BookDao
) : ViewModel() {
    
    private val _currentBook = MutableStateFlow<Book?>(null)
    val currentBook: StateFlow<Book?> = _currentBook
    
    private val _currentChapterIndex = MutableStateFlow(0)
    val currentChapterIndex: StateFlow<Int> = _currentChapterIndex
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    fun loadBook(bookUrl: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val book = bookDao.getBookByUrl(bookUrl)
            _currentBook.value = book
            _isLoading.value = false
        }
    }
    
    fun updateReadProgress(chapter: String, progress: Float) {
        viewModelScope.launch {
            _currentBook.value?.let { book ->
                bookDao.updateReadProgress(book.bookUrl, System.currentTimeMillis(), chapter, progress)
            }
        }
    }
    
    fun nextChapter() {
        val currentIndex = _currentChapterIndex.value
        val book = _currentBook.value
        if (book != null && currentIndex < book.chapterList.size - 1) {
            _currentChapterIndex.value = currentIndex + 1
            updateReadProgress(book.chapterList[currentIndex + 1].title, 0f)
        }
    }
    
    fun previousChapter() {
        val currentIndex = _currentChapterIndex.value
        val book = _currentBook.value
        if (book != null && currentIndex > 0) {
            _currentChapterIndex.value = currentIndex - 1
            updateReadProgress(book.chapterList[currentIndex - 1].title, 0f)
        }
    }
}