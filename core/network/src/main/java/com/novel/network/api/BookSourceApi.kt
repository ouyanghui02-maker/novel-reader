package com.novel.network.api

import com.novel.model.Book
import com.novel.model.Chapter
import retrofit2.http.GET
import retrofit2.http.Url

interface BookSourceApi {
    @GET
    suspend fun getBookList(@Url url: String): String
    
    @GET
    suspend fun getBookInfo(@Url url: String): String
    
    @GET
    suspend fun getChapterList(@Url url: String): String
    
    @GET
    suspend fun getChapterContent(@Url url: String): String
}