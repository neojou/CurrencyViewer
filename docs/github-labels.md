# GitHub Labels 定義（SSOT）

Last updated: 2026-02-14  
Maintainers: role:pm, role:writer, role:architect  
Scope: CurrencyViewer

### 簡介
- 本文件為此 repo GitHub Labels 的**單一真實來源 SSOT**參考文件。  
- 所有 AI agent、Human Owner、Reviewer 在開 Issue / PR、挑選模板、貼標時，必須以此為準。  
- 若需新增/修改/刪除 label（名稱/語意/分類/色碼），請先建立 ADR，再同步更新「GitHub Labels 設定」與本文件。
- GitHub 的 Issue/PR 可透過 labels 進行追蹤與分類。

### 定義
- MUST / MUST NOT：硬性規則（違反視為流程缺陷，需要修正）。
- SHOULD：強烈建議；若不採用，MUST 在 issue 內留一句 `Exception reason: <one sentence>` 說明原因。
- MAY / OPTIONAL：可選；不影響 DoD。

### 命名規則
- Priority 使用 `P0/P1/P2`
- 其餘分類使用前綴：`type:* / area:* / role:* / status:*`
- Cross-cutting 使用獨立 labels：`decision / handoff / breaking-change / good-first-issue`，以及 sprint tag（例如 `sprint:process-0`）

### 角色名稱和標籤
- Issue/PR 文字內容（Work Request / Review Notes / Handoff）請使用 canonical roles（Owner/PM/Architect/Coder/QE/Writer/Artist）。
- `role:*` labels 用於「篩選/路由/統計」：表示此工作主要需要哪個角色介入、協作或驗收。

---

## 1) Priority（優先級）

| Name | Description | Color | 使用原則 |
|---|---|---|---|
| P0 | 必做；阻塞里程碑/交付 | #B60205 | SHOULD 只在「不做會阻塞 merge/交付/關鍵路徑」時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| P1 | 重要但不阻塞；本 Sprint 優先 | #FB8C00 | SHOULD 用於本 Sprint 希望完成但不會卡住其他工作者；若例外，請在 issue 留 `Exception reason: ...`。 |
| P2 | 可延後；改善/加值項 | #F9D423 | SHOULD 用於非阻塞、可進 backlog 的改善項；若例外，請在 issue 留 `Exception reason: ...`。 |

---

## 2) Type（類型）

| Name | Description | Color | 使用原則 |
|---|---|---|---|
| type:feature | 新增功能（使用者可感知） | #0366D6 | SHOULD 用於新增/改變 user-facing 行為或 UI 的交付；若例外，請在 issue 留 `Exception reason: ...`。 |
| type:bug | 錯誤修正（行為與預期不符） | #D73A4A | SHOULD 用於修正與需求/預期不符或 regression 的問題；若例外，請在 issue 留 `Exception reason: ...`。 |
| type:chore | 雜務/重構/清理（不改使用者功能） | #6A737D | SHOULD 用於 refactor/清理/內部調整且不改 user-facing 功能；若例外，請在 issue 留 `Exception reason: ...`。 |
| type:docs | 文件（README/ADR/規範/註解） | #5319E7 | SHOULD 用於主要交付物是文件或規範的工作；若例外，請在 issue 留 `Exception reason: ...`。 |
| type:ci | CI / GitHub Actions / 構建流程 | #0E8A8A | SHOULD 用於 pipeline、workflow、build/release、自動化與可重現環境；若例外，請在 issue 留 `Exception reason: ...`。 |
| type:test | 測試（單元/整合/快照/策略） | #1D76DB | SHOULD 用於新增/調整測試或測試策略的交付；若例外，請在 issue 留 `Exception reason: ...`。 |

---

## 3) Area（模組/關注領域）

| Name | Description | Color | 使用原則 |
|---|---|---|---|
| area:common | commonMain / 共用邏輯 | #C2E0C6 | SHOULD 當主要變更集中在 commonMain/共用層時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| area:desktop | desktopMain / Desktop 平台差異 | #BFDADC | SHOULD 當主要變更只影響 Desktop 平台差異時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| area:wasm | wasmJsMain / Web/Wasm 平台差異 | #D4C5F9 | SHOULD 當主要變更只影響 Web/Wasm 平台差異時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| area:build | Gradle / 版本 / toolchain / dependencies | #FEF2C0 | SHOULD 當主要變更在 Gradle/版本/toolchain/依賴策略時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| area:ui | Compose UI / layout / theme | #F9D0C4 | SHOULD 當主要變更在 UI/layout/theme/互動呈現時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| area:data | 資料層（API、Ktor、repository、序列化） | #BFE5BF | SHOULD 當主要變更在資料取得/解析/序列化/資料流時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| area:chart | 線圖/hover/互動 | #FFD8B5 | SHOULD 當主要變更在圖表渲染/hover/互動時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| area:docs-process | 協作流程（templates、handoff、ADR、meeting notes） | #E6E6E6 | SHOULD 當主要變更是流程/模板/ADR/協作治理時使用；若例外，請在 issue 留 `Exception reason: ...`。 |

---


## 4) Role（負責角色）

| Name | Brief                                                                                                                                | Description | 使用原則 | Color |
|---|--------------------------------------------------------------------------------------------------------------------------------------|---|---|---|
| role:owner | 人類決策                                                                                                                                 | 人類 Owner：最終決策與驗收口徑（AC）、scope/priority 拍板（非 AI agent） | SHOULD 當需要人類拍板或最終驗收口徑/手動操作時使用；若例外，請在 issue 留 `Exception reason: ...`。 | #BFD4F2 |
| role:pm | 任務管理 / 風險控管                                                                                                                          | 規劃/拆解/追蹤/風險控管；Owner 指示時可暫任 Orchestrator 決定下一個 handoff 對象 | SHOULD 當需要 triage、拆票、排程或風險控管/協作推進時使用；若例外，請在 issue 留 `Exception reason: ...`。 | #111111 |
| role:architect | 架構設計 / 程式碼審核 / 改善建議                                                                                                                  | 架構設計與技術決策（必要時 ADR/architecture.md）；收到 Review Request 時用 Review Notes 格式回應 | SHOULD 當涉及架構取捨、跨模組設計或需要 ADR 的決策審視時使用；若例外，請在 issue 留 `Exception reason: ...`。 | #4C2C92 |
| role:coder | 程式                                                                                                                                   | 生產級 Kotlin 實作與整合，落實 DoD/AC，提供可重現驗證方式 | SHOULD 當主要交付為程式實作/整合/修正，且需提供可重現驗證時使用；若例外，請在 issue 留 `Exception reason: ...`。 | #0052CC |
| role:writer | 文件 / 驗收                                                                                                                              | 文件與溝通產物：README、ADR、templates、commit/PR 文案（確保可交接/可重現/可驗收）；亦涵蓋 repo operational 文件化與維護（CI/CD、部署、版本策略、可重現建置） | SHOULD 當主要 owner/路由對象是 Writer，或工作以 docs/process/CI 的「可交接文字與規範產物」為主時使用（CI 類型仍 SHOULD 同時貼 `type:ci`）；若例外，請在 issue 留 `Exception reason: ...`。 | #8A63D2 |
| role:qe | 測試                                                                                                                                   | QE：自動化測試 + 探索式/驗收測試 + 回歸清單，確保品質可重現驗證（合併 Tester+QA） | SHOULD 當需要 QE gate、驗收/回歸清單或品質驗證策略時使用；若例外，請在 issue 留 `Exception reason: ...`。 | #2DA44E |
| role:artist | 美術資產/UIUX                                                                                                                            | 美術資產 + 基礎 UI/UX 建議；若無明確需求，優先產出資產而非程式碼 | SHOULD 當主要需要視覺資產或 UI/UX 建議輸出時使用；若例外，請在 issue 留 `Exception reason: ...`。 | #E99695 |

---

## 5) Status（流程狀態）

嚴格規則（MUST）
- Open issue MUST 各 labels 數量滿足：Priority（Exactly 1）、Type（Exactly 1）、Role（At least 1）、Status（0 or 1）、Area（0..n）、Cross-cutting（0..n）


| Name | Description | Color | 使用原則 |
|---|---|---|---|
| status:ready | 需求清楚，可開工 | #0E8A16 | SHOULD 當 AC/DoD/Verification 齊備且無阻塞時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| status:blocked | 被阻塞（缺資訊/依賴未完成） | #B60205 | SHOULD 只在有明確 blocker 時使用並填寫 `Blocked:`；若例外，請在 issue 留 `Exception reason: ...`。 |
| status:in-progress | 進行中 | #FBCA04 | SHOULD 當已有人接手且有分支/PR 或明確下一步時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| status:needs-review | 等待 Reviewer | #5319E7 | SHOULD 當 PR 已 ready、等待 QE/Owner/指定 reviewer 審查時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| status:needs-decision | 需要決策（老闆/團隊） | #D876E3 | SHOULD 當卡在不可逆取捨需要拍板時使用（建議同時貼 `decision`）；若例外，請在 issue 留 `Exception reason: ...`。 |
| status:done | 完成（可選；通常關 issue 即可） | #1A7F37 | SHOULD 只在你們保留 open issue 但要標完成的情境使用；若例外，請在 issue 留 `Exception reason: ...`。 |

---

## 6) Cross-cutting（跨領域標記）

嚴格規則（MUST）
- 若貼 `decision`，Issue/PR MUST 連結 ADR（或建立 ADR 的 work item 並連結）。
- 若貼 `handoff`，完成時 MUST 在 issue/PR comment 留 Handoff（含 From/To role、Where to look、How to verify、Risks & follow-ups）。
- 若貼 `breaking-change`，Issue/PR MUST 描述 Breaking 點，並 MUST 提供遷移/回退方式與補強 Verification。

| Name | Description | Color | 使用原則 |
|---|---|---|---|
| decision | 會產生或更新 ADR 的議題 | #D876E3 | SHOULD 用於需要長期可追溯決策的取捨；若例外，請在 issue 留 `Exception reason: ...`。 |
| handoff | 需要跨角色交接，必填 Handoff 區塊 | #A2EEEF | SHOULD 用於需要跨角色接力且不想依賴聊天紀錄的工作；若例外，請在 issue 留 `Exception reason: ...`。 |
| breaking-change | 可能破壞相容性（toolchain/Gradle 大升級等） | #FF0000 | SHOULD 用於可能導致編譯/建置/行為不相容的變更；若例外，請在 issue 留 `Exception reason: ...`。 |
| good-first-issue | 適合新加入者（可選） | #7057FF | SHOULD 僅在範圍小、風險低、AC/Verification 清楚且可獨立完成時使用；若例外，請在 issue 留 `Exception reason: ...`。 |
| sprint:process-0 | 流程基礎建置（對應 milestone Process-0；可選） | #C5DEF5 | SHOULD 僅在屬於 Process-0 範圍的流程基礎建置工作時使用；若例外，請在 issue 留 `Exception reason: ...`。 |

---

## 變更紀錄
2026-02-14 v1.0：初版建立（完成 Issue #12）
