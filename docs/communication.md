# Communication（溝通與交接格式）

Last updated: 2026-02-13  
Scope: CurrencyViewer（Kotlin Multiplatform / Compose Multiplatform）

本文件定義團隊在 GitHub Issues / Pull Requests / 手動轉述（目前由老闆在 browser 轉交）時的統一訊息格式。  
目標：讓任何角色只看「Issue/PR 內容 + 連結的文件」就能開工；交接時不依賴聊天紀錄。

> 規範原則：格式要一致、內容要可驗收、並能追溯到單一真實來源（requirements / architecture / tasks / ADR）。

---

## 1) Work Request（交辦給某角色的工作單）

使用時機：
- 老闆/PM 指派任務給某角色
- 任何人開新 Issue（尤其是 P0/P1）
- 任何需要跨角色協作的工作（建議加上 `handoff` label）

模板：
```md
## Role / Owner
Owner role: <Owner / PM / Architect / Coder / Tester / QA / DevOps / Reviewer / Technical Writer / Artist>
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
<!-- 指令/步驟，務必可重現 -->
- [ ] `./gradlew build`
- [ ] Desktop: `<command>`
- [ ] Wasm: `<command>`
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
填寫指南（簡短）：

Context/Links 必填：確保後續能追溯決策與口徑。

DoD 是「專案層面/任務層面」完成定義；AC 是「需求層面」驗收條件（兩者不同）。[web:369]

Verification 要能在新環境重現，避免「我這邊可以」無法交接。

## 2) Review Notes（Reviewer 結構化回饋）
使用時機：

- Reviewer 對 PR 或 code snippet 的審查回饋
- 對架構/依賴/CI 等改動提出 gate（合併門檻）

模板：
```
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
<!-- Reviewer 覺得應該跑的驗證 -->
- [ ] `./gradlew build`
- [ ] Desktop: ...
- [ ] Wasm: ...
```
回饋原則：

- 優先指出「會造成 bug/維護災難」的 must-fix，再談 style。

- 若回饋涉及政策/取捨（例如 toolchain/CI 策略），建議升級成 ADR（decision）。

## 3) Handoff（跨角色交接）
使用時機：

- 一個角色完成工作，交棒給下一角色（例如 Reviewer → Technical Writer、PM → DevOps）

- PR 合併後，需要通知下一位接手者「下一步做什麼」

- 需要由老闆在 browser 手動轉述時：請用此格式貼在 Issue comment 中（最容易追蹤）

模板：

```
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
  - `./gradlew build`
  - Desktop: <command>
  - Wasm: <command>
- Manual steps:
  - ...

### Decisions / Constraints
- ...

### Risks & follow-ups
- Risk: ...
- Follow-up: <issue title suggestion + labels>

```

交接原則：

- 必須讓接手者不需要翻聊天：只靠「Where to look + How to verify + Decisions」就能繼續工作。[web:241]

- 若交接內容牽涉決策，請把決策落在 ADR，再由 handoff 連結 ADR。

## 4) ADR vs Meetings vs Handoff（何時用哪個）
- ADR：記「決策、替代方案、取捨、後果」，一決策一檔，長期可引用（決策日誌）。

- Meetings（minutes）：記「討論過程摘要 + 決議 + action items」，時間序追加。

- Handoff：記「工作交接」，讓下一角色能直接開始。

## 5) 範例（最小示範）
範例 A：Reviewer → Technical Writer（README/CI） 
   
- Decision（ADR）：docs/decisions/ADR-0001-build-toolchain-policy.md

- Handoff：在 Issue comment 填 Handoff（PR/檔案/驗證指令）

- Technical Writer 依 ADR + handoff 完成 README/CI commit

