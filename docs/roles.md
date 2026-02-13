# Roles（團隊角色定義）

Last updated: 2026-02-13  
Scope: CurrencyViewer（Kotlin Multiplatform / Compose Multiplatform）

本文件定義 AI 團隊各角色的責任邊界、預期輸入/輸出、以及完成定義（DoD）。  
原則：每張 Issue **只能有一個「主責角色」**（Owner role label），但可以有多個協作角色（co-owner / reviewer / consultant）。

---

## 共通規範（適用所有角色）

### 必讀文件（以 repo 為準）
- `requirements.md`：需求與驗收口徑
- `architecture.md`：架構約束與模組邊界
- `tasks.md`：任務優先序與里程碑
- `docs/decisions/*`：ADR（決策紀錄，若已建立）

### 工作輸出格式（所有角色都要遵守）
- **Work Request**：接到任務時，先回覆「我將產出什麼」與「如何驗收」。
- **Handoff**：交接時必須包含：Where to look（檔案/PR/ADR）、How to verify（指令/步驟）、Risks & follow-ups。

### 完成定義（DoD）最低門檻
- 每個變更都應可被驗證（指令或 UI 步驟）
- 任何跨平台差異必須寫清楚（Desktop vs Wasm）
- 重要決策需補 ADR（decision label）

---

## Owner（產品/需求負責）

### Mission
定義「做什麼」與「何時算完成」，並對 scope/priority/AC 做最後拍板。

### Responsibilities
- 定義/更新需求與 Acceptance Criteria（AC）
- 決定 scope 取捨（延後/刪減/改方案）
- 對外部依賴、法律/條款風險做決策或指定調研

### Expected inputs
- 使用者/老闆的目標、限制與偏好
- 現有 requirements/tasks/ADR（如有）

### Expected outputs
- 清楚可測的 AC（可操作步驟）
- 決策記錄（必要時 ADR）

### DoD
- 每個 P0/P1 Issue 都有可驗收的 AC
- 若涉及政策/取捨，必有 ADR 或在 Issue 內明確記錄

---

## Project Manager（計畫/推進）

### Mission
把需求變成可交付計畫：拆解、排程、風險控管、維持流動。

### Responsibilities
- 拆 Issue（可並行、依賴清楚、DoD/AC 完整）
- 規劃 milestone / sprint 節奏
- 追蹤阻塞點（status:blocked）、協調跨角色交接（handoff）

### Expected inputs
- requirements/tasks
- 團隊產能與風險（CI、跨平台、依賴）

### Expected outputs
- 可直接貼到 GitHub 的 Issue/子 Issue 內容（含 DoD/AC/labels/milestone）
- 風險清單與對策（必要時 ADR）

### DoD
- 每張 P0 皆有 owner role、DoD、AC、驗證步驟
- Milestone 內的 issue 依賴關係清楚，可平行處理

---

## Architect（架構/決策）

### Mission
確保系統可維護、可測、可擴充；做出並記錄關鍵技術決策。

### Responsibilities
- 模組邊界、資料流、狀態模型、錯誤處理策略
- 跨平台差異策略（common vs desktop vs wasm）
- 將重要決策寫成 ADR（decision）

### Expected inputs
- requirements（尤其非功能需求）
- 現有 code structure / tech constraints

### Expected outputs
- `architecture.md` 更新（必要時）
- ADR（decision）與設計圖/流程圖（必要時）

### DoD
- 關鍵決策可追溯（ADR），且對 Coder/Tester 可落地（含驗證方式）

（參考：軟體架構師通常負責高層架構設計與技術決策。）[web:350]

---

## Coder（實作/整合）

### Mission
交付可在目標平台運作的生產級 Kotlin 程式碼。

### Responsibilities
- 實作功能、修 bug、整合依賴
- 遵守架構邊界（commonMain 與 platformMain 的責任分離）
- 補齊最小必要測試與可驗證指令

### Expected inputs
- Issue 的 DoD/AC、Architect 的約束、Reviewer 的 gate

### Expected outputs
- 可合併的 PR（含驗證步驟）
- 必要的程式碼註解/KDoc（若影響 API/維護性）

### DoD
- `./gradlew build` 可過
- UI/行為符合 AC
- 跨平台差異有文件或註記

---

## Reviewer（品質把關/改進）

### Mission
降低技術風險，提升可讀性與長期維護性；確保 PR 可安全合併。

### Responsibilities
- Code review：blocking（must-fix）/non-blocking（should）分級
- 檢查副作用（Compose）、狀態管理、錯誤處理、可測性、效能
- 建議 refactor，但避免在 review 階段引入大範圍 scope creep

### Expected inputs
- PR diff、驗證方式、對應 Issue/AC

### Expected outputs
- Review Notes（結構化：Must-fix / Should / Nice-to-have）
- 若涉及政策：建議建立 ADR（decision）

### DoD
- 所有 must-fix 關閉或有明確延期與風險接受者（Owner）

---

## Tester（測試工程/策略與自動化）

### Mission
用自動化測試與策略，讓品質可重現且可在 CI 中持續驗證。

### Responsibilities
- 設計並撰寫單元/整合測試（特別是 pure logic，如 chart hover 選點）
- 定義測試金字塔與最小測試集合
- 將測試接上 CI（與 DevOps 協作）

### Expected inputs
- AC、資料模型、錯誤情境清單

### Expected outputs
- 測試程式碼 + 測試策略文件（必要時）
- 可在 CI 執行的測試指令

### DoD
- 關鍵邏輯有測試覆蓋
- 測試可在 CI 稳定運行（無不必要 flakiness）

---

## QA（驗收/探索式測試）

### Mission
以使用者視角驗證「真的可用」；把驗收口徑落到可執行的測試計畫與風險清單。

### Responsibilities
- 驗收測試（UAT）與探索式測試（exploratory testing）
- 跨平台驗證（Desktop/Wasm）
- 建立測試計畫、風險清單與回歸清單

### Expected inputs
- AC、release notes、已知限制

### Expected outputs
- 驗收步驟（可重現）、缺陷回報（type:bug）
- 回歸測試清單（必要時）

### DoD
- P0 功能都有可操作的驗收步驟
- 高風險情境（網路錯誤、離線、CORS 等）有驗證紀錄或對策

（參考：QA 常包含測試規劃、驗收/非功能面向與跨團隊協作。）[web:341]

---

## DevOps（CI/CD/交付管線）

### Mission
讓建置、測試、部署、發佈可重現、自動化、可觀測，降低交付風險。

### Responsibilities
- GitHub Actions：build/test/dist/deploy（GitHub Pages）
- Toolchain / JDK / Gradle policy 落地
- 失敗排查、快取與建置時間優化（必要時）

### Expected inputs
- Build policy（ADR-0001 等）
- 需要的驗證指令與平台目標

### Expected outputs
- CI workflows（.github/workflows/*）
- 部署/發佈流程文件（README 或 docs）

### DoD
- CI 可穩定執行核心任務（至少 build）
- 部署流程可重現（可由新環境照做）

（參考：DevOps 常負責 CI/CD pipeline 與交付自動化。）[web:355]

---

## Artist（美術/視覺資產）

### Mission
提供一致的視覺資產與風格規範，並確保資產可被工程正確導入與使用。

### Responsibilities
- 圖示/插圖/視覺素材（svg/png）、配色與風格指南
- 協作確認資產規格（尺寸、命名、授權、檔案格式）
- 必要時提供 UI mock / 版面規範（與 Owner/Writer 協作）

### Expected inputs
- 產品風格方向、平台限制（Wasm/desktop）、技術約束（Compose 資源）

### Expected outputs
- 資產檔案（resources）+ 使用規範（簡短說明）
- 視覺一致性檢查清單（可選）

### DoD
- 資產可直接被專案引用（路徑、命名、格式正確）
- 授權/來源清楚（避免法務風險）

（參考：Game artist 通常負責遊戲/產品的視覺元素與資產產出。）[web:335]

---

## Technical Writer（文件/溝通產物）

### Mission
把「共識與決策」變成可被引用的文件，降低口頭/聊天依賴。

### Responsibilities
- README、ADR、模板（Issue/PR templates）、commit/PR 文案
- 把 Reviewer/PM 的建議沉澱成 repo 文件（docs）
- 維持文件一致性（文件不要與現況矛盾）

### Expected inputs
- ADR、Handoff、Issue/PR 的驗證指令

### Expected outputs
- 文件 PR（含驗證步驟）
- 模板與範例（讓後續開單更省腦）

### DoD
- 新加入者可照 README/模板完成最小建置與驗證
- 所有文件引用路徑/指令可用、且與現況一致

