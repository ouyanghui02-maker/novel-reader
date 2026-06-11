package com.novel.parser

import com.novel.model.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookSourceParser @Inject constructor() {
    
    fun parseSearchResult(html: String, rule: SearchRule): List<Book> {
        val document = Jsoup.parse(html)
        val books = mutableListOf<Book>()
        
        val bookListElements = document.select(rule.bookList)
        for (element in bookListElements) {
            val name = element.select(rule.name).text()
            val author = element.select(rule.author).text()
            val coverUrl = element.select(rule.coverUrl).attr("abs:src")
            val bookUrl = element.select(rule.bookUrl).attr("abs:href")
            val intro = if (rule.intro.isNotEmpty()) element.select(rule.intro).text() else ""
            val lastChapter = if (rule.lastChapter.isNotEmpty()) element.select(rule.lastChapter).text() else ""
            
            if (name.isNotEmpty() && bookUrl.isNotEmpty()) {
                books.add(
                    Book(
                        bookUrl = bookUrl,
                        title = name,
                        author = author,
                        coverUrl = coverUrl,
                        intro = intro,
                        bookSourceUrl = "",
                        bookChapterUrl = "",
                        latestChapter = lastChapter,
                        bookInfo = ""
                    )
                )
            }
        }
        
        return books
    }
    
    fun parseBookInfo(html: String, rule: BookInfoRule): Book {
        val document = Jsoup.parse(html)
        
        val name = if (rule.name.isNotEmpty()) document.select(rule.name).text() else ""
        val author = if (rule.author.isNotEmpty()) document.select(rule.author).text() else ""
        val coverUrl = if (rule.coverUrl.isNotEmpty()) document.select(rule.coverUrl).attr("abs:src") else ""
        val intro = if (rule.intro.isNotEmpty()) document.select(rule.intro).text() else ""
        val lastChapter = if (rule.lastChapter.isNotEmpty()) document.select(rule.lastChapter).text() else ""
        val tocUrl = if (rule.tocUrl.isNotEmpty()) document.select(rule.tocUrl).attr("abs:href") else ""
        
        return Book(
            bookUrl = "",
            title = name,
            author = author,
            coverUrl = coverUrl,
            intro = intro,
            bookSourceUrl = "",
            bookChapterUrl = tocUrl,
            latestChapter = lastChapter,
            bookInfo = ""
        )
    }
    
    fun parseChapterList(html: String, rule: TocRule): List<Chapter> {
        val document = Jsoup.parse(html)
        val chapters = mutableListOf<Chapter>()
        
        val chapterElements = document.select(rule.chapterList)
        for (element in chapterElements) {
            val title = element.text()
            val url = element.attr("abs:href")
            
            if (title.isNotEmpty() && url.isNotEmpty()) {
                chapters.add(
                    Chapter(
                        title = title,
                        url = url
                    )
                )
            }
        }
        
        return chapters
    }
    
    fun parseChapterContent(html: String, rule: ContentRule): String {
        val document = Jsoup.parse(html)
        
        val content = if (rule.content.isNotEmpty()) {
            document.select(rule.content).text()
        } else {
            document.body().text()
        }
        
        var result = content
        for (replaceRegex in rule.replaceRegex) {
            val parts = split(replaceRegex)
            if (parts.size == 2) {
                result = result.replace(parts[0].toRegex(), parts[1])
            }
        }
        
        return result
    }
    
    private fun split(str: String): List<String> {
        val parts = mutableListOf<String>()
        var current = StringBuilder()
        var inQuote = false
        var quoteChar = ' '
        
        for (char in str) {
            when {
                char == '\\' -> continue
                char == '"' || char == '\'' -> {
                    if (!inQuote) {
                        inQuote = true
                        quoteChar = char
                    } else if (char == quoteChar) {
                        inQuote = false
                        parts.add(current.toString())
                        current = StringBuilder()
                    }
                }
                char == ',' && !inQuote -> {
                    parts.add(current.toString())
                    current = StringBuilder()
                }
                else -> current.append(char)
            }
        }
        
        if (current.isNotEmpty()) {
            parts.add(current.toString())
        }
        
        return parts
    }
}