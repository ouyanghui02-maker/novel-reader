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