package com.novel.model

import com.google.gson.annotations.SerializedName

data class BookSource(
    @SerializedName("bookSourceName")
    val bookSourceName: String = "",
    
    @SerializedName("bookSourceUrl")
    val bookSourceUrl: String = "",
    
    @SerializedName("bookSourceGroup")
    val bookSourceGroup: String = "",
    
    @SerializedName("enabled")
    val enabled: Boolean = true,
    
    @SerializedName("searchUrl")
    val searchUrl: String = "",
    
    @SerializedName("ruleSearch")
    val ruleSearch: SearchRule = SearchRule(),
    
    @SerializedName("ruleBookInfo")
    val ruleBookInfo: BookInfoRule = BookInfoRule(),
    
    @SerializedName("ruleToc")
    val ruleToc: TocRule = TocRule(),
    
    @SerializedName("ruleContent")
    val ruleContent: ContentRule = ContentRule()
)

data class SearchRule(
    @SerializedName("bookList")
    val bookList: String = "",
    
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("author")
    val author: String = "",
    
    @SerializedName("coverUrl")
    val coverUrl: String = "",
    
    @SerializedName("bookUrl")
    val bookUrl: String = "",
    
    @SerializedName("wordCount")
    val wordCount: String = "",
    
    @SerializedName("lastChapter")
    val lastChapter: String = "",
    
    @SerializedName("intro")
    val intro: String = "",
    
    @SerializedName("kind")
    val kind: String = ""
)

data class BookInfoRule(
    @SerializedName("init")
    val init: String = "",
    
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("author")
    val author: String = "",
    
    @SerializedName("coverUrl")
    val coverUrl: String = "",
    
    @SerializedName("intro")
    val intro: String = "",
    
    @SerializedName("wordCount")
    val wordCount: String = "",
    
    @SerializedName("lastChapter")
    val lastChapter: String = "",
    
    @SerializedName("tocUrl")
    val tocUrl: String = ""
)

data class TocRule(
    @SerializedName("preUpdateJs")
    val preUpdateJs: String = "",
    
    @SerializedName("preUpdateAppLog")
    val preUpdateAppLog: String = "",
    
    @SerializedName("postUpdateJs")
    val postUpdateJs: String = "",
    
    @SerializedName("postUpdateAppLog")
    val postUpdateAppLog: String = "",
    
    @SerializedName("chapterList")
    val chapterList: String = "",
    
    @SerializedName("nextTocUrl")
    val nextTocUrl: String = "",
    
    @SerializedName("isVolume")
    val isVolume: String = ""
)

data class ContentRule(
    @SerializedName("preUpdateJs")
    val preUpdateJs: String = "",
    
    @SerializedName("preUpdateAppLog")
    val preUpdateAppLog: String = "",
    
    @SerializedName("postUpdateJs")
    val postUpdateJs: String = "",
    
    @SerializedName("postUpdateAppLog")
    val postUpdateAppLog: String = "",
    
    @SerializedName("content")
    val content: String = "",
    
    @SerializedName("replaceRegex")
    val replaceRegex: List<String> = emptyList(),
    
    @SerializedName("nextContentUrl")
    val nextContentUrl: String = "",
    
    @SerializedName("payAction")
    val payAction: String = ""
)