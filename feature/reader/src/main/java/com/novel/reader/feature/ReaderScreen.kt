package com.novel.reader.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(
    bookUrl: String,
    onBackClick: () -> Unit,
    viewModel: ReaderViewModel = hiltViewModel()
) {
    var showToolbar by remember { mutableStateOf(false) }
    var fontSize by remember { mutableStateOf(16.sp) }
    var lineSpacing by remember { mutableStateOf(1.8f) }
    var isDarkMode by remember { mutableStateOf(false) }
    var showSettingsPanel by remember { mutableStateOf(false) }
    var brightness by remember { mutableFloatStateOf(0.8f) }
    
    val backgroundColor = if (isDarkMode) Color(0xFF1a1a1a) else Color(0xFFF8F9FA)
    val textColor = if (isDarkMode) Color(0xFFE0E0E0) else Color(0xFF333333)
    
    Scaffold(
        topBar = {
            if (showToolbar) {
                TopAppBar(
                    title = { Text("阅读") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* TODO: 添加书签 */ }) {
                            Icon(Icons.Default.Bookmark, contentDescription = "书签")
                        }
                        IconButton(onClick = { showSettingsPanel = !showSettingsPanel }) {
                            Icon(Icons.Default.Settings, contentDescription = "设置")
                        }
                    }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(padding)
                .clickable { showToolbar = !showToolbar }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                Text(
                    text = "第一千二百三十四章 新的征程",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Text(
                    text = "萧炎站在云岚宗的废墟之上，望着远方的天际，心中充满了感慨。三年之约已经完成，云岚宗也已成为过去。他知道，这只是一个新的开始。",
                    fontSize = fontSize,
                    lineHeight = fontSize * lineSpacing,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Text(
                    text = "\"萧炎，我们该走了。\"药老的声音从戒指中传出，带着一丝欣慰。",
                    fontSize = fontSize,
                    lineHeight = fontSize * lineSpacing,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                
                Text(
                    text = "萧炎点了点头，转身看向身后的一行人。彩鳞、小医仙、紫研……这些跟随他一路走来的伙伴，都将与他一同前往中州，开启新的旅程。",
                    fontSize = fontSize,
                    lineHeight = fontSize * lineSpacing,
                    color = textColor,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
            
            // 设置面板
            if (showSettingsPanel && showToolbar) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // 字体大小调整
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.FormatSize, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("字体大小: ${fontSize.value.toInt()}sp", modifier = Modifier.weight(1f))
                            Slider(
                                value = fontSize.value,
                                onValueChange = { fontSize = it.sp },
                                valueRange = 12f..24f,
                                modifier = Modifier.width(120.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // 行距调整
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("行距: ${"%.1f".format(lineSpacing)}倍", modifier = Modifier.weight(1f))
                            Slider(
                                value = lineSpacing,
                                onValueChange = { lineSpacing = it },
                                valueRange = 1.0f..3.0f,
                                modifier = Modifier.width(120.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // 亮度调整
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Brightness6, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("亮度: ${"%.0f".format(brightness * 100)}%", modifier = Modifier.weight(1f))
                            Slider(
                                value = brightness,
                                onValueChange = { brightness = it },
                                valueRange = 0.1f..1.0f,
                                modifier = Modifier.width(120.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // 深色模式开关
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("深色模式", modifier = Modifier.weight(1f))
                            Switch(
                                checked = isDarkMode,
                                onCheckedChange = { isDarkMode = it }
                            )
                        }
                    }
                }
            }
        }
    }
}