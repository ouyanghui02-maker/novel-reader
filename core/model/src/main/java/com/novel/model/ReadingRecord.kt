package com.novel.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reading_records",
    indices = [Index(value = ["bookUrl"])]
)
data class ReadingRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val bookUrl: String,
    
    val chapterTitle: String,
    
    val readTime: Long = 0,
    
    val readProgress: Float = 0f
)