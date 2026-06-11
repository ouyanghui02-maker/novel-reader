package com.novel.database

import com.novel.model.Book
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BookDaoTest {
    
    private lateinit var fakeBookDao: FakeBookDao
    
    @Before
    fun setup() {
        fakeBookDao = FakeBookDao()
    }
    
    @Test
    fun `insertBook should add book to database`() = runTest {
        val book = createTestBook()
        
        fakeBookDao.insertBook(book)
        
        val books = fakeBookDao.getAllBooks().first()
        assertEquals(1, books.size)
        assertEquals(book.bookUrl, books[0].bookUrl)
    }
    
    @Test
    fun `getAllBooks should return all books`() = runTest {
        val book1 = createTestBook(bookUrl = "url1", title = "Book 1")
        val book2 = createTestBook(bookUrl = "url2", title = "Book 2")
        
        fakeBookDao.insertBook(book1)
        fakeBookDao.insertBook(book2)
        
        val books = fakeBookDao.getAllBooks().first()
        assertEquals(2, books.size)
    }
    
    @Test
    fun `getBookByUrl should return correct book`() = runTest {
        val book = createTestBook()
        fakeBookDao.insertBook(book)
        
        val result = fakeBookDao.getBookByUrl(book.bookUrl)
        assertNotNull(result)
        assertEquals(book.title, result?.title)
    }
    
    @Test
    fun `deleteBook should remove book from database`() = runTest {
        val book = createTestBook()
        fakeBookDao.insertBook(book)
        
        fakeBookDao.deleteBook(book)
        
        val books = fakeBookDao.getAllBooks().first()
        assertTrue(books.isEmpty())
    }
    
    @Test
    fun `updateBook should update book data`() = runTest {
        val book = createTestBook()
        fakeBookDao.insertBook(book)
        
        val updatedBook = book.copy(title = "Updated Title")
        fakeBookDao.updateBook(updatedBook)
        
        val result = fakeBookDao.getBookByUrl(book.bookUrl)
        assertEquals("Updated Title", result?.title)
    }
    
    @Test
    fun `searchBooks should find books by title`() = runTest {
        val book1 = createTestBook(title = "斗破苍穹")
        val book2 = createTestBook(bookUrl = "url2", title = "凡人修仙传")
        
        fakeBookDao.insertBook(book1)
        fakeBookDao.insertBook(book2)
        
        val results = fakeBookDao.searchBooks("斗破").first()
        assertEquals(1, results.size)
        assertEquals("斗破苍穹", results[0].title)
    }
    
    @Test
    fun `updateReadProgress should update book read data`() = runTest {
        val book = createTestBook()
        fakeBookDao.insertBook(book)
        
        fakeBookDao.updateReadProgress(
            bookUrl = book.bookUrl,
            time = System.currentTimeMillis(),
            chapter = "第一章",
            progress = 0.5f
        )
        
        val result = fakeBookDao.getBookByUrl(book.bookUrl)
        assertEquals("第一章", result?.lastReadChapter)
        assertEquals(0.5f, result?.lastReadProgress)
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
    
    fun getAllBooks() = kotlinx.coroutines.flow.flow {
        emit(books.toList())
    }
    
    suspend fun getBookByUrl(bookUrl: String) = books.find { it.bookUrl == bookUrl }
    
    fun searchBooks(keyword: String) = kotlinx.coroutines.flow.flow {
        emit(books.filter { 
            it.title.contains(keyword, ignoreCase = true) || 
            it.author.contains(keyword, ignoreCase = true) 
        })
    }
    
    suspend fun insertBook(book: Book) {
        books.add(book)
    }
    
    suspend fun updateBook(book: Book) {
        val index = books.indexOfFirst { it.bookUrl == book.bookUrl }
        if (index != -1) {
            books[index] = book
        }
    }
    
    suspend fun deleteBook(book: Book) {
        books.remove(book)
    }
    
    suspend fun updateReadProgress(bookUrl: String, time: Long, chapter: String, progress: Float) {
        val index = books.indexOfFirst { it.bookUrl == bookUrl }
        if (index != -1) {
            books[index] = books[index].copy(
                lastReadTime = time,
                lastReadChapter = chapter,
                lastReadProgress = progress
            )
        }
    }
}