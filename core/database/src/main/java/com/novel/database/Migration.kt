package com.novel.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS book_groups (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    name TEXT NOT NULL,
                    sort INTEGER NOT NULL DEFAULT 0,
                    createTime INTEGER NOT NULL DEFAULT 0
                )
            """)
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                CREATE TABLE IF NOT EXISTS reading_records (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    bookUrl TEXT NOT NULL,
                    chapterTitle TEXT NOT NULL,
                    readTime INTEGER NOT NULL DEFAULT 0,
                    readProgress REAL NOT NULL DEFAULT 0.0
                )
            """)

            db.execSQL("CREATE INDEX IF NOT EXISTS index_reading_records_bookUrl ON reading_records(bookUrl)")
        }
    }
}