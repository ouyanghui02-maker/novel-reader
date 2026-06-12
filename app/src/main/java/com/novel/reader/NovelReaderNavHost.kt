package com.novel.reader

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.novel.bookshelf.BookshelfScreen
import com.novel.reader.feature.ReaderScreen
import com.novel.search.SearchScreen
import com.novel.settings.SettingsScreen
import com.novel.source.SourceScreen

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Bookshelf : Screen("bookshelf", "书架", Icons.Default.MenuBook)
    object Discover : Screen("discover", "发现", Icons.Default.Explore)
    object Profile : Screen("profile", "我的", Icons.Default.Person)
    object Reader : Screen("reader/{bookUrl}", "阅读", Icons.Default.MenuBook)
    object Search : Screen("search", "搜索", Icons.Default.Explore)
    object Source : Screen("source", "书源管理", Icons.Default.Person)
    object Settings : Screen("settings", "设置", Icons.Default.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovelReaderNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val bottomBarScreens = listOf(Screen.Bookshelf, Screen.Discover, Screen.Profile)
    val showBottomBar = bottomBarScreens.any { screen ->
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    }
    
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomBarScreens.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Bookshelf.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Bookshelf.route) {
                BookshelfScreen(
                    onBookClick = { bookUrl ->
                        navController.navigate("reader/$bookUrl")
                    },
                    onSearchClick = {
                        navController.navigate(Screen.Search.route)
                    }
                )
            }
            composable(Screen.Discover.route) {
                DiscoverScreen(
                    onSourceClick = {
                        navController.navigate(Screen.Source.route)
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onSettingsClick = {
                        navController.navigate(Screen.Settings.route)
                    }
                )
            }
            composable(Screen.Reader.route) { backStackEntry ->
                val bookUrl = backStackEntry.arguments?.getString("bookUrl") ?: ""
                ReaderScreen(
                    bookUrl = bookUrl,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.Search.route) {
                SearchScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onBookClick = { bookUrl ->
                        navController.navigate("reader/$bookUrl")
                    }
                )
            }
            composable(Screen.Source.route) {
                SourceScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun DiscoverScreen(onSourceClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "发现",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "热门书源", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "笔趣阁 - 玄幻小说", style = MaterialTheme.typography.bodyMedium)
                Text(text = "起点中文网 - 全品类", style = MaterialTheme.typography.bodyMedium)
                Text(text = "番茄小说 - 免费阅读", style = MaterialTheme.typography.bodyMedium)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onSourceClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("管理书源")
        }
    }
}

@Composable
fun ProfileScreen(onSettingsClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "我的",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "NovelReader", style = MaterialTheme.typography.titleMedium)
                Text(text = "v1.0.0", style = MaterialTheme.typography.bodySmall)
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "阅读统计", style = MaterialTheme.typography.titleMedium)
                Text(text = "累计阅读: 0 分钟", style = MaterialTheme.typography.bodyMedium)
                Text(text = "阅读书籍: 0 本", style = MaterialTheme.typography.bodyMedium)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(
            onClick = onSettingsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("设置")
        }
    }
}