# CurrencyViewer — tasks.md (v1.6)

Last updated: 2026-02-12
Scope: v1.6 (MVP)

## 0. 標記規則
- Priority: P0（必做）/ P1（重要）/ P2（可延後）
- Size: S（0.5d）/ M（1d）/ L（2d）
- Owner role: Architect / Coder / Tester / Reviewer / Technical Writer / PM
- DoD: Definition of Done（完成定義）
- AC: Acceptance Criteria（驗收條件）

## 1. v1.6 交付目標（DoD）
- Desktop（macOS）可顯示：
  - USD/JPY latest 匯率數字 + 匯率日期（YYYY-MM-DD，UTC）
  - 最近 30 天（日資料）線圖（X 軸為日期（日））
  - 滑鼠在圖上移動：顯示最接近該 x 位置的資料點之「日期 + 匯率」
  - 無網路時不崩潰，顯示錯誤狀態
- Web（Wasm / GitHub Pages）可顯示上述相同功能
- v1.6 不做：自動刷新、手動刷新、多幣別、日內線圖

## 2. Sprint 規劃（建議）
- Sprint 0（腳手架/部署）：1–2 天
- Sprint 1（MVP 功能）：3–5 天
- Sprint 1.1（補強/修 bug）：1–2 天

---

## 3. Sprint 0：專案腳手架（P0）

### T0.1 建立 KMP 專案骨架（Desktop + Wasm）
- Priority: P0 / Size: M
- Owner: Architect + Coder
- Output:
  - Kotlin Multiplatform + Compose Multiplatform 專案可建置
  - `src/commonMain` 放 App/主要 UI
  - `src/desktopMain` 僅 `Main.kt`
  - `src/wasmJsMain` 僅 `WasmMain.kt`
- DoD:
  - `./gradlew build` 成功
  - Desktop 可啟動顯示「Placeholder UI」
  - Wasm 可在本機 dev server 開起來顯示「Placeholder UI」

### T0.2 GitHub Pages 部署管線（Wasm）
- Priority: P0 / Size: M
- Owner: Coder + Technical Writer
- Output:
  - GitHub Actions workflow：build Wasm 輸出到 Pages
  - README 補上「如何部署/更新」
- DoD:
  - push 到 main 後，Pages 可開啟並看到 Placeholder UI
  - 專案內無需手動複製檔案

### T0.3 基礎品質門檻
- Priority: P0 / Size: S
- Owner: Reviewer
- Output:
  - PR checklist（分層、可測試性、錯誤處理、跨平台差異）
  - 最小 code style 約定（命名、檔案結構、KDoc 是否需要）
- DoD:
  - repo 新增 `docs/pr-checklist.md` 或在 tasks.md 內直接列規則

---

## 4. Sprint 1：MVP 功能（P0）

### T1.1 定義 domain model 與資料流程
- Priority: P0 / Size: S
- Owner: Architect
- Output:
  - `FxPair = USD/JPY`（v1.6 固定）
  - `DailyRatePoint(date: LocalDate, rate: Double)`
  - `LatestRate(date: LocalDate, rate: Double)`
  - `UiState = Loading/Ready/Error`（至少）
  - 明確定義「匯率日期」使用 API 回傳的 date（UTC 意義）
- DoD:
  - architecture.md（最小版）新增「資料流與狀態」段落
  - commonMain 有對應資料類別（或至少介面）

### T1.2 實作 Frankfurter client（latest + 30d series）
- Priority: P0 / Size: M
- Owner: Coder
- Output:
  - `RatesRepository`（或 Provider）：
    - `getLatestUsdJpy(): LatestRate`
    - `getUsdJpyLast30Days(endDate = todayUtc): List<DailyRatePoint>`
  - 30 天區間計算（start = end - 29 days）
  - JSON 解析與錯誤處理（timeout / parse error）
- DoD:
  - Desktop/Wasm 皆能成功呼叫 API
  - 離線時回傳 Error（不丟出未捕捉例外到 UI）
- Notes:
  - v1.6 不需要快取；可在 v1.7 再加

### T1.3 畫面：Header + latest + date + chart layout
- Priority: P0 / Size: M
- Owner: Coder
- Output:
  - 單一畫面組合：
    - 標題：USD/JPY
    - 大字：latest rate
    - 下方：latest date（YYYY-MM-DD）
    - 線圖：30 天日線（X 軸日期）
  - Responsive：視窗縮放不會遮到主要資訊
- DoD:
  - Desktop/Wasm 皆可顯示
  - 沒資料時顯示 Loading / Error

### T1.4 線圖繪製（30 天）
- Priority: P0 / Size: M
- Owner: Coder
- Output:
  - 線圖元件（建議 Canvas 實作，放 commonMain）
  - Y 軸自動縮放（依資料 min/max，加 padding 避免貼邊）
  - X 軸顯示日期（日）
- DoD:
  - 30 天資料點可連成線
  - 視窗改變大小時重新 layout，不裁切

### T1.5 Hover tooltip：顯示日期 + 匯率（nearest-by-x）
- Priority: P0 / Size: M
- Owner: Coder + Reviewer
- Output:
  - 滑鼠在圖上移動時：
    - 計算游標 x 對應到序列 index
    - 取 nearest-by-x 的資料點
    - 在該點上方顯示「YYYY-MM-DD  +  匯率值」
  - 游標離開圖區域：tooltip 消失
- DoD:
  - Desktop：mouse hover 穩定可用
  - Wasm：mouse hover 可用（瀏覽器）
  - 不需要點擊；純 hover
- Reviewer checklist:
  - tooltip 計算與 UI 解耦（可測試）
  - 不要每次 pointer move 都做昂貴重算（簡單算 index 即可）

### T1.6 錯誤狀態與降級顯示
- Priority: P0 / Size: S
- Owner: Coder
- Output:
  - latest 失敗 / series 成功：仍顯示線圖，latest 區塊顯示錯誤
  - series 失敗 / latest 成功：仍顯示 latest，線圖區塊顯示錯誤
  - 都失敗：顯示整體錯誤狀態
- DoD:
  - 以上 3 種情境可驗收（手動斷網/模擬例外）

---

## 5. 測試（Sprint 1，P0/P1）

### T2.1 單元測試：日期區間計算
- Priority: P0 / Size: S
- Owner: Tester
- Output:
  - 測 `endDate` 給定時，start/end 是否正確（30 天含 end）
  - 測跨月、跨年邊界
- DoD:
  - commonTest 可跑

### T2.2 單元測試：nearest-by-x 對應規則
- Priority: P0 / Size: S
- Owner: Tester
- Output:
  - 給定 chartWidth、pointsCount、mouseX
  - 驗證映射到正確 index（含邊界 0 / last）
- DoD:
  - commonTest 可跑

### T2.3 單元測試：JSON mapping（最小）
- Priority: P1 / Size: S
- Owner: Tester
- Output:
  - 固定一份 Frankfurter 回應 sample（latest + series）
  - 測解析後的 date/rate 是否正確
- DoD:
  - commonTest 可跑

---

## 6. 文件與交付（Sprint 1，P0）

### T3.1 README（開發/部署）
- Priority: P0 / Size: S
- Owner: Technical Writer
- Output:
  - 如何跑 Desktop
  - 如何跑 Wasm（本機）
  - 如何部署到 GitHub Pages
  - 說明 v1.6 不做刷新、資料為日線
- DoD:
  - 新環境照 README 可跑起來

### T3.2 版本標記與發布
- Priority: P1 / Size: S
- Owner: PM + Technical Writer
- Output:
  - tag：`v1.6.0`
  - CHANGELOG（可簡短）
- DoD:
  - GitHub Release（可選）

---

## 7. Sprint 1.1（可選補強，P1/P2）
- P1：加「手動刷新」按鈕（重新抓 latest + series）
- P1：多資料源 fallback（Frankfurter 失敗改用另一家）
- P2：視覺微調（顏色、網格線、字體）

