<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" style="height:64px;margin-right:32px"/>

# 建立一個專案空間：CurrencyViewer AI 團隊工作區。請分析我上傳的 ai-team.md, communication.md, roles.md, requirements.md, architecture.md 等檔案，作為後續討論的上下文。

完美！我已經成功讀取並分析了你上傳的五份核心文件。現在為你創建一個**專案空間總結文檔**，作為後續討論的上下文基準。

***

## 📋 **CurrencyViewer AI 團隊工作區 — 專案空間分析**

### 🎯 **專案概況**

| 面向 | 內容 |
| :-- | :-- |
| **專案名稱** | CurrencyViewer（Kotlin Multiplatform / Compose Multiplatform） |
| **目標** | 跨平台應用（macOS Desktop + Web/Wasm）查看 USD/JPY 匯率與 30 天走勢圖 |
| **版本** | v1.6（單畫面、無刷新、無多幣別） |
| **平台** | macOS Desktop + GitHub Pages (Wasm) |
| **API 數據源** | Frankfurter API（client-side，無 key 需求） |


***

### 📁 **核心文件架構與主要內容**

#### **1️⃣ ai-team.md（協作入口）**

- **功能**：AI 團隊協作的中樞；規定角色名稱、文件優先序、工作流程
- **關鍵規則**：
    - 8 個 canonical roles（Owner / PM / Architect / Coder / QE / DevOps / Writer / Artist）
    - 文件優先序：Owner 指示 > tasks.md > ADR > architecture.md > requirements.md > docs
    - 所有交付物必須落在 repo（Issue / PR / ADR）
    - 系統啟動模板（必貼於每次新聊天）


#### **2️⃣ communication.md（溝通規範）**

- **4 大模板**：
    - **Work Request**：交辦任務（Goal + Context + AC + DoD + Verification）
    - **Review Notes**：結構化回饋（Must-fix / Should / Nice-to-have + Verification）
    - **Handoff**：跨角色交接（What changed + Where to look + How to verify + Risks）
    - **Issue Status Labels**：status:ready / blocked / in-progress / needs-review / needs-decision
- **Cross-cutting labels**：decision / handoff / breaking-change / good-first-issue


#### **3️⃣ roles.md（角色職責）**

- **8 個角色定義**（各含 Mission / Responsibilities / Inputs/Outputs / DoD）：
    - **Owner**（人類）：定義需求、決策、驗收
    - **PM**：拆解任務、排程、協調交接
    - **Architect**：技術決策、ADR、code review
    - **Coder**：實作、測試、交付 PR
    - **QE**：自動化測試、驗收、缺陷回報
    - **DevOps**：CI/CD、build policy、部署
    - **Writer**：文件、ADR、模板
    - **Artist**：視覺資產、UI/UX 建議


#### **4️⃣ requirements.md（產品需求）**

- **核心需求**：
    - 顯示 USD/JPY latest 匯率與日期
    - 線圖：最近 30 天日線
    - Tooltip：滑鼠懸停顯示日期 + 匯率值
- **API 端點**：
    - 最新：`GET https://api.frankfurter.dev/v1/latest?base=USD&symbols=JPY`
    - 時序：`GET https://api.frankfurter.dev/v1/{start}..{end}?base=USD&symbols=JPY`
- **非目標**：自動刷新、手動刷新、多幣別、日內線圖、新聞整合
- **AC 與驗收**：build 可過、行為符合、跨平台差異明確、無網路時不崩潰


#### **5️⃣ architecture.md（技術架構）**

- **核心原則**：
    - 最大化 commonMain 程式碼共享
    - 平台層極薄（desktopMain / wasmJsMain 只負責啟動）
    - 單一畫面應用（無複雜導航）
    - 可測試性優先
    - 漸進模組化（Sprint 0-1 單 module；之後拆分）
- **技術選型**（v1.0）：
    - UI：Compose Multiplatform 1.7.x+
    - 網路：Ktor Client 3.x
    - JSON：kotlinx-serialization 1.7.x+
    - 日期：kotlinx-datetime 0.6.x+
    - 依賴注入：暫無（保持簡單）
    - 圖表：待定（Sprint 1 決定）
    - 離線存儲：v1.0 無需
- **檔案組織**（初期）：

```
commonMain/kotlin/com/neojou/currencyviewer/
├── App.kt
├── ui/
├── data/
├── domain/
└── platform/
```


***

### 🔄 **工作流程（Issue → PR → Merge → Handoff）**

1. **開 Issue**：用 template，填充 Goal / Scope / AC / DoD / Verification
2. **PM 指派**：用 `角色名稱:` 指定主責角色（Role label）
3. **執行並交付**：遵守 Work Request / Review Notes / Handoff 格式
4. **PR 審查**：依 Review Notes（Must-fix / Should / Nice-to-have）
5. **Merge**：PR 合併進 main
6. **Handoff**：在 Issue comment 填充 Handoff 模板（SSoT = Single Source of Truth）

***

### ✅ **當前協作規範要點**

| 項目 | 規則 |
| :-- | :-- |
| **角色切換** | 必須用 `角色名稱:` 明確指定；不自行切換 |
| **越權禁止** | 每張 Issue 只能有一個「主責角色」；協作用 co-owner label |
| **文件優先序** | 遇到衝突時：Owner 指示 > tasks.md > ADR > 其他 |
| **Decision** | 政策/長期決策必須寫 ADR（docs/decisions/ADR-XXXX-*） |
| **交接 SSoT** | Handoff 必填在「Issue comment」中（最容易追蹤） |
| **驗收可重現** | 所有 Verification 必須能在新環境執行（指令/步驟清楚） |
| **無依賴聊天** | 交付物一律落在 repo；聊天紀錄只作通知用 |


***

### 🚀 **後續如何開始協作**

1. **確認已讀**：你已讀完 roles.md、communication.md、requirements.md、architecture.md
2. **系統初始化**：下次新聊天時貼上 ai-team.md 的「系統啟動模板」
3. **指定角色**：用 `角色名稱:` 開始指派工作（例如 `PM:` 或 `Coder:` 等）
4. **嚴格模式**：AI 層級嚴格遵守角色職責、不越權、參照 repo 文件優先序

***

### 📌 **核心決策與待確認事項**

| 項目 | 狀態 | 備註 |
| :-- | :-- | :-- |
| 圖表庫選型 | ⏳ 待決 | Sprint 1 再決定（Compose Charts？） |
| GitHub Issue Templates | ⏳ TODO | ai-task.md、ai-process.md 尚未建立 |
| ADR 初版 | ⏳ TODO | docs/decisions/ 資料夾與首個 ADR 待建立 |
| tasks.md 細化 | ⏳ TODO | 里程碑與優先序詳細計畫待補 |
| Prompt Templates | ⏳ TODO | docs/prompt-templates/ 各角色專屬 prompt 待補 |


***

### 💡 **你現在可以做的事**

✅ **立即可執行**：

- 任何 PM 工作（拆 Issue、規劃里程碑、確認依賴）
- 任何 Architect 工作（審視架構、補 ADR、code review）
- 任何 Coder 工作（實作功能、建立 PR）
- 任何 QE 工作（設計測試、驗收清單）
- 任何 Writer 工作（補文件、ADR、Issue templates）

✅ **下一步建議**：

1. Owner（你）明確第一個 Sprint 0 里程碑（哪些功能優先）
2. PM 拆開 GitHub Issues（最小化依賴、可並行執行）
3. Architect 補初版 ADR（圖表選型、HTTP 客戶端政策等）
4. 各角色依指派開始交付

***

**專案空間已建立！現在請用 `角色名稱:` 開始指派工作，或告訴我你想從何著手。**
<span style="display:none">[^1][^2][^3][^4][^5]</span>

<div align="center">⁂</div>

[^1]: ai-team.md

[^2]: communication.md

[^3]: roles.md

[^4]: requirements.md

[^5]: architecture.md

