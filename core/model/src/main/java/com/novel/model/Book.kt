package com.novel.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey
    val bookUrl: String,
    
    val title: String,
    
    val author: String,
    
    val coverUrl: String,
    
    val intro: String,
    
    val bookSourceUrl: String,
    
    val bookChapterUrl: String,
    
    val latestChapter: String,
    
    val bookInfo: String,
    
    val chapterList: List<Chapter> = emptyList(),
    
    val chapterCount: Int = 0,
    
    val lastReadTime: Long = 0L,
    
    val lastReadChapter: String = "",
    
    val lastReadProgress: Float = 0f
)