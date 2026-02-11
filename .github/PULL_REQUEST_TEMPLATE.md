## Linked Issue
Closes #

## Summary
- 

## What changed
- 

## How to verify
> 請至少列出你實際執行過的指令與人工操作步驟。

- [ ] `./gradlew build`
- [ ] Desktop: `./gradlew run`（若實際 task 不在 root，請改用 `./gradlew :<module>:run`）
- [ ] Wasm: `./gradlew wasmJsBrowserDevelopmentRun`（若實際 task 不在 root，請改用 `./gradlew :<module>:wasmJsBrowserDevelopmentRun`）

## Screenshots / Evidence (optional)
- Desktop:
- Web (GitHub Pages URL):

## Checklist (DoD)
- [ ] 變更範圍合理，與 linked issue 目標一致
- [ ] 主要邏輯放在 `commonMain`；`desktopMain`/`wasmJsMain` 僅薄 entrypoint（如需例外，請在 Summary 說明原因）
- [ ] 錯誤處理：不會因未捕捉例外導致 UI/程序崩潰
- [ ] 若有跨平台差異（Desktop vs Wasm），已在 Summary 說明
- [ ] 已更新文件（README / requirements.md / tasks.md / architecture.md）或註明不需要更新的原因


