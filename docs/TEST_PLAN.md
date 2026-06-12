# NovelReader 测试文档

## 测试概览

| 模块 | 测试类 | 测试用例数 | 状态 |
|------|--------|-----------|------|
| core:model | BookTest | 5 | ✅ 通过 |
| core:model | ChapterTest | 3 | ✅ 通过 |
| core:parser | BookSourceParserTest | 6 | ✅ 通过 |

**总计: 14 个测试用例，全部通过**

---

## 1. 数据模型测试 (core:model)

### BookTest
| # | 测试用例 | 预期结果 |
|---|---------|---------|
| 1 | 创建 Book 对象时所有字段正确赋值 | 各字段值与构造参数一致 |
| 2 | chapterList 默认值为空 JSON 数组 | 默认值为 "[]" |
| 3 | chapterCount 默认值为 0 | 默认值为 0 |
| 4 | lastReadTime 默认值为 0 | 默认值为 0L |
| 5 | data class 相等性比较正确 | 相同字段的 Book 对象相等 |

### ChapterTest
| # | 测试用例 | 预期结果 |
|---|---------|---------|
| 1 | 创建 Chapter 对象时所有字段正确赋值 | 各字段值与构造参数一致 |
| 2 | content 默认值为 null | 默认值为 null |
| 3 | isRead 和 isCached 默认值为 false | 默认值为 false |

---

## 2. 书源解析器测试 (core:parser)

### BookSourceParserTest
| # | 测试用例 | 预期结果 |
|---|---------|---------|
| 1 | 解析搜索结果 HTML 提取书籍列表 | 正确提取书名、作者、封面、URL |
| 2 | 解析空 HTML 返回空列表 | 返回 emptyList |
| 3 | 解析书籍详情页提取书籍信息 | 正确提取各字段 |
| 4 | 解析章节目录提取章节列表 | 正确提取章节标题和 URL |
| 5 | 解析章节内容提取正文 | 正确提取文本内容 |
| 6 | 解析带替换规则的内容 | 正确应用正则替换 |

---

## 3. 书架 ViewModel 测试 (feature:bookshelf)

### BookshelfViewModelTest
| # | 测试用例 | 预期结果 |
|---|---------|---------|
| 1 | 添加书籍后 books 列表更新 | 包含新添加的书籍 |
| 2 | 删除书籍后 books 列表更新 | 不包含已删除的书籍 |
| 3 | 更新阅读进度后书籍数据更新 | lastReadChapter 和 lastReadProgress 更新 |
| 4 | 初始状态 books 为空 | 返回 emptyList |
| 5 | 添加多本书籍后列表正确排序 | 按 lastReadTime 降序 |
| 6 | 删除不存在的书籍不崩溃 | 列表不变 |

---

## 4. 阅读器 ViewModel 测试 (feature:reader)

### ReaderViewModelTest
| # | 测试用例 | 预期结果 |
|---|---------|---------|
| 1 | parseChapterTitles 解析空 JSON | 返回空列表 |
| 2 | parseChapterTitles 解析空数组 | 返回空列表 |
| 3 | parseChapterTitles 解析正常 JSON | 返回正确的标题列表 |
| 4 | nextChapter 在最后一章时不崩溃 | currentChapterIndex 不变 |
| 5 | previousChapter 在第一章时不崩溃 | currentChapterIndex 不变 |
| 6 | loadBook 加载书籍后 currentBook 更新 | currentBook 不为 null |

---

## 5. 搜索 ViewModel 测试 (feature:search)

### SearchViewModelTest
| # | 测试用例 | 预期结果 |
|---|---------|---------|
| 1 | 搜索空关键词时 results 为空 | searchResults 为空列表 |
| 2 | 搜索空白字符时 results 为空 | searchResults 为空列表 |
| 3 | 搜索关键词后 searchHistory 增加 | 搜索历史包含该关键词 |
| 4 | clearSearchHistory 清空历史 | searchHistory 为空 |
| 5 | 搜索后 isLoading 正确切换 | 搜索期间为 true，完成后为 false |

---

## 6. 书源 ViewModel 测试 (feature:source)

### SourceViewModelTest
| # | 测试用例 | 预期结果 |
|---|---------|---------|
| 1 | 初始化后加载 5 个默认书源 | bookSources 列表包含 5 个书源 |
| 2 | 默认书源中笔趣阁和起点启用 | enabled = true |
| 3 | toggleSource 切换书源状态 | enabled 值取反 |
| 4 | addSource 添加新书源 | bookSources 包含新书源 |
| 5 | deleteSource 删除书源 | bookSources 不包含已删除书源 |

---

## 运行测试

```bash
# 运行所有单元测试
./gradlew test

# 运行特定模块测试
./gradlew :core:model:test
./gradlew :feature:bookshelf:test
./gradlew :feature:reader:test
./gradlew :feature:search:test
./gradlew :feature:source:test
```

## 测试覆盖率目标

| 模块 | 目标覆盖率 |
|------|-----------|
| core:model | 90% |
| core:parser | 80% |
| feature:bookshelf | 85% |
| feature:reader | 85% |
| feature:search | 80% |
| feature:source | 80% |
