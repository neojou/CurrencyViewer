# Communication（溝通與交接格式）

Version: 1.0  
Last updated: 2026-02-14  
Scope: CurrencyViewer（Kotlin Multiplatform / Compose Multiplatform）

本文件定義團隊在 GitHub Issues / Pull Requests / 手動轉述（目前由人類 Owner 在 browser 轉交）時的統一訊息格式。  
目標：讓任何角色只看「Issue/PR 內容 + 連結的文件」就能開工；交接時不依賴聊天紀錄。

> 規範原則：格式要一致、內容要可驗收、並能追溯到單一真實來源（requirements / architecture / tasks / ADR）。

---

## 0) Canonical roles（固定拼法）

本 repo 僅承認以下角色名稱（短版）；請勿使用別名：

- `Owner:`（人類，不由 AI 扮演；AI 不得自稱 Owner）
- `PM:`
- `Architect:`
- `Coder:`
- `QE:`
- `DevOps:`
- `Writer:`
- `Artist:`

---

## 1) Work Request（交辦給某角色的工作單）

使用時機：
- Owner/PM 指派任務給某角色
- 任何人開新 Issue（尤其是 P0/P1）
- 任何需要跨角色協作的工作（建議加上 `handoff` label）

模板：
```md
## Role / Owner
Owner role: <Owner / PM / Architect / Coder / QE / DevOps / Writer / Artist>
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
<!-- Optional but recommended -->
- Current: <status:ready / status:blocked / status:in-progress / status:needs-review / status:needs-decision>
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
```text
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
<!-- Reviewer 覺得應該跑的驗證（依 PR 性質增減） -->
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
- 一個角色完成工作，交棒給下一角色（例如 QE → Writer、PM → DevOps）
- PR 合併後，需要通知下一位接手者「下一步做什麼」
- 需要由人類 Owner 在 browser 手動轉述時：請用此格式貼在 Issue comment 中（最容易追蹤）

模板：
```text
## Handoff
From role → To role: <role:a> → <role:b>

### What changed
- ...

### Where to look
- PR: <link>
- Files:
  - <path>
  - <path>
- ADR(s): docs/decisions/ADR-XXXX-...

### How to verify
- Commands:
  - `./gradlew build` (if applicable)
  - Desktop: <command> (if applicable)
  - Wasm: <command> (if applicable)
- Manual steps:
  - ...

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
Status labels 用來表示「這張工作目前在哪個流程階段」，讓團隊不用看板也能用 label 篩出：
- 目前可開工的清單（status:ready）
- 被阻塞的清單（status:blocked）
- 正在做的清單（status:in-progress）
- 等待審查的清單（status:needs-review）
- 等待決策的清單（status:needs-decision）

原則：
- 一張 open issue 最多只貼 1 個 status（避免衝突）。
- status 必須對應「下一個 owner / 下一個 action / 明確等待條件」，否則 status 會變成噪音。

狀態定義（含 entry / exit 規則）

```text
status:ready
Meaning: 需求/AC/DoD 已清楚、依賴已滿足，可立刻開始。
Entry: PM/Owner 補齊 Context、AC、Verification，並確認無阻塞。
Exit: 有人開始執行 → status:in-progress；若發現缺資訊/依賴 → status:blocked 或 status:needs-decision。

status:blocked
Meaning: 工作無法繼續，且有「單一且具體」的阻塞原因。
Entry: 執行者遇到依賴/缺資訊，必須在 issue 留下 Blocked: <原因>。
Exit: 阻塞解除 → status:ready 或 status:in-progress（視是否已有人接手）。

status:in-progress
Meaning: 有人正在主動處理（通常已有分支/PR 或明確下一步）。
Entry: 開始動工。
Exit: 提交 PR 等待審查 → status:needs-review；遇到依賴 → status:blocked。

status:needs-review
Meaning: 工作已完成到可檢查程度，等待 QE/Owner 驗收或審查。
Entry: PR ready for review（或明確指定 QE/Owner 驗收）。
Exit: 需要修改 → status:in-progress；審查通過且已合併/完成 → 關閉 issue（或 status:done 若你們選用）。

status:needs-decision
Meaning: 卡在方案選擇/政策取捨，需要 Owner/團隊拍板（建議產出 ADR 或在 issue 記錄決議）。
Entry: 出現不可逆決策點（例如 toolchain/CI policy/資料源取捨）。
Exit: 決策完成（最好有 ADR 連結）→ status:ready 或 status:in-progress。

status:done
Meaning: 已完成且不需再行動。
Note: 通常直接關閉 issue 即可，不一定需要此 label。
```

---

## 5) Cross-cutting labels（跨領域標記：貼標準則）
Cross-cutting labels 用來標記「跨模組/跨角色/跨流程」的特殊議題；這些標籤會影響 DoD/交接方式/風險控管。

原則：
- 這些標籤可以與 type/area/role/status 並存（它們是不同維度）。
- 貼上 cross-cutting label 後，Issue/PR 必須滿足對應的額外要求（見下）。

decision（會產生或更新ADR ）

何時貼：
- 需要在多個方案中選一個，且會影響後續長期維護/相容性/協作方式（例如 toolchain、CI policy、資料源策略、跨平台架構策略）。
- 同一類決策若不記錄，未來很可能重複討論。

貼了之後的 DoD 加碼：
- [ ] 必須新增或更新 ADR：docs/decisions/ADR-XXXX-*.md
- [ ] Issue/PR 內必須連結該 ADR（Context/Links）

handoff（需要跨角色交接）
何時貼：
- 這張工作完成後，下一步明確需要由「不同角色」接續（例如 QE → Writer、PM → DevOps）。
- 工作會拆成多階段、多 PR，需要明確交接點。

貼了之後的 DoD 加碼：
- Issue 或 PR 完成時必填 Handoff 區塊（Where to look / How to verify / Risks & follow-ups）
- Handoff 內必須指向「下一步的 owner role」與「下一張 issue/PR（若已存在）」

breaking-change（可能破壞相容性）
何時貼：
- 變更可能導致升級後無法編譯/無法建置/行為不相容（例如大幅調整 Gradle/toolchain、改 public API、改資料格式或 CLI/任務名稱）。
貼了之後的 DoD 加碼：
- 必須在 Issue/PR 描述「Breaking 的點」與「遷移/回退方式」
- 必須補強 Verification（至少 build + 相關平台驗證）
- 若是政策性改動，建議同時貼 decision 並補 ADR

good-first-issue（適合新加入者，可選）
何時貼：
- 範圍小、風險低、說明清楚、有明確驗收方式，且不需要深度領域知識即可完成。
- 建議同時確保 issue 具備足夠 Context/Links 與 Verification 指令。

---

## 6) ADR vs Meetings vs Handoff（何時用哪個）
ADR：記「決策、替代方案、取捨、後果」，一決策一檔，長期可引用（決策日誌）。

Meetings（minutes）：記「討論過程摘要 + 決議 + action items」，時間序追加。

Handoff：記「工作交接」，讓下一角色能直接開始。

---

## 7) 範例（最小示範，≤ 15 行/範例）
   Example A — Work Request（docs 任務）
```md
## Role / Owner
Owner role: Writer

## Goal (1-2 sentences)
新增 docs/communication.md v1.0（模板 + minimal examples），可直接 commit。

## Context / Links
- Related Issue: #7
- roles: docs/roles.md
- ai-team: docs/ai-team.md

## Deliverables (DoD)
- [ ] docs/communication.md v1.0

## Acceptance Criteria (AC)
- [ ] 三模板齊全且角色名皆 canonical
```

Example B — Review Notes（docs PR）
```md
## Review Summary
- Scope reviewed: PR #?
- Overall status: REQUEST CHANGES

## Must-fix (Blocking)
- [ ] 移除非 canonical 角色名（Reviewer/Technical Writer 等）

## Verification
- [ ] 人工檢查：搜尋關鍵字與連結是否為相對路徑
```

Example C — Handoff（QE → Writer）
```md
## Handoff
From role → To role: QE → Writer

### What changed
- PR #? 已 review，需修正角色名與補 minimal examples

### Where to look
- PR: <link>
- File: docs/communication.md

### How to verify
- Manual: 搜尋是否仍含非 canonical 角色名

### Risks & follow-ups
- Follow-up: 若要自動 link-check，可另開 issue
```

---

## Change log（文件變更紀錄）

- 2026-02-14 v1.0：初版建立（定稿 Work Request / Review Notes / Handoff 模板，status:* 與 cross-cutting labels 規則，並統一 canonical roles）。
