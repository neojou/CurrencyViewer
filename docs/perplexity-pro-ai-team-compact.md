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
<span style="display:none">[^1_1][^1_2][^1_3][^1_4][^1_5]</span>

<div align="center">⁂</div>

[^1_1]: ai-team.md

[^1_2]: communication.md

[^1_3]: roles.md

[^1_4]: requirements.md

[^1_5]: architecture.md


---

# 目前角色 DevOps 已併入 Writer, labels 如附件, 其他檔案的角色也都以此 github-labels.md 為主, 之後其他檔案也會做相對應的修改. ai-team.md 修改後如下： \# AI 團隊協作入口（必讀）

**專案：** CurrencyViewer（Kotlin Multiplatform / Compose Multiplatform）
**最後更新：** 2026-02-14
**版本：** 1.0

**目標：** 讓 AI 模擬多角色團隊（或未來真 multi-agent）能以 repo-native 方式協作，盡量不依賴聊天紀錄。

歡迎加入 AI 團隊！請按以下順序閱讀與操作。

---

## 1) 必讀核心文件（按順序）

1. **`docs/roles.md`**
→ 每個角色的 Mission、Responsibilities、Inputs/Outputs、DoD（團隊憲法；不得越權）
[前往文件 → roles.md](./roles.md)
2. **`docs/communication.md`**
→ Work Request / Review Notes / Handoff 模板 + Status / cross-cutting labels 規則（所有輸出都要遵守）
[前往文件 → communication.md](./communication.md)
3. **`docs/prompt-templates/`**（若已建立；若不存在 → TODO: 可另開 Issue 補齊）
→ 各角色專屬 prompt 模板（複製貼上即可開工）
[前往資料夾 → prompt-templates/](./prompt-templates/)
4. **產品 / 架構 / 任務文件**（以 repo 根目錄為準；若不存在 → TODO: 建立）
    - [requirements.md](../requirements.md)
    - [architecture.md](../architecture.md)
    - [tasks.md](../tasks.md)
5. **ADR（決策紀錄）**（若不存在 → TODO: 建立 `docs/decisions/`）
→ 所有架構/政策決策的單一真實來源（SSoT）
[前往資料夾 → decisions/](./decisions/)

> 原則：能用連結就不要複製內容到多個地方，避免版本漂移（single source of truth）。

---

## 2) 文件優先序（遇到衝突時）

若資訊彼此矛盾，優先序如下（由高到低）：

1. **人類 Owner 最新指示**（Issue / Comment / PR）
2. **`tasks.md`**（里程碑與優先序）
3. **`docs/decisions/*`**（ADR，已定案的決策）
4. **`architecture.md`**
5. **`requirements.md`**
6. **`docs/*`**（roles / communication / templates 等規範與模板）

> 若需要改變既有決策：以新增/更新 ADR 的方式處理，並在 Issue/PR 連回 ADR。

---

## 3) 5 分鐘上手（占位符）

如果你只想最快開始協作：

1. 先讀完：[`docs/roles.md`](./roles.md)、[`docs/communication.md`](./communication.md)。
2. 用「系統啟動模板」開新對話（見下方）。
3. 由人類 Owner 指派第一個角色與任務（例如 `PM:` 或 `Coder:`）。
4. 交付物一律落在 repo（Issue / PR / ADR / docs），不要只留在聊天紀錄。

---

## 4) 標準工作流程（Issue → PR → Merge → Handoff）

1. **開 Issue**：用 template（見下節），把 Goal / Scope / AC / DoD 寫清楚。
2. **執行**：由 `角色名稱:` 指派主責角色；輸出必須符合 `docs/communication.md`。
3. **開 PR**：把變更集中在 PR；PR description 填 `Closes #<issue>`（例如 `Closes #5`）。
4. **Review**：依 `docs/communication.md` 的 Review Notes（Must-fix / Should / Nice-to-have + Verification）。
5. **Merge**：合併進 main。
6. **Handoff**：依 `docs/communication.md` 的 Handoff 模板，貼在對應 Issue comment（SSoT）。

---

## 5) 角色名稱（canonical，固定拼法）

本 repo 僅承認以下角色名稱（短版）；請勿使用別名（例如 ProjectManager/QualityEngineer/TechnicalWriter 等）。

- `Owner:`（人類，不由 AI 扮演；AI 不得自稱 Owner）
- `PM:`
- `Architect:`
- `Coder:`
- `QE:`
- `Writer:`
- `Artist:`

---

## 6) Issue / PR 怎麼開（模板選擇）

請優先使用 GitHub templates 來標準化所需資訊（DoD / AC / 驗證方式）：

- **一般任務（功能 / bug / CI / 文件）**：`.github/ISSUE_TEMPLATE/ai-task.md`（若不存在 → TODO: 建立或先略過）
- **流程 / 規範 / 模板 / 角色 / ADR 政策類**：`.github/ISSUE_TEMPLATE/ai-process.md`（若不存在 → TODO: 建立或先略過）
- PR 一律套用 `.github/PULL_REQUEST_TEMPLATE.md`（若不存在 → TODO: 建立或先略過）

（檔案路徑，方便檢視）

- TODO（尚未建立）：`.github/ISSUE_TEMPLATE/ai-task.md`
- TODO（尚未建立）：`.github/ISSUE_TEMPLATE/ai-process.md`
- [PULL_REQUEST_TEMPLATE.md](../.github/PULL_REQUEST_TEMPLATE.md)

---

## 7) 怎麼交接（Handoff 規則）

- 任何跨角色協作（貼 `handoff` label）都必須用 `docs/communication.md` 的 Handoff 模板。
- **Handoff 的單一真實來源（SSoT）**：一律放在「對應的 Issue comment」中（最貼近脈絡、最容易追蹤）。
- `docs/handoffs.md`（若未來建立）只作「索引 / 彙總連結」用途：列出 Issue/PR 連結與一句話簡述，不要重貼整段 handoff，避免內容漂移。

---

## 8) ADR 指引（何時需要 ADR？）

符合下列任一情況，請開/更新 ADR（`docs/decisions/*`）並在 Issue/PR 連回：

- 需要改變既有決策或文件優先序
- 影響多平台/多模組的長期維護成本
- 引入新依賴/新資料來源/新政策（例如授權、隱私、爬取限制等）
- 團隊對同一問題反覆討論、無法靠 comment 達成一致

---

## 9) 系統啟動模板（每次新聊天必貼）

你可以直接複製此段到新聊天視窗最前面，然後用 `角色名稱:` 開始指派。

```text
你現在是我個人的 AI 軟體工程團隊，採用多角色分工協作模式。

請嚴格遵守 repo 內文件（永遠以此為準）：
- docs/roles.md（角色責任與 DoD）
- docs/communication.md（Work Request / Review Notes / Handoff、Status、Cross-cutting labels）
- requirements.md / architecture.md / tasks.md
- docs/decisions/*（ADR：決策單一真實來源）

規則：
1) 我會用「角色名稱:」來指定你現在應該扮演的角色，例如：
   PM:
   Architect:
   Coder:
   QE:
   Writer:
   Artist:
   請立刻切換成該角色，並以該角色的視角、責任、輸出格式回應。

2) 除非我明確用「角色名稱:」指定，否則你不得自行切換或扮演任何角色；
   請回覆：「請指定角色（例如 Architect:、Coder: 等）」

3) 每個回應都要保持該角色的語氣、責任範圍與輸出標準（Work Request / Review Notes / Handoff 等），嚴禁越權。

4) 所有建議必須參照上述 repo 文件；若文件衝突，依 docs/ai-team.md 的「文件優先序」處理；
   若需改變既有決策，請要求建立/更新 ADR，並連回對應 Issue/PR。

現在請確認你已理解以上規則，回覆「已理解，等待角色指定」。
```


## 10) 聯絡 Owner（人類）

所有最終決策、scope 取捨、AC 確認，由人類 Owner 拍板。

Owner：@neojou

開始吧：請先閱讀 docs/roles.md，然後用「角色名稱:」指定你的角色。

## 變更紀錄

2026-02-14 v1.0：初版建立（完成 Issue \#5）

已更新：從現在起以 `github-labels.md` 作為 labels 與角色對應的 **SSOT**，並以你貼出的新版 `ai-team.md`（DevOps 併入 Writer、canonical roles 移除 DevOps）作為最新協作入口規範。[^2_1]

## 目前有效的角色集合

- Canonical roles（Work Request / Review Notes / Handoff 文字內容使用）：`Owner / PM / Architect / Coder / QE / Writer / Artist`（已不含 DevOps）。[^2_1]
- `role:*` labels 用於篩選/路由/統計，其中 `role:writer` 的職責範圍已明確包含 repo operational 文件化與維護（CI/CD、部署、版本策略、可重現建置），等同吸收 DevOps 面向。[^2_1]


## labels 規則（之後開單/審查就照這套）

Open issue 的 label 數量規則（MUST）：Priority 恰好 1、Type 恰好 1、Role 至少 1、Status 0 或 1、Area 0..n、Cross-cutting 0..n。[^2_1]
Cross-cutting 的硬規則（MUST）：`decision` 必須連 ADR、`handoff` 完成時必須留 Handoff、`breaking-change` 必須描述破壞點與遷移/回退並補強 Verification。[^2_1]

## 舊文件的已知不一致點（待你後續逐步改）

- `communication.md` 仍列出 `DevOps:` 及相關範例（例如「PM → DevOps」），需要改為「PM → Writer」等，且 Work Request 模板的 Owner role 列表也需移除 DevOps。[^2_2][^2_1]
- `roles.md` 仍包含 DevOps 章節，且多處提到「Architect 對 Coder/DevOps/QE 可落地」之類文字；依你指示，後續應以 `github-labels.md` 的角色/labels 為準並同步修正。[^2_3][^2_1]
- 你貼的新版 `ai-team.md` 內容已移除 DevOps 並更新 canonical roles，這會與舊版附件 `ai-team.md` 產生差異；後續 repo 內文件更新時，以你貼的新版為準。[^2_4][^2_1]


## 我這邊的「後續討論上下文」會怎麼套用

- 任何你問到「要貼哪些 labels / role label / status / cross-cutting」：一律以 `github-labels.md` 為準。[^2_1]
- 任何你指派角色時：只接受 `PM: / Architect: / Coder: / QE: / Writer: / Artist:`（Owner 仍為人類）。[^2_1]
- 遇到 `communication.md`、`roles.md` 與 `github-labels.md` 衝突時：先照 `github-labels.md` 執行，並把「同步修文件」當成後續的 process/docs 工作項。[^2_3][^2_2][^2_1]

你希望我接下來直接幫你列一份「需要同步修改的檔案清單 + 具體修改點（逐條、可開 issue）」嗎？

<div align="center">⁂</div>

[^2_1]: github-labels.md

[^2_2]: communication.md

[^2_3]: roles.md

[^2_4]: ai-team.md

