package com.novel.source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novel.database.dao.BookSourceDao
import com.novel.model.BookSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    private val bookSourceDao: BookSourceDao
) : ViewModel() {
    
    val bookSources: Flow<List<BookSource>> = bookSourceDao.getAllBookSources()
    
    init {
        loadDefaultSources()
    }
    
    private fun loadDefaultSources() {
        viewModelScope.launch {
            val defaultSources = listOf(
                BookSource(
                    bookSourceUrl = "https://www.biquge.com",
                    bookSourceName = "笔趣阁",
                    bookSourceGroup = "玄幻 · 武侠",
                    enabled = true
                ),
                BookSource(
                    bookSourceUrl = "https://www.qidian.com",
                    bookSourceName = "起点中文网",
                    bookSourceGroup = "全品类 · 正版",
                    enabled = true
                ),
                BookSource(
                    bookSourceUrl = "https://www.fanqienovel.com",
                    bookSourceName = "番茄小说",
                    bookSourceGroup = "免费 · 全品类",
                    enabled = false
                ),
                BookSource(
                    bookSourceUrl = "https://www.ciweimao.com",
                    bookSourceName = "刺猬猫",
                    bookSourceGroup = "轻小说 · 二次元",
                    enabled = false
                ),
                BookSource(
                    bookSourceUrl = "https://www.zongheng.com",
                    bookSourceName = "纵横中文网",
                    bookSourceGroup = "玄幻 · 都市",
                    enabled = false
                )
            )
            bookSourceDao.insertBookSources(defaultSources)
        }
    }
    
    fun addSource(source: BookSource) {
        viewModelScope.launch {
            bookSourceDao.insertBookSource(source)
        }
    }
    
    fun deleteSource(source: BookSource) {
        viewModelScope.launch {
            bookSourceDao.deleteBookSource(source)
        }
    }
    
    fun toggleSource(source: BookSource) {
        viewModelScope.launch {
            bookSourceDao.updateBookSourceEnabled(source.bookSourceUrl, !source.enabled)
        }
    }
}