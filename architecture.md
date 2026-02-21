# CurrencyViewer Architecture

**版本**：0.2  
**最後更新**：2026-02-21  
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

6. **通用工具與業務邏輯分離**  
   通用、可跨專案重用的基礎建設（如網路客戶端抽象）應放在 `tools` 包下，不混入任何業務邏輯。

## 2. 高層架構（文字版）

```text
┌─────────────────────┐ ┌─────────────────────┐
│ Desktop (macOS)     │ │ Web (Wasm)          │
│ Main.kt             │ │ WasmMain.kt         │
│ → Window + App()    │ │ → Canvas + App()    │
└──────────┬──────────┘ └──────────┬──────────┘
           │                         │
           └─────────────────────────┘
                       │
                 commonMain
┌─────────────────────────────────────────────────────────────────────┐
│ ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐        │
│ │ tools / web     │ │ UI Layer        │ │ State / Flow    │        │
│ │ - HttpClient    │ │ - App.kt        │ │ - UiState       │        │
│ │   Factory       │ │ - Components    │ │ - StateFlow     │        │
│ │ - HttpFetcher   │ │ - Chart         │ │ - Events        │        │
│ │ - HttpFetcher   │ └─────────────────┘ └─────────────────┘        │
│ │   Json          │                                                  │
│ └─────────────────┘                                                  │
│                                                                      │
│ ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐        │
│ │ Domain Layer    │ │ Data Layer      │ │ platform/       │        │
│ │ - Models        │ │ - Repository    │ │ (平台抽象預留)  │        │
│ │ - Currency      │ │ - Frankfurter   │ │                 │        │
│ │                 │ │   DataSource    │ │                 │        │
│ └─────────────────┘ └─────────────────┘ └─────────────────┘        │
└─────────────────────────────────────────────────────────────────────┘
                       ▲
               KMP Coroutines / Flow
```

## 3. 目前技術選型（v1.0 目標）

| 類別                  | 技術選擇                              | 版本建議     | 主要理由                                                                 |
|-----------------------|---------------------------------------|--------------|--------------------------------------------------------------------------|
| UI                    | Compose Multiplatform                 | 1.7.x+       | Desktop + Wasm 同時支援                                                  |
| 網路請求              | Ktor Client (CIO engine)              | 3.x          | KMP 原生、輕量、coroutine-based；CIO 適合多平台、非阻塞 I/O             |
| JSON 序列化           | kotlinx-serialization                 | 1.7.x+       | 官方推薦、性能好                                                         |
| 日期處理              | kotlinx-datetime                      | 0.6.x+       | 跨平台 ISO-8601 處理                                                     |
| 通用網路工具          | HttpFetcher + HttpFetcherJson         | —            | 跨專案可重用、無業務邏輯的 HTTP 抽象層（放在 tools.web）                |
| 依賴注入              | 暫不引入（手動建構）                  | —            | 初期規模小，保持簡單；未來視規模考慮 Koin 輕量注入                      |
| 圖表                  | 待定（Compose Charts / Multiplatform）| —            | Sprint 1 再決定                                                          |
| 離線儲存              | 暫無                                  | —            | v1.0 不需要持久化                                                        |

**刻意避免**（v1.0 階段）：
- Room / SQLDelight
- Koin / Kodein / Ktor DI 框架（除非必要）
- Navigation / Decompose / Voyager 等導航庫
- 將業務邏輯混入通用工具層（tools.web 僅限基礎網路抽象）

## 4. 未來擴展方向（參考用）

- 多幣別支援 → 拆 `:feature:currency-rates`
- Android / iOS 支援 → 加入 `:androidMain` / `:iosMain`
- 離線快取 → 引入 SQLDelight 或 DataStore
- 設定畫面 → 引入簡單導航（Voyager / Decompose）
- 主題切換 / 多語言 → `:core:designsystem` + `:core:i18n`
- 通用工具抽離 → 考慮獨立 `:tools-web` 或 `:core-network` module

## 5. 命名與檔案組織約定（初期）

```text
composeApp/
└── src/
    ├── commonMain/kotlin/com/neojou/
    │   ├── currencyviewer/           # 業務主包（App.kt, ui/, data/, domain/）
    │   │   ├── App.kt
    │   │   ├── ui/
    │   │   │   ├── components/
    │   │   │   └── screens/
    │   │   ├── data/
    │   │   │   ├── remote/
    │   │   │   └── repository/
    │   │   └── domain/
    │   │       └── model/
    │   └── tools/
    │       └── web/                  # 通用、可跨專案網路基礎建設
    │           ├── HttpClientFactory.kt
    │           ├── HttpFetcher.kt
    │           └── HttpFetcherJson.kt
    ├── desktopMain/kotlin/...
    └── wasmJsMain/kotlin/...
```

## 資料流與狀態

### 概述
系統資料流遵循分層責任：

1. **通用網路層**（tools.web）：提供純 HTTP 抽象（HttpClient 建立、請求執行、結果包裝），不含任何業務知識。
2. **API Layer**（data.remote）：業務專屬 DataSource（如 FrankfurterDataSourceImpl），使用通用工具呼叫 API，轉換成 Domain Models。
3. **Domain Models**：核心資料結構，跨平台共用（commonMain）。
4. **UiState**：包裝狀態，支援 Loading/Ready/Error。
5. **UI Layer**：Compose 消費 UiState，顯示圖表與最新值。

### 簡單資料流圖

```mermaid
graph TD
    A[Frankfurter API] --> B[HttpFetcher / HttpFetcherJson<br>(通用工具層 - tools.web)]
    B --> C[FrankfurterDataSourceImpl<br>(業務專屬 - data.remote)]
    C --> D[Domain Models<br>FxPair, DailyRatePoint, LatestRate]
    D --> E[UiState<br>Loading / Ready / Error]
    E --> F[Compose UI<br>Chart & Latest Rate]
```

### 模型說明
- FxPair: Value class，固定 "USD/JPY"。
- DailyRatePoint: Data class (date: LocalDate, rate: Double) – 用於日線圖。
- LatestRate: Data class (date: LocalDate, rate: Double) – 用於最新顯示。
- UiState<T>: Sealed interface – Loading, Ready(data: T), Error(message: String, cause: Throwable?)。

### 狀態管理建議
- ViewModel 中以 StateFlow<UiState<T>> 管理（Desktop/Wasm 共用）。
- 未來擴展：MVI 或 Redux-like，若需複雜 side effects。

**變更紀錄**
- v0.2 (2026-02-21)：新增 tools.web 包說明、Ktor CIO 引擎細節、通用網路層與業務層分離、更新資料流圖

