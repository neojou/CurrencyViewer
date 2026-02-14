# Communication（溝通與交接格式）

Version: 1.1  
Last updated: 2026-02-15  
Scope: CurrencyViewer（Kotlin Multiplatform / Compose Multiplatform）

本文件定義團隊在 GitHub Issues / Pull Requests / 手動轉述（目前由人類 Owner 在 browser 轉交）時的統一訊息格式。  
目標：讓任何角色只看「Issue/PR 內容 + 連結的文件」就能開工；交接時不依賴聊天紀錄。

> 規範原則：格式要一致、內容要可驗收、並能追溯到單一真實來源（requirements / architecture / tasks / ADR）。

---

## GitHub Labels（SSOT）

GitHub labels 的名稱、描述、顏色、使用原則，**一律**以 `docs/github-labels.md` 為準（Single Source of Truth）。  
本文件只描述「何時要貼標、貼標對流程的意義」，不在此重複列出任何 label 定義清單。

若要新增、修改或廢除任何 label：
- 先更新 `docs/github-labels.md`（SSOT）。
- 若變更屬於長期政策/流程門檻調整，請依專案規範走 ADR 流程，並在 Issue/PR 連回對應 ADR。

---

## 0) Canonical roles（固定拼法）

本 repo 僅承認以下角色名稱（短版）；請勿使用別名：

- `Owner:`（人類，不由 AI 扮演；AI 不得自稱 Owner）
- `PM:`
- `Architect:`
- `Coder:`
- `QE:`
- `Writer:`
- `Artist:`

> Issue/PR 的 `role:*` labels 請依 `docs/github-labels.md` 貼標；本文件不重複定義 label 名單。

---

## 1) Work Request（交辦給某角色的工作單）

使用時機：
- Owner/PM 指派任務給某角色
- 任何人開新 Issue（尤其是高優先級事項）
- 任何需要跨角色協作的工作（若需要跨角色交接，請依 `docs/github-labels.md` 貼上對應標記）

模板：
```md
## Role / Owner
Owner role: <Owner / PM / Architect / Coder / QE / Writer / Artist>
Co-owners (optional): <role:...>

## Goal (1-2 sentences)
<!-- 你想達成什麼？用「可驗收」描述，不要只寫「改善/優化」。 -->

## Context / Links
<!-- 至少 1 個連結；越精準越好 -->
- requirements.md:
- architecture.md:
- tasks.md:
- ADR(s): docs/decisions/ADR-XXXX-...
- Related Issue/PR:

## Status (workflow)
<!-- Optional but recommended; status:* labels 以 docs/github-labels.md 為準 -->
- Current: <status:...>
- Blocker (if blocked): <one clear reason, e.g. "Blocked: waiting for ADR-0001 decision">

## Scope
<!-- 要做什麼（in scope） -->
- ...

## Non-goals
<!-- 明確列出不做什麼，避免 scope creep -->
- ...

## Proposal / Plan (v0)
<!-- 3-7 bullets，具體到檔案/模組/任務順序 -->
- ...

## Deliverables (DoD)
<!-- 交付物清單：檔案/功能/工作流，能打勾 -->
- [ ] ...
- [ ] ...

## Acceptance Criteria (AC)
<!-- 驗收條件：用「我怎麼點/怎麼跑/看到什麼」來描述 -->
- [ ] ...

## Verification (How to verify)
<!-- 指令/步驟，務必可重現；依任務性質增減 -->
- [ ] `./gradlew build` (if applicable)
- [ ] Desktop: `<command>` (if applicable)
- [ ] Wasm: `<command>` (if applicable)
- [ ] Manual steps: ...

## Risks / Notes
<!-- 風險、未知點、依賴 -->
- ...

## Handoff (when done)
<!-- 完成時必填，交接給下一角色（可留空白當占位） -->
From role → To role:
- What changed:
- Where to look (files/PR/ADR):
- How to verify:
- Risks & follow-ups:
```

### 填寫指南（簡短）：
- Context/Links 建議必填：確保後續能追溯決策與口徑。
- DoD 是「任務完成定義（交付物）」；AC 是「需求驗收條件」（兩者不同，避免混用）。
- Verification 要能在新環境重現，避免「我這邊可以」無法交接。

---

## 2) Review Notes（QE 結構化回饋）
使用時機：
- QE 對 PR 或 snippet 的審查回饋
- 對架構/依賴/CI 等改動提出 gate（合併門檻）

模板：
```md
## Review Summary
- Scope reviewed: <files/modules/PR link>
- Overall status: <APPROVE / REQUEST CHANGES / COMMENT>

## Must-fix (Blocking)
<!-- 不修不能合併；每條都要可操作 -->
- [ ] <problem> → <expected fix> (file:line or file path)

## Should (Non-blocking)
<!-- 建議改善；可在本 PR 或後續 issue 處理 -->
- [ ] ...

## Nice-to-have
<!-- 不急；通常可忽略或等有空再做 -->
- [ ] ...

## Risks / Follow-ups
<!-- 可能的 regression、跨平台差異、後續建議 -->
- Risk: ...
- Follow-up issue suggestion: <title + labels + milestone>

## Verification
<!-- QE 覺得應該跑的驗證（依 PR 性質增減） -->
- [ ] `./gradlew build` (if applicable)
- [ ] Desktop: ... (if applicable)
- [ ] Wasm: ... (if applicable)
- [ ] Manual steps: ...
```

### 回饋原則：
- 優先指出「會造成 bug/維護災難」的 Must-fix，再談 style。
- 若回饋涉及政策/取捨（例如 toolchain/CI 策略），建議升級成 ADR（貼 decision）。

---

## 3) Handoff（跨角色交接）
使用時機：
- 一個角色完成工作，交棒給下一角色（例如 PM → Writer、Coder → QE、QE → Writer）
- PR 合併後，需要通知下一位接手者「下一步做什麼」
- 需要由人類 Owner 在 browser 手動轉述時：請用此格式貼在 Issue comment 中（最容易追蹤）

模板：
```md
### Handoff
From role → To role: <role:a> → <role:b>

### What changed
- ...

### Where to look
- PR: <PR link>
- Files: <path>, <path>
- ADR(s): docs/decisions/ADR-XXXX-...

### How to verify
- Commands: `./gradlew build` (if applicable); Desktop: <command> (if applicable); Wasm: <command> (if applicable)
- Manual steps: ...

### Decisions / Constraints
- ...

### Risks & follow-ups
- Risk: ...
- Follow-up: <issue title suggestion + labels>
```

### 交接原則：
接手者不需要翻聊天：只靠 Where to look + How to verify + Decisions / Constraints 就能繼續工作。
若交接內容牽涉決策，請把決策落在 ADR，再由 handoff 連結 ADR。

---

## 4) Issue Status（workflow labels）
Status labels 用來表示「這張工作目前在哪個流程階段」，讓團隊不用看板也能用 label 篩出清單。
status:* 的名稱與使用原則請一律依 docs/github-labels.md；本文件不列出/不重複任何 status:* 清單或逐條定義。

原則（與 SSOT 一致時，以 SSOT 為準）：

- 一張 open issue 最多只貼 1 個 status（避免衝突）。

- status 應對應「下一個 action / 明確等待條件」，否則 status 會變成噪音。

- 若是 blocked，務必在 issue 留下 Blocked: <單一且具體原因>，並在解除後更新 status。

---

## 5) Cross-cutting labels（跨領域標記：貼標準則）
Cross-cutting labels 用來標記「跨模組/跨角色/跨流程」的特殊議題，可能影響 DoD、交接方式與風險控管。

cross-cutting labels 的名稱與額外要求請一律依 docs/github-labels.md；本文件不重複列出任何 cross-cutting label 定義清單。

建議做法：

- 若貼了任何 cross-cutting label，請在 Work Request 的 DoD / Verification / Handoff 補上對應的額外資訊（依 SSOT 規則）。

- 若牽涉長期政策或不可逆決策，優先把決策落在 ADR，再由 Issue/PR 與 Handoff 連回。



---

## 6) ADR vs Meetings vs Handoff（何時用哪個）
ADR：記「決策、替代方案、取捨、後果」，一決策一檔，長期可引用（決策日誌）。

Meetings（minutes）：記「討論過程摘要 + 決議 + action items」，時間序追加。

Handoff：記「工作交接」，讓下一角色能直接開始。

---

## 7) 範例（最小示範，≤ 15 行/範例）
<...> 代表占位符；請在使用時替換成實際值（例如 <PR link>、<Issue #>）。

   Example A — Work Request（docs 任務）
```md
## Role / Owner
Owner role: Writer

## Goal (1-2 sentences)
更新 docs/communication.md：移除內嵌 labels 定義，改引用 docs/github-labels.md（SSOT）。

## Context / Links
- Related Issue: #14
- labels SSOT: docs/github-labels.md

## Deliverables (DoD)
- [ ] docs/communication.md 更新

## Acceptance Criteria (AC)
- [ ] 文件不再包含任何 labels 清單定義
```

Example B — Review Notes（docs PR）
```md
### Review Summary
- Scope reviewed: <PR link>
- Overall status: REQUEST CHANGES

## Must-fix (Blocking)
- [ ] 移除非 canonical 角色名（如 Reviewer/Technical Writer 等）

## Verification
- [ ] 人工檢查：搜尋關鍵字與連結是否為相對路徑
```

Example C — Handoff（QE → Writer）
```md
## Handoff
From role → To role: QE → Writer

### What changed
- <PR link> 已 review，需修正範例一致性（code fence / placeholders）

### Where to look
- PR: <PR link>
- File: docs/communication.md

### How to verify
- Manual: 搜尋是否仍含非 canonical 角色名

### Risks & follow-ups
- Follow-up: 若要自動 link-check，可另開 issue
```

---

## Change log（文件變更紀錄）
- 2026-02-15 v1.1：移除文件內嵌的 labels 定義清單，改以 docs/github-labels.md 作為 labels SSOT；同步移除 DevOps 角色並對齊最新 canonical roles。

- 2026-02-14 v1.0：初版建立（定稿 Work Request / Review Notes / Handoff 模板，並統一 canonical roles）。

