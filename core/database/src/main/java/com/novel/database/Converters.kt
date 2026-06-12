package com.novel.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.novel.model.*

class Converters {
    private val gson = Gson()
    
    @TypeConverter
    fun fromChapterList(chapters: List<Chapter>): String {
        return gson.toJson(chapters)
    }
    
    @TypeConverter
    fun toChapterList(json: String): List<Chapter> {
        val type = object : TypeToken<List<Chapter>>() {}.type
        return gson.fromJson(json, type)
    }
    
    @TypeConverter
    fun fromSearchRule(rule: SearchRule): String {
        return gson.toJson(rule)
    }
    
    @TypeConverter
    fun toSearchRule(json: String): SearchRule {
        return gson.fromJson(json, SearchRule::class.java)
    }
    
    @TypeConverter
    fun fromBookInfoRule(rule: BookInfoRule): String {
        return gson.toJson(rule)
    }
    
    @TypeConverter
    fun toBookInfoRule(json: String): BookInfoRule {
        return gson.fromJson(json, BookInfoRule::class.java)
    }
    
    @TypeConverter
    fun fromTocRule(rule: TocRule): String {
        return gson.toJson(rule)
    }
    
    @TypeConverter
    fun toTocRule(json: String): TocRule {
        return gson.fromJson(json, TocRule::class.java)
    }
    
    @TypeConverter
    fun fromContentRule(rule: ContentRule): String {
        return gson.toJson(rule)
    }
    
    @TypeConverter
    fun toContentRule(json: String): ContentRule {
        return gson.fromJson(json, ContentRule::class.java)
    }
    
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return gson.toJson(list)
    }
    
    @TypeConverter
    fun toStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, type)
    }
}