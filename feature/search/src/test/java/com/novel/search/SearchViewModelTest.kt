package com.novel.search

import com.novel.database.dao.BookDao
import com.novel.database.dao.BookSourceDao
import com.novel.database.dao.SearchHistoryDao
import com.novel.model.Book
import com.novel.model.BookSource
import com.novel.model.SearchHistory
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
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var fakeBookDao: FakeSearchBookDao
    private lateinit var fakeBookSourceDao: FakeSearchBookSourceDao
    private lateinit var fakeSearchHistoryDao: FakeSearchHistoryDao
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeBookDao = FakeSearchBookDao()
        fakeBookSourceDao = FakeSearchBookSourceDao()
        fakeSearchHistoryDao = FakeSearchHistoryDao()
        viewModel = SearchViewModel(fakeBookDao, fakeBookSourceDao, fakeSearchHistoryDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search with blank keyword returns empty`() = runTest {
        viewModel.searchBooks("")
        
        val results = viewModel.searchResults.first()
        assertTrue(results.isEmpty())
    }

    @Test
    fun `search with whitespace keyword returns empty`() = runTest {
        viewModel.searchBooks("   ")
        
        val results = viewModel.searchResults.first()
        assertTrue(results.isEmpty())
    }

    @Test
    fun `search adds to history`() = runTest {
        viewModel.searchBooks("斗破")
        
        val history = fakeSearchHistoryDao.history.value
        assertTrue(history.any { it.keyword == "斗破" })
    }

    @Test
    fun `clearSearchHistory clears all`() = runTest {
        fakeSearchHistoryDao.insertSearchHistory(SearchHistory(keyword = "test"))
        
        viewModel.clearSearchHistory()
        
        val history = fakeSearchHistoryDao.history.value
        assertTrue(history.isEmpty())
    }

    @Test
    fun `search with keyword finds matching books`() = runTest {
        fakeBookDao.books.add(
            Book(
                bookUrl = "url1",
                title = "斗破苍穹",
                author = "天蚕土豆",
                coverUrl = "",
                intro = "",
                bookSourceUrl = "",
                bookChapterUrl = "",
                latestChapter = "",
                bookInfo = ""
            )
        )
        
        viewModel.searchBooks("斗破")
        
        val results = viewModel.searchResults.first()
        assertEquals(1, results.size)
        assertEquals("斗破苍穹", results[0].title)
    }
}

class FakeSearchBookDao {
    val books = mutableListOf<Book>()

    fun searchBooks(keyword: String) = kotlinx.coroutines.flow.flow {
        emit(books.filter {
            it.title.contains(keyword, ignoreCase = true) ||
            it.author.contains(keyword, ignoreCase = true)
        })
    }
}

class FakeSearchBookSourceDao {
    fun getAllBookSources() = kotlinx.coroutines.flow.flow {
        emit(emptyList<BookSource>())
    }
}

class FakeSearchHistoryDao {
    val history = MutableStateFlow<List<SearchHistory>>(emptyList())

    fun getRecentSearchHistory(): Flow<List<SearchHistory>> = history

    suspend fun insertSearchHistory(searchHistory: SearchHistory) {
        val current = history.value.toMutableList()
        current.add(searchHistory)
        history.value = current
    }

    suspend fun clearAllSearchHistory() {
        history.value = emptyList()
    }
}

private fun <T> kotlinx.coroutines.flow.MutableStateFlow<List<T>>.value(): List<T> = this.value