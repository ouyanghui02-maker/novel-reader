package com.novel.model

import org.junit.Assert.*
import org.junit.Test

class BookTest {

    @Test
    fun `create Book with all fields`() {
        val book = Book(
            bookUrl = "https://example.com/book1",
            title = "斗破苍穹",
            author = "天蚕土豆",
            coverUrl = "https://example.com/cover.jpg",
            intro = "异界大陆少年崛起",
            bookSourceUrl = "https://biquge.com",
            bookChapterUrl = "https://biquge.com/book1/chapters",
            latestChapter = "第一千二百三十四章",
            bookInfo = "玄幻小说"
        )
        
        assertEquals("https://example.com/book1", book.bookUrl)
        assertEquals("斗破苍穹", book.title)
        assertEquals("天蚕土豆", book.author)
        assertEquals("https://example.com/cover.jpg", book.coverUrl)
        assertEquals("异界大陆少年崛起", book.intro)
        assertEquals("https://biquge.com", book.bookSourceUrl)
        assertEquals("https://biquge.com/book1/chapters", book.bookChapterUrl)
        assertEquals("第一千二百三十四章", book.latestChapter)
        assertEquals("玄幻小说", book.bookInfo)
    }

    @Test
    fun `chapterList default is empty JSON array`() {
        val book = Book(bookUrl = "test", title = "t", author = "a", coverUrl = "", intro = "", bookSourceUrl = "", bookChapterUrl = "", latestChapter = "", bookInfo = "")
        assertEquals("[]", book.chapterList)
    }

    @Test
    fun `chapterCount default is 0`() {
        val book = Book(bookUrl = "test", title = "t", author = "a", coverUrl = "", intro = "", bookSourceUrl = "", bookChapterUrl = "", latestChapter = "", bookInfo = "")
        assertEquals(0, book.chapterCount)
    }

    @Test
    fun `lastReadTime default is 0`() {
        val book = Book(bookUrl = "test", title = "t", author = "a", coverUrl = "", intro = "", bookSourceUrl = "", bookChapterUrl = "", latestChapter = "", bookInfo = "")
        assertEquals(0L, book.lastReadTime)
    }

    @Test
    fun `data class equality works`() {
        val book1 = Book(bookUrl = "test", title = "t", author = "a", coverUrl = "", intro = "", bookSourceUrl = "", bookChapterUrl = "", latestChapter = "", bookInfo = "")
        val book2 = Book(bookUrl = "test", title = "t", author = "a", coverUrl = "", intro = "", bookSourceUrl = "", bookChapterUrl = "", latestChapter = "", bookInfo = "")
        val book3 = Book(bookUrl = "other", title = "t", author = "a", coverUrl = "", intro = "", bookSourceUrl = "", bookChapterUrl = "", latestChapter = "", bookInfo = "")
        
        assertEquals(book1, book2)
        assertNotEquals(book1, book3)
    }
}