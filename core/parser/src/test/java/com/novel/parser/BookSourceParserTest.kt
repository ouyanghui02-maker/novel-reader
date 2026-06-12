package com.novel.parser

import com.novel.model.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class BookSourceParserTest {

    private lateinit var parser: BookSourceParser

    @Before
    fun setup() {
        parser = BookSourceParser()
    }

    @Test
    fun `parseSearchResult extracts books from HTML`() {
        val html = """
            <div class="book-list">
                <div class="book-item">
                    <a class="book-name" href="https://example.com/book/1">斗破苍穹</a>
                    <span class="book-author">天蚕土豆</span>
                    <img class="book-cover" src="https://example.com/cover/1.jpg">
                    <span class="book-intro">异界大陆</span>
                    <span class="book-last-chapter">第一千章</span>
                </div>
            </div>
        """.trimIndent()
        
        val rule = SearchRule(
            bookList = "div.book-item",
            name = "a.book-name",
            author = "span.book-author",
            coverUrl = "img.book-cover",
            bookUrl = "a.book-name",
            intro = "span.book-intro",
            lastChapter = "span.book-last-chapter"
        )
        
        val books = parser.parseSearchResult(html, rule)
        
        assertEquals(1, books.size)
        assertEquals("斗破苍穹", books[0].title)
        assertEquals("天蚕土豆", books[0].author)
        assertEquals("异界大陆", books[0].intro)
        assertEquals("第一千章", books[0].latestChapter)
    }

    @Test
    fun `parseSearchResult returns empty list for empty HTML`() {
        val html = ""
        val rule = SearchRule(bookList = "div.book-item", name = "a")
        
        val books = parser.parseSearchResult(html, rule)
        
        assertTrue(books.isEmpty())
    }

    @Test
    fun `parseBookInfo extracts book details`() {
        val html = """
            <div class="book-info">
                <h1 class="book-title">凡人修仙传</h1>
                <span class="book-author">忘语</span>
                <img class="book-cover" src="https://example.com/cover/2.jpg">
                <div class="book-intro">凡人流修仙</div>
                <span class="book-last-chapter">第二千章</span>
            </div>
        """.trimIndent()
        
        val rule = BookInfoRule(
            name = "h1.book-title",
            author = "span.book-author",
            coverUrl = "img.book-cover",
            intro = "div.book-intro",
            lastChapter = "span.book-last-chapter"
        )
        
        val book = parser.parseBookInfo(html, rule)
        
        assertEquals("凡人修仙传", book.title)
        assertEquals("忘语", book.author)
        assertEquals("凡人流修仙", book.intro)
        assertEquals("第二千章", book.latestChapter)
    }

    @Test
    fun `parseChapterList extracts chapters`() {
        val html = """
            <div class="chapter-list">
                <a href="https://example.com/chapter/1">第一章</a>
                <a href="https://example.com/chapter/2">第二章</a>
                <a href="https://example.com/chapter/3">第三章</a>
            </div>
        """.trimIndent()
        
        val rule = TocRule(chapterList = "div.chapter-list a")
        
        val chapters = parser.parseChapterList(html, rule)
        
        assertEquals(3, chapters.size)
        assertEquals("第一章", chapters[0].title)
        assertEquals("第二章", chapters[1].title)
        assertEquals("第三章", chapters[2].title)
    }

    @Test
    fun `parseChapterContent extracts text`() {
        val html = """
            <div class="content">
                <p>这是正文内容。</p>
                <p>第二段正文。</p>
            </div>
        """.trimIndent()
        
        val rule = ContentRule(content = "div.content")
        
        val content = parser.parseChapterContent(html, rule)
        
        assertTrue(content.contains("这是正文内容"))
        assertTrue(content.contains("第二段正文"))
    }

    @Test
    fun `parseChapterContent applies replace rules`() {
        val html = "<div class=\"content\">广告文字 正文内容</div>"
        
        val rule = ContentRule(
            content = "div.content",
            replaceRegex = listOf("广告文字,移除后")
        )
        
        val content = parser.parseChapterContent(html, rule)
        
        assertFalse(content.contains("广告文字"))
        assertTrue(content.contains("正文内容"))
    }
}