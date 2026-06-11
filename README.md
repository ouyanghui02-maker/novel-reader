# NovelReader

一个类似 Legado 的网络小说聚合阅读器，使用 Kotlin + Jetpack Compose 开发。

## 功能特性

### 书架管理
- 网格/列表视图切换
- 书籍分组管理
- 长按菜单操作（编辑、删除）
- 阅读进度记录

### 阅读器
- 沉浸式阅读体验
- 字体大小调整
- 行距调整
- 亮度调整
- 深色模式
- 书签管理

### 书源管理
- 完全兼容 Legado 书源格式
- 书源导入/导出
- 书源启用/禁用
- 书源分组管理

### 搜索功能
- 多源搜索
- 搜索历史
- 搜索结果展示

### 设置
- 阅读设置
- 主题设置
- 备份恢复
- WebDAV 同步

## 技术栈

- **语言**: Kotlin
- **UI 框架**: Jetpack Compose
- **架构模式**: MVVM
- **依赖注入**: Hilt
- **本地存储**: Room (SQLite)
- **网络请求**: Retrofit + OkHttp
- **图片加载**: Coil
- **书源解析**: Jsoup

## 项目结构

```
novel-reader/
├── app/                          # 主应用模块
├── core/
│   ├── common/                   # 公共工具类
│   ├── network/                  # 网络请求
│   ├── database/                 # Room 数据库
│   ├── model/                    # 数据模型
│   └── parser/                   # 书源解析器
├── feature/
│   ├── bookshelf/                # 书架功能
│   ├── reader/                   # 阅读器
│   ├── source/                   # 书源管理
│   ├── search/                   # 搜索功能
│   └── settings/                 # 设置
```

## 构建和运行

### 环境要求
- Android Studio Hedgehog 或更高版本
- JDK 17
- Android SDK 34

### 构建步骤
1. 克隆项目
2. 用 Android Studio 打开项目
3. 同步 Gradle
4. 运行应用

## 运行测试

### 单元测试
```bash
./gradlew test
```

### Android 仪器测试
```bash
./gradlew connectedAndroidTest
```

## 数据库迁移

项目包含以下数据库迁移：
- 1→2: 添加书籍分组功能
- 2→3: 添加阅读时间统计
- 3→4: 创建阅读记录表

## 开发计划

- [ ] 完善书源解析器
- [ ] 实现多源搜索
- [ ] 添加书源市场
- [ ] 实现 WebDAV 同步
- [ ] 添加 TTS 朗读功能
- [ ] 优化阅读器性能

## 许可证

MIT License