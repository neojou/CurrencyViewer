## Linked Issue
Closes #

> Labels（SSOT）：請依 `docs/github-labels.md` 貼上必要 labels（P0/P1、type:*、area:*、role:*、status:*、handoff...）。

## Summary
- 

## What changed
- 

## How to verify
> 請至少列出你實際執行過的指令與人工操作步驟；不適用請刪除或註明 N/A。

- [ ] `./gradlew build` (if applicable)
- [ ] Desktop: `./gradlew run` (if applicable; or `./gradlew :<module>:run`)
- [ ] Wasm: `./gradlew wasmJsBrowserDevelopmentRun` (if applicable; or `./gradlew :<module>:wasmJsBrowserDevelopmentRun`)
- [ ] Manual steps:
    - [ ] 

## Screenshots / Evidence (optional)
- Desktop:
- Web (GitHub Pages URL):

## Risks / Notes
- Risk:
- Mitigation / rollback:

## Checklist (DoD)
- [ ] Scope is reasonable and matches the linked issue goal.
- [ ] If applicable: Core logic lives in `commonMain`; `desktopMain`/`wasmJsMain` remain thin entrypoints (exceptions explained in Summary).
- [ ] Error handling: no uncaught exceptions that crash UI/process in expected scenarios.
- [ ] Cross-platform differences (Desktop vs Wasm) are documented in Summary (if any).
- [ ] Docs updated (README / requirements.md / tasks.md / architecture.md / docs/*) or explicitly marked N/A.
- [ ] Handoff section is filled if another role must act after merge.

## Handoff (after merge)
From role → To role: <role:a> → <role:b>

- What changed:
- Where to look (files/PR/ADR):
- How to verify:
- Risks & follow-ups:
