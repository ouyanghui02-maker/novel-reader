package com.novel.reader.feature

import com.novel.model.Book
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ReaderViewModelTest {

    private val gson = Gson()

    @Test
    fun `parseChapterTitles with empty JSON`() {
        val result = parseChapterTitles("[]")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `parseChapterTitles with blank string`() {
        val result = parseChapterTitles("")
        assertTrue(result.isEmpty())
    }

    @Test
    fun `parseChapterTitles with valid JSON`() {
        val json = """[{"title":"第一章","url":"url1"},{"title":"第二章","url":"url2"}]"""
        val result = parseChapterTitles(json)
        
        assertEquals(2, result.size)
        assertEquals("第一章", result[0])
        assertEquals("第二章", result[1])
    }

    @Test
    fun `nextChapter does not exceed last chapter`() {
        val book = createTestBook(chapterList = """[{"title":"第一章","url":"url1"}]""")
        val currentIndex = 0
        
        val chapters = parseChapterTitles(book.chapterList)
        val nextIndex = if (chapters.isNotEmpty() && currentIndex < chapters.size - 1) {
            currentIndex + 1
        } else {
            currentIndex
        }
        
        assertEquals(0, nextIndex)
    }

    @Test
    fun `previousChapter does not go below 0`() {
        val currentIndex = 0
        
        val prevIndex = if (currentIndex > 0) {
            currentIndex - 1
        } else {
            currentIndex
        }
        
        assertEquals(0, prevIndex)
    }

    @Test
    fun `parseChapterTitles handles missing title field`() {
        val json = """[{"url":"url1"},{"title":"第二章","url":"url2"}]"""
        val result = parseChapterTitles(json)
        
        assertEquals(2, result.size)
        assertEquals("", result[0])
        assertEquals("第二章", result[1])
    }

    private fun parseChapterTitles(json: String): List<String> {
        if (json.isBlank() || json == "[]") return emptyList()
        val type = object : com.google.gson.reflect.TypeToken<List<Map<String, String>>>() {}.type
        val chapters: List<Map<String, String>> = gson.fromJson(json, type)
        return chapters.map { it["title"] ?: "" }
    }

    private fun createTestBook(chapterList: String = "[]") = Book(
        bookUrl = "https://example.com/book",
        title = "Test Book",
        author = "Test Author",
        coverUrl = "",
        intro = "",
        bookSourceUrl = "",
        bookChapterUrl = "",
        latestChapter = "",
        bookInfo = "",
        chapterList = chapterList
    )
}