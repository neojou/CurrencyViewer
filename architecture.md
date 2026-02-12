# CurrencyViewer Architecture

**版本**：0.1  
**最後更新**：2026-02-12  
**適用階段**：Sprint 0 ~ Sprint 1（初始骨架與單一畫面版本）

## 1. 核心設計原則

1. **最大化 commonMain 程式碼共享**  
   幾乎所有業務邏輯、領域模型、UI 組成、狀態管理都應放在 `commonMain`。

2. **平台層極薄**  
   `desktopMain` 和 `wasmJsMain` 只負責：
   - 啟動流程
   - 視窗 / Canvas 建立
   - 平台特定注入（如檔案系統、瀏覽器 API）

3. **單一畫面應用（Single Screen App）**  
   v1.0 階段只有一個主要 Composable 畫面，無複雜導航。

4. **可測試性優先**  
   資料層（Repository）、領域邏輯（UseCase / Interactor）應為純 Kotlin 類別，易於單元測試。

5. **漸進式模組化**  
   - Sprint 0–1：單一 `composeApp` module（快速驗證）
   - Sprint 2 之後：視需求拆分為 `:core:xxx` / `:feature:xxx`

## 2. 高層架構（文字版）
```text
┌─────────────────────┐          ┌─────────────────────┐
│   Desktop (macOS)   │          │     Web (Wasm)      │
│     Main.kt         │          │     WasmMain.kt     │
│   → Window + App()  │          │   → Canvas + App()  │
└──────────┬──────────┘          └──────────┬──────────┘
│                                      │
└───────────────────┬──────────────────┘
│
commonMain
│
┌───────────────────────┬────────────┼────────────┬───────────────────────┐
│                       │                         │                       │
┌──────────────┐       ┌───────────────┐       ┌───────────────┐       ┌───────────────┐
│   UI Layer   │       │  State / Flow  │       │  Domain Layer  │       │   Data Layer  │
│ - App.kt     │       │ - UiState      │       │ - Models       │       │ - Repository  │
│ - Components │       │ - StateFlow    │       │ - Currency     │       │ - Frankfurter │
│ - Chart      │       │ - Events       │       │                │       │   ApiClient   │
└──────────────┘       └───────────────┘       └───────────────┘       └───────────────┘
▲
│
KMP Coroutines / Flow
```


## 3. 目前技術選型（v1.0 目標）

| 類別               | 技術選擇                        | 版本建議       | 主要理由                                      |
|--------------------|---------------------------------|----------------|-----------------------------------------------|
| UI                 | Compose Multiplatform           | 1.7.x+         | Desktop + Wasm 同時支援                       |
| 網路請求           | Ktor Client                     | 3.x            | KMP 原生、輕量、與 kotlinx-serialization 整合佳 |
| JSON 序列化        | kotlinx-serialization           | 1.7.x+         | 官方推薦、性能好                              |
| 日期處理           | kotlinx-datetime                | 0.6.x+         | 跨平台 ISO-8601 處理                          |
| 依賴注入           | 暫不引入（手動建構或 Koin 輕量）| —              | 初期規模小，保持簡單                          |
| 圖表               | 待定（Compose Charts / Multiplatform 方案） | — | Sprint 1 再決定                               |
| 離線儲存           | 暫無                            | —              | v1.0 不需要持久化                             |

**刻意避免**（v1.0 階段）：
- Room / SQLDelight
- Koin / Kodein / Ktor DI 框架（除非必要）
- Navigation / Decompose / Voyager 等導航庫

## 4. 未來擴展方向（參考用）

- 多幣別支援 → 拆 `:feature:currency-rates`
- Android / iOS 支援 → 加入 `:androidMain` / `:iosMain`
- 離線快取 → 引入 SQLDelight 或 DataStore
- 設定畫面 → 引入簡單導航（Voyager / Decompose）
- 主題切換 / 多語言 → `:core:designsystem` + `:core:i18n`

## 5. 命名與檔案組織約定（初期）
```text
composeApp/
└── src/
├── commonMain/kotlin/com/neojou/currencyviewer/
│   ├── App.kt
│   ├── ui/
│   │   ├── components/
│   │   └── screens/
│   ├── data/
│   │   ├── remote/
│   │   └── repository/
│   ├── domain/
│   │   └── model/
│   └── platform/          ← 平台相關抽象（預留）
├── desktopMain/kotlin/...
└── wasmJsMain/kotlin/...
```


