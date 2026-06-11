package com.novel.bookshelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novel.database.dao.BookDao
import com.novel.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookshelfViewModel @Inject constructor(
    private val bookDao: BookDao
) : ViewModel() {
    
    val books: Flow<List<Book>> = bookDao.getAllBooks()
    
    fun addBook(book: Book) {
        viewModelScope.launch {
            bookDao.insertBook(book)
        }
    }
    
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            bookDao.deleteBook(book)
        }
    }
    
    fun updateReadProgress(bookUrl: String, chapter: String, progress: Float) {
        viewModelScope.launch {
            bookDao.updateReadProgress(bookUrl, System.currentTimeMillis(), chapter, progress)
        }
    }
}