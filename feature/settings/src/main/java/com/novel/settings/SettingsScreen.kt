package com.novel.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设置") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsSection(title = "阅读设置") {
                SettingsItem(
                    title = "字体大小",
                    subtitle = "${settings.fontSize}sp",
                    onClick = { }
                )
                SettingsItem(
                    title = "行距",
                    subtitle = "${settings.lineSpacing}倍",
                    onClick = { }
                )
                SettingsItem(
                    title = "翻页动画",
                    subtitle = settings.pageAnimation,
                    onClick = { }
                )
            }
            
            SettingsSection(title = "主题设置") {
                SettingsSwitchItem(
                    title = "深色模式",
                    checked = settings.isDarkMode,
                    onCheckedChange = { viewModel.updateDarkMode(it) }
                )
                SettingsSwitchItem(
                    title = "跟随系统",
                    checked = settings.followSystem,
                    onCheckedChange = { viewModel.updateFollowSystem(it) }
                )
            }
            
            SettingsSection(title = "备份设置") {
                SettingsItem(
                    title = "导出数据",
                    subtitle = "导出书源和书架数据",
                    onClick = { }
                )
                SettingsItem(
                    title = "导入数据",
                    subtitle = "从文件导入数据",
                    onClick = { }
                )
                SettingsItem(
                    title = "WebDAV 同步",
                    subtitle = "配置云同步",
                    onClick = { }
                )
            }
            
            SettingsSection(title = "关于") {
                SettingsItem(
                    title = "版本",
                    subtitle = "1.0.0",
                    onClick = { }
                )
                SettingsItem(
                    title = "开源许可",
                    subtitle = "",
                    onClick = { }
                )
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsSwitchItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}