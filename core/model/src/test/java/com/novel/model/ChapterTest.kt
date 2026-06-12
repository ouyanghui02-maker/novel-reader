package com.novel.model

import org.junit.Assert.*
import org.junit.Test

class ChapterTest {

    @Test
    fun `create Chapter with all fields`() {
        val chapter = Chapter(
            title = "第一章 少年崛起",
            url = "https://example.com/chapter1",
            content = "这是章节内容",
            isRead = true,
            isCached = true
        )
        
        assertEquals("第一章 少年崛起", chapter.title)
        assertEquals("https://example.com/chapter1", chapter.url)
        assertEquals("这是章节内容", chapter.content)
        assertTrue(chapter.isRead)
        assertTrue(chapter.isCached)
    }

    @Test
    fun `content default is null`() {
        val chapter = Chapter(title = "test", url = "test")
        assertNull(chapter.content)
    }

    @Test
    fun `isRead and isCached default to false`() {
        val chapter = Chapter(title = "test", url = "test")
        assertFalse(chapter.isRead)
        assertFalse(chapter.isCached)
    }
}