package com.novel.model

data class Chapter(
    val title: String,
    
    val url: String,
    
    val content: String? = null,
    
    val isRead: Boolean = false,
    
    val isCached: Boolean = false
)