package com.novel.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {
    
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // 添加 book_group_id 字段到 books 表
            db.execSQL("ALTER TABLE books ADD COLUMN book_group_id INTEGER DEFAULT 0")
            
            // 创建 book_source_groups 表
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS book_source_groups (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name TEXT NOT NULL,
                    sort INTEGER NOT NULL DEFAULT 0,
                    create_time INTEGER NOT NULL DEFAULT 0
                )
            """)
        }
    }
    
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // 添加 last_update_time 字段到 books 表
            db.execSQL("ALTER TABLE books ADD COLUMN last_update_time INTEGER DEFAULT 0")
            
            // 添加 total_read_time 字段到 books 表
            db.execSQL("ALTER TABLE books ADD COLUMN total_read_time INTEGER DEFAULT 0")
        }
    }
    
    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // 创建 reading_records 表
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS reading_records (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    book_url TEXT NOT NULL,
                    chapter_title TEXT NOT NULL,
                    read_time INTEGER NOT NULL DEFAULT 0,
                    read_progress REAL NOT NULL DEFAULT 0.0
                )
            """)
            
            // 创建索引
            db.execSQL("CREATE INDEX IF NOT EXISTS index_reading_records_book_url ON reading_records(book_url)")
        }
    }
}