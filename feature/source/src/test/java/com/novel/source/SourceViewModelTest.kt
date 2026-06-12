package com.novel.source

import com.novel.database.dao.BookSourceDao
import com.novel.model.BookSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SourceViewModelTest {

    private lateinit var viewModel: SourceViewModel
    private lateinit var fakeBookSourceDao: FakeBookSourceDao
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeBookSourceDao = FakeBookSourceDao()
        viewModel = SourceViewModel(fakeBookSourceDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads 5 default sources`() = runTest {
        val sources = viewModel.bookSources.first()
        assertEquals(5, sources.size)
    }

    @Test
    fun `default sources include biquge and qidian as enabled`() = runTest {
        val sources = viewModel.bookSources.first()
        val biquge = sources.find { it.bookSourceName == "笔趣阁" }
        val qidian = sources.find { it.bookSourceName == "起点中文网" }
        
        assertNotNull(biquge)
        assertTrue(biquge!!.enabled)
        assertNotNull(qidian)
        assertTrue(qidian!!.enabled)
    }

    @Test
    fun `toggleSource changes enabled state`() = runTest {
        val sources = viewModel.bookSources.first()
        val source = sources.first()
        val originalState = source.enabled
        
        viewModel.toggleSource(source)
        
        val updatedSources = viewModel.bookSources.first()
        val updated = updatedSources.find { it.bookSourceUrl == source.bookSourceUrl }
        assertEquals(!originalState, updated?.enabled)
    }

    @Test
    fun `addSource adds new source`() = runTest {
        val newSource = BookSource(
            bookSourceUrl = "https://new.com",
            bookSourceName = "新书源",
            bookSourceGroup = "测试",
            enabled = true
        )
        
        viewModel.addSource(newSource)
        
        val sources = viewModel.bookSources.first()
        assertTrue(sources.any { it.bookSourceUrl == "https://new.com" })
    }

    @Test
    fun `deleteSource removes source`() = runTest {
        val sources = viewModel.bookSources.first()
        val source = sources.first()
        
        viewModel.deleteSource(source)
        
        val updatedSources = viewModel.bookSources.first()
        assertFalse(updatedSources.any { it.bookSourceUrl == source.bookSourceUrl })
    }
}

class FakeBookSourceDao {
    private val sources = mutableListOf<BookSource>()
    val allSources = MutableStateFlow<List<BookSource>>(emptyList())

    fun getAllBookSources(): Flow<List<BookSource>> = allSources

    suspend fun insertBookSource(source: BookSource) {
        sources.add(source)
        allSources.value = sources.toList()
    }

    suspend fun insertBookSources(newSources: List<BookSource>) {
        sources.addAll(newSources)
        allSources.value = sources.toList()
    }

    suspend fun deleteBookSource(source: BookSource) {
        sources.removeAll { it.bookSourceUrl == source.bookSourceUrl }
        allSources.value = sources.toList()
    }

    suspend fun updateBookSourceEnabled(url: String, enabled: Boolean) {
        val index = sources.indexOfFirst { it.bookSourceUrl == url }
        if (index != -1) {
            sources[index] = sources[index].copy(enabled = enabled)
            allSources.value = sources.toList()
        }
    }
}