package com.novel.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.novel.model.Chapter

class Converters {
    private val gson = Gson()
    
    @TypeConverter
    fun fromChapterList(chapters: List<Chapter>): String {
        return gson.toJson(chapters)
    }
    
    @TypeConverter
    fun toChapterList(json: String): List<Chapter> {
        if (json.isBlank() || json == "[]") return emptyList()
        val type = object : TypeToken<List<Chapter>>() {}.type
        return gson.fromJson(json, type)
    }
}