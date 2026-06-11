package com.novel.bookshelf

import com.novel.database.dao.BookDao
import com.novel.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookshelfViewModelTest {
    
    private lateinit var viewModel: BookshelfViewModel
    private lateinit var fakeBookDao: FakeBookDao
    private val testDispatcher = UnconfinedTestDispatcher()
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeBookDao = FakeBookDao()
        viewModel = BookshelfViewModel(fakeBookDao)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun `addBook should add book to bookshelf`() = runTest {
        val book = createTestBook()
        
        viewModel.addBook(book)
        
        val books = viewModel.books.first()
        assertEquals(1, books.size)
        assertEquals(book.bookUrl, books[0].bookUrl)
    }
    
    @Test
    fun `deleteBook should remove book from bookshelf`() = runTest {
        val book = createTestBook()
        viewModel.addBook(book)
        
        viewModel.deleteBook(book)
        
        val books = viewModel.books.first()
        assertTrue(books.isEmpty())
    }
    
    @Test
    fun `updateReadProgress should update book read data`() = runTest {
        val book = createTestBook()
        viewModel.addBook(book)
        
        viewModel.updateReadProgress(book.bookUrl, "第一章", 0.5f)
        
        val books = viewModel.books.first()
        assertEquals(1, books.size)
        assertEquals("第一章", books[0].lastReadChapter)
        assertEquals(0.5f, books[0].lastReadProgress)
    }
    
    @Test
    fun `books should emit empty list initially`() = runTest {
        val books = viewModel.books.first()
        assertTrue(books.isEmpty())
    }
    
    @Test
    fun `addBook should update books flow`() = runTest {
        val book1 = createTestBook(bookUrl = "url1", title = "Book 1")
        val book2 = createTestBook(bookUrl = "url2", title = "Book 2")
        
        viewModel.addBook(book1)
        val booksAfterFirst = viewModel.books.first()
        assertEquals(1, booksAfterFirst.size)
        
        viewModel.addBook(book2)
        val booksAfterSecond = viewModel.books.first()
        assertEquals(2, booksAfterSecond.size)
    }
    
    private fun createTestBook(
        bookUrl: String = "https://example.com/book",
        title: String = "Test Book",
        author: String = "Test Author"
    ) = Book(
        bookUrl = bookUrl,
        title = title,
        author = author,
        coverUrl = "https://example.com/cover.jpg",
        intro = "Test intro",
        bookSourceUrl = "https://example.com/source",
        bookChapterUrl = "https://example.com/chapter",
        latestChapter = "Latest Chapter",
        bookInfo = ""
    )
}

class FakeBookDao {
    private val books = mutableListOf<Book>()
    
    val allBooks = kotlinx.coroutines.flow.MutableStateFlow<List<Book>>(emptyList())
    
    fun getAllBooks() = allBooks
    
    suspend fun getBookByUrl(bookUrl: String) = books.find { it.bookUrl == bookUrl }
    
    fun searchBooks(keyword: String) = kotlinx.coroutines.flow.flow {
        emit(books.filter { 
            it.title.contains(keyword, ignoreCase = true) || 
            it.author.contains(keyword, ignoreCase = true) 
        })
    }
    
    suspend fun insertBook(book: Book) {
        books.add(book)
        allBooks.value = books.toList()
    }
    
    suspend fun updateBook(book: Book) {
        val index = books.indexOfFirst { it.bookUrl == book.bookUrl }
        if (index != -1) {
            books[index] = book
            allBooks.value = books.toList()
        }
    }
    
    suspend fun deleteBook(book: Book) {
        books.remove(book)
        allBooks.value = books.toList()
    }
    
    suspend fun updateReadProgress(bookUrl: String, time: Long, chapter: String, progress: Float) {
        val index = books.indexOfFirst { it.bookUrl == bookUrl }
        if (index != -1) {
            books[index] = books[index].copy(
                lastReadTime = time,
                lastReadChapter = chapter,
                lastReadProgress = progress
            )
            allBooks.value = books.toList()
        }
    }
}