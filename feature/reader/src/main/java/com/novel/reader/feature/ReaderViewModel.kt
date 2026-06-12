package com.novel.reader.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novel.database.dao.BookDao
import com.novel.model.Book
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(
    private val bookDao: BookDao
) : ViewModel() {
    
    private val gson = Gson()
    
    private val _currentBook = MutableStateFlow<Book?>(null)
    val currentBook: StateFlow<Book?> = _currentBook
    
    private val _currentChapterIndex = MutableStateFlow(0)
    val currentChapterIndex: StateFlow<Int> = _currentChapterIndex
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private fun parseChapterTitles(json: String): List<String> {
        if (json.isBlank() || json == "[]") return emptyList()
        val type = object : TypeToken<List<Map<String, String>>>() {}.type
        val chapters: List<Map<String, String>> = gson.fromJson(json, type)
        return chapters.map { it["title"] ?: "" }
    }
    
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
        if (book != null) {
            val chapters = parseChapterTitles(book.chapterList)
            if (chapters.isNotEmpty() && currentIndex < chapters.size - 1) {
                _currentChapterIndex.value = currentIndex + 1
                updateReadProgress(chapters[currentIndex + 1], 0f)
            }
        }
    }
    
    fun previousChapter() {
        val currentIndex = _currentChapterIndex.value
        val book = _currentBook.value
        if (book != null) {
            val chapters = parseChapterTitles(book.chapterList)
            if (currentIndex > 0) {
                _currentChapterIndex.value = currentIndex - 1
                updateReadProgress(chapters[currentIndex - 1], 0f)
            }
        }
    }
}