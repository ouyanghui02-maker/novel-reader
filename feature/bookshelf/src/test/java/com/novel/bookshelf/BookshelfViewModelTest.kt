package com.novel.bookshelf

import com.novel.database.dao.BookDao
import com.novel.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
    fun `initial books list is empty`() = runTest {
        val books = viewModel.books.first()
        assertTrue(books.isEmpty())
    }

    @Test
    fun `addBook updates books list`() = runTest {
        val book = createTestBook()
        
        viewModel.addBook(book)
        
        val books = viewModel.books.first()
        assertEquals(1, books.size)
        assertEquals(book.bookUrl, books[0].bookUrl)
    }

    @Test
    fun `deleteBook removes book from list`() = runTest {
        val book = createTestBook()
        viewModel.addBook(book)
        
        viewModel.deleteBook(book)
        
        val books = viewModel.books.first()
        assertTrue(books.isEmpty())
    }

    @Test
    fun `deleteBook does not crash for non-existent book`() = runTest {
        val book = createTestBook()
        viewModel.deleteBook(book)
        
        val books = viewModel.books.first()
        assertTrue(books.isEmpty())
    }

    @Test
    fun `updateReadProgress updates book data`() = runTest {
        val book = createTestBook()
        viewModel.addBook(book)
        
        viewModel.updateReadProgress(book.bookUrl, "第一章", 0.5f)
        
        val books = viewModel.books.first()
        assertEquals(1, books.size)
        assertEquals("第一章", books[0].lastReadChapter)
        assertEquals(0.5f, books[0].lastReadProgress)
    }

    @Test
    fun `multiple books can be added`() = runTest {
        val book1 = createTestBook(bookUrl = "url1", title = "Book 1")
        val book2 = createTestBook(bookUrl = "url2", title = "Book 2")
        
        viewModel.addBook(book1)
        viewModel.addBook(book2)
        
        val books = viewModel.books.first()
        assertEquals(2, books.size)
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
        bookChapterUrl = "https://example.com/chapters",
        latestChapter = "Latest Chapter",
        bookInfo = ""
    )
}

class FakeBookDao : BookDao {
    private val books = mutableListOf<Book>()
    val allBooks = MutableStateFlow<List<Book>>(emptyList())

    fun getAllBooks(): Flow<List<Book>> = allBooks

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

    override suspend fun deleteBookByUrl(bookUrl: String) {
        books.removeAll { it.bookUrl == bookUrl }
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