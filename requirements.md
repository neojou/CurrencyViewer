# CurrencyViewer — 需求規格（v1.6）

狀態：Draft（PM 定稿）
最後更新：2026-02-12

## 1. 目標（Goal）
以 Kotlin Multiplatform 製作一個跨平台應用（macOS Desktop + Web/Wasm GitHub Pages），用來查看 USD/JPY 的：
- 最新匯率數字（latest）
- 最新匯率日期（YYYY-MM-DD）
- 最近 30 天日線線圖（X 軸為日期（日））

## 2. 非目標（Non-goals）
v1.6 不做：
- 自動刷新（不輪詢、不背景更新）
- 手動刷新按鈕
- 多幣別切換（僅 USD/JPY）
- 日內（intraday）線圖與「1D/5D/1M/YTD」等切換
- 新聞、完整網頁嵌入、或任何 Google Finance 抓取/解析

## 3. 目標平台（Platforms）
- Desktop：macOS（Compose Multiplatform Desktop）
- Web：Kotlin/Wasm（Compose Multiplatform），部署到 GitHub Pages

## 4. 架構約束（Architecture constraints）
- Kotlin Multiplatform：大部分程式放在 commonMain
- desktopMain：只放薄薄的進入點 Main.kt（呼叫共用 App）
- wasmJsMain：只放薄薄的進入點 WasmMain.kt（呼叫共用 App）

## 5. 資料來源（Data source）
使用 Frankfurter API（client-side 使用、免 API key、提供 latest 與時間序列查詢）。[page:2]

### 5.1 端點（Endpoints）
- 最新匯率（USD 為 base、JPY 為 symbols）：
  - GET https://api.frankfurter.dev/v1/latest?base=USD&symbols=JPY [page:2]
  - 顯示：
    - 匯率數字：rates.JPY [page:2]
    - 匯率日期：date（YYYY-MM-DD）[page:2]

- 最近 30 天日線序列：
  - GET https://api.frankfurter.dev/v1/{start}..{end}?base=USD&symbols=JPY [page:2]
  - {start}/{end} 為 YYYY-MM-DD [page:2]

### 5.2 日期/時區規則
- 「匯率日期」顯示 API 回傳的 date（以 UTC 日期語意為準）。[page:2]
- 線圖 X 軸僅顯示日期（日），不顯示時間。

## 6. 畫面與互動（UX）
### 6.1 單一畫面（Single screen）
畫面需包含以下區塊（由上到下）：
1) 標題：USD/JPY
2) 最新匯率（大字）
3) 匯率日期（顯示在匯率數字下方）
4) 線圖：最近 30 天（daily）走勢線

### 6.2 線圖互動（Hover tooltip）
- 當滑鼠游標在「線圖繪製區域」內移動時：
  - 顯示一個浮動提示（tooltip/label），內容為：
    - 該位置對應的 X 軸日期（YYYY-MM-DD）
    - 該日期對應的 USD/JPY 匯率值（JPY）
- 對應規則：
  - 顯示「最接近游標 x 位置」的資料點（nearest-by-x）。
  - 若該日無資料點（例如週末/假日），以序列中最接近的日期點為準。
- 游標離開線圖區域時：提示消失。

### 6.3 自適應
- 線圖須能隨視窗/畫布尺寸改變而自動重新布局（不裁切主要內容）。
- 顯示範圍需涵蓋最近 30 天資料（以可用資料點為準）。

## 7. 載入與更新行為（Loading & refresh）
- v1.6 僅在「啟動時」抓取資料：
  - 抓取 latest（顯示匯率數字與匯率日期）
  - 抓取最近 30 天 time series（用於線圖）
- v1.6 不做自動刷新；若要更新資料，使用者需重啟 App / 重載頁面。

## 8. 錯誤處理（Error handling）
- 任何網路/API 失敗時：
  - App 不可崩潰
  - 顯示清楚的錯誤狀態（例如「資料暫時無法取得」）
- 若 latest 與 time series 僅有一個成功：
  - 盡可能顯示成功的部分（例如線圖可顯示但 latest 顯示錯誤，或反之）。

## 9. 驗收標準（Acceptance criteria / DoD）
### Desktop（macOS）
- 可顯示 USD/JPY latest 匯率與匯率日期。
- 可顯示最近 30 天日線線圖。
- 滑鼠在圖上移動時，可顯示對應日期與匯率值。
- 無網路時不崩潰，顯示錯誤狀態。

### Web（GitHub Pages）
- 可顯示相同內容（latest + date + 30 天線圖）。
- 滑鼠在圖上移動時，可顯示對應日期與匯率值。
- 無網路時不崩潰，顯示錯誤狀態。

## 10. 後續擴充（Out of scope for v1.6）
- 手動刷新 / 自動刷新（前景輪詢）
- 多幣對切換
- 日內線圖（intraday）
- 多資料源 fallback

