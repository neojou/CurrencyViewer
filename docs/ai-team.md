# AI 團隊協作入口（必讀）

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
- `DevOps:`
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
   DevOps:
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
2026-02-14 v1.0：初版建立（完成 Issue #5）