---
name: "AI Task"
about: "一般工作單（功能 / bug / 文件 / CI / 重構）— 強制帶出 AC / DoD / Verification / Handoff"
title: "[task] "
---

## Role / Owner
Owner role: <PM / Architect / Coder / QE / Writer / Artist>
Co-owners (optional): <role:...>

> Labels（SSOT）：請依 `docs/github-labels.md` 選擇並貼上對應 labels（P0/P1、type:*、area:*、role:*、status:*、handoff...）。  
> 角色切換只使用 canonical roles（例如 `Coder:`）；不要把 role labels 當成角色切換指令。

---

## Parent / Links
- Parent issue / Epic:
- Related issues / PRs:
- requirements.md:
- architecture.md:
- tasks.md:
- ADR(s) (if any): `docs/decisions/ADR-XXXX-...`

## Goal (1–2 sentences)
<!-- 可驗收、可觀察，不要只寫「改善/優化」 -->

## Scope
- In scope:
- Out of scope:

## Non-goals
- ...

## Context / Notes
<!-- 背景、已知限制、假設、參考資料 -->
- ...

## Proposal / Plan (v0)
<!-- 3–7 bullets，具體到檔案/模組/步驟 -->
- ...

## Deliverables (DoD)
- [ ] Code / docs changes are committed (paths listed below).
- [ ] PR includes summary + verification steps.
- [ ] Handoff is prepared (if handoff label is used).

### Deliverable paths
- `<path>`
- `<path>`

## Acceptance Criteria (AC)
<!-- 用「我怎麼操作 / 看到什麼結果」描述 -->
- [ ] ...

## Platform notes (if applicable)
- Desktop:
- Wasm/Web:

## Verification (How to verify)
> 請至少列出你「實際跑過/做過」的步驟；若不適用請刪除或註明 N/A。

- [ ] `./gradlew build` (if applicable)
- [ ] Desktop: `<command>` (if applicable)
- [ ] Wasm: `<command>` (if applicable)
- [ ] Manual steps:
    - [ ] ...

## Risks / Follow-ups
- Risk:
- Follow-up issue suggestion:

## Handoff (when done)
From role → To role: <role:a> → <role:b>
- What changed:
- Where to look (files/PR/ADR):
- How to verify:
- Risks & follow-ups:
