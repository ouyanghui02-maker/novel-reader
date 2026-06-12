package com.novel.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_sources")
data class BookSource(
    @PrimaryKey
    val bookSourceUrl: String = "",
    val bookSourceName: String = "",
    val bookSourceGroup: String = "",
    val enabled: Boolean = true,
    val searchUrl: String = "",
    val ruleSearch: String = "{}",
    val ruleBookInfo: String = "{}",
    val ruleToc: String = "{}",
    val ruleContent: String = "{}"
)

data class SearchRule(
    val bookList: String = "",
    val name: String = "",
    val author: String = "",
    val coverUrl: String = "",
    val bookUrl: String = "",
    val wordCount: String = "",
    val lastChapter: String = "",
    val intro: String = "",
    val kind: String = ""
)

data class BookInfoRule(
    val init: String = "",
    val name: String = "",
    val author: String = "",
    val coverUrl: String = "",
    val intro: String = "",
    val wordCount: String = "",
    val lastChapter: String = "",
    val tocUrl: String = ""
)

data class TocRule(
    val preUpdateJs: String = "",
    val preUpdateAppLog: String = "",
    val postUpdateJs: String = "",
    val postUpdateAppLog: String = "",
    val chapterList: String = "",
    val nextTocUrl: String = "",
    val isVolume: String = ""
)

data class ContentRule(
    val preUpdateJs: String = "",
    val preUpdateAppLog: String = "",
    val postUpdateJs: String = "",
    val postUpdateAppLog: String = "",
    val content: String = "",
    val replaceRegex: List<String> = emptyList(),
    val nextContentUrl: String = "",
    val payAction: String = ""
)