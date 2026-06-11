package com.novel.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_groups")
data class BookGroup(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val name: String,
    
    val sort: Int = 0,
    
    val createTime: Long = System.currentTimeMillis()
)