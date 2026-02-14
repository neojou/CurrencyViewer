---
name: "AI Process"
about: "流程 / 規範 / 決策 / 模板 / taxonomy 調整（對齊 docs 與 SSOT）"
title: "[process] "
---

## Role / Owner
Owner role: Writer
Co-owners (optional): role:pm, role:architect, role:qe

> Labels（SSOT）：請依 `docs/github-labels.md` 選擇並貼上對應 labels（P0/P1、type:*、area:*、role:*、status:*、handoff、decision...）。  
> 注意：role labels 用於分類/路由，不是角色切換指令；角色切換只使用 canonical roles（例如 `Writer:`）。

---

## Parent / Links
- Parent issue / Epic:
- Related issues / PRs:
- ADR(s) (if any): `docs/decisions/ADR-XXXX-...`

## Goal (1–2 sentences)
<!-- 這個流程/規範變更要達成什麼？用可驗收的方式描述 -->

## Context / Motivation
<!-- 背景、痛點、為何現在要改 -->
- Problem:
- Why now:
- Constraints / Non-goals:

## Scope
<!-- 影響範圍（in scope） -->
- In scope:
- Out of scope:

## Proposal (v0)
<!-- 具體提案：3–7 bullets，指到檔案/規則/流程 -->
- ...

## Alternatives considered (required)
<!-- 至少列 1–3 個替代方案與取捨，避免同一題重複討論 -->
- Option A:
- Option B:
- Option C (optional):

## Impact / Migration
<!-- 這次變更會影響哪些既有文件/模板/工作習慣？如何遷移？ -->
- Who is affected:
- What needs updating (files / templates):
- Migration steps:
- Backward compatibility:
- Deprecation plan (if any):

## Deliverables (DoD)
- [ ] Spec / rules are written in repo docs (SSoT is clear; avoid duplicated truth).
- [ ] Templates (if any) updated to match docs.
- [ ] Verification steps are runnable/repeatable.
- [ ] Handoff is filled (SSoT in Issue comment when done).

## Acceptance Criteria (AC)
- [ ] A new contributor can follow the updated process without chat context.
- [ ] No conflicting definitions across docs (SSOT points to exactly one place).
- [ ] Any required ADR is created/updated and linked.

## Status / Blocked by
- Current status (choose from `docs/github-labels.md`): status:...
- Blocked: (leave empty if not blocked)
  - Blocked: <single concrete reason>

## Estimated effort
- S / M / L (or minutes/hours):

## Verification (How to verify)
- [ ] GitHub renders Markdown correctly (headings, code fences, checklists).
- [ ] Repo-wide search confirms deprecated terms are removed (if applicable).
- [ ] Links referenced are valid (manual click-through is OK).

## Handoff (when done)
From role → To role: Writer → PM (optionally Architect/QE)
- What changed:
- Where to look (files/PR/ADR):
- How to verify:
- Risks & follow-ups:

