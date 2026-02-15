<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" style="height:64px;margin-right:32px"/>

# 請先讀取底下資訊來建立觀念：閱讀完後請確認你已經了解底下規則, 回覆「已理解，等待角色指定」。

—
<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" style="height:64px;margin-right:32px"/>

# 建立一個專案空間：CurrencyViewer AI 團隊工作區。請分析我上傳的 ai-team.md 和其他所有附件的檔案，作為後續討論的上下文。

完美！我已成功讀取並分析了全部 8 份核心檔案。現在為你建立一份完整的**專案空間總結文檔**，作為後續協作的上下文基準。

***

# 🎯 **CurrencyViewer AI 團隊工作區 — 完整專案空間分析**

## 📋 **一覽表**

| 面向 | 內容 |
| :-- | :-- |
| **專案名稱** | CurrencyViewer（Kotlin Multiplatform / Compose Multiplatform） |
| **目標** | 跨平台應用（macOS Desktop + Web/Wasm）查看 USD/JPY 匯率與 30 天走勢圖 |
| **版本** | v1.6（單畫面、無刷新、無多幣別） |
| **平台** | macOS Desktop + GitHub Pages (Wasm) |
| **API 數據源** | Frankfurter API（client-side，免 key） |
| **技術棧** | Kotlin Multiplatform / Compose Multiplatform + Ktor + kotlinx-serialization |
| **Canonical Roles** | 7 個：Owner / PM / Architect / Coder / QE / Writer / Artist |
| **文件優先序** | Owner 指示 > tasks.md > ADR > architecture.md > requirements.md > docs/* |


***

## 📁 **核心文件架構與主要內容**

### **1️⃣ ai-team.md（協作入口）** ✅ 已更新 v1.0

- **功能**：AI 團隊協作的中樞；規定角色、文件優先序、工作流程
- **關鍵更新**：
    - 7 個 canonical roles（Owner / PM / Architect / Coder / QE / Writer / Artist）
    - 移除舊的 DevOps 角色，合併入 Writer（包含 CI/CD 與文件）
    - **文件優先序**：Owner 指示 > tasks.md > ADR > architecture.md > requirements.md > docs/*
    - 系統啟動模板（每次新聊天必貼）
    - 標準工作流：Issue → PR → Merge → Handoff

***

### **2️⃣ communication.md（溝通規範）** ✅ v1.1 已同步 DevOps → Writer

- **4 大模板**：
    - **Work Request**：Goal + Context + AC + DoD + Verification
    - **Review Notes**：Must-fix / Should / Nice-to-have + Verification
    - **Handoff**：What changed + Where to look + How to verify + Risks
    - **Issue Status Labels**：status:ready / blocked / in-progress / needs-review / needs-decision
- **Cross-cutting labels**：decision / handoff / breaking-change / good-first-issue / sprint:process-0
- **SSOT 指向**：所有 label 定義一律依 `docs/github-labels.md` 為準

***

### **3️⃣ roles.md（角色職責）** ✅ v1.1 已對齊 github-labels.md

- **7 個角色定義**（各含 Mission / Responsibilities / Inputs/Outputs / DoD）：
    - **Owner**（人類）：定義需求、決策、驗收；不由 AI 扮演
    - **PM**：拆解任務、排程、協調交接；可暫任 Orchestrator
    - **Architect**：技術決策、ADR、code review、架構一致性把關
    - **Coder**：實作、測試、交付 PR；遵守架構邊界
    - **QE**：自動化測試 + 探索式/驗收測試 + 回歸清單
    - **Writer**：文件、ADR、templates、**CI/CD、部署、可重現環境**（新增 DevOps 職能）
    - **Artist**：美術資產、UI/UX 建議；無明確需求優先產出資產而非程式碼
- **關鍵規則**：
    - 每張 Issue 只能有一個「主責角色」；協作用 co-owner 標記
    - 角色切換（文字）必須用 canonical roles；labels 用 `role:*` 作路由/統計
    - **Labels SSOT**：所有 labels 名稱、描述、顏色、使用原則一律參考 `docs/github-labels.md`

***

### **4️⃣ requirements.md（產品需求）** ✅ 詳細

- **核心需求**：
    - USD/JPY latest 匯率 + 日期（YYYY-MM-DD，UTC）
    - 最近 30 天日線線圖（X 軸為日期）
    - Hover tooltip：顯示最接近游標 x 位置的日期 + 匯率值
    - 無網路時不崩潰，顯示錯誤狀態
- **API 端點**：
    - Latest: `GET https://api.frankfurter.dev/v1/latest?base=USD&symbols=JPY`
    - Series: `GET https://api.frankfurter.dev/v1/{start}..{end}?base=USD&symbols=JPY`
- **非目標**：自動刷新、手動刷新、多幣別、日內線圖、新聞整合
- **AC 與驗收**：build 可過、行為符合、跨平台差異明確、無網路時不崩潰

***

### **5️⃣ architecture.md（技術架構）** ✅ 詳細

- **核心原則**：
    - 最大化 commonMain 程式碼共享
    - 平台層極薄（desktopMain / wasmJsMain 只負責啟動）
    - 單一畫面應用（無複雜導航）
    - 可測試性優先
    - 漸進模組化（Sprint 0–1 單 module；後續拆分）
- **技術選型**（v1.0）：
    - UI：Compose Multiplatform 1.7.x+
    - 網路：Ktor Client 3.x
    - JSON：kotlinx-serialization 1.7.x+
    - 日期：kotlinx-datetime 0.6.x+
    - 依賴注入：暫無（保持簡單）
    - **圖表**：待定（Sprint 1 決定）
    - 離線存儲：v1.0 無需
- **刻意避免**：Room / SQLDelight / Koin / Navigation / Voyager
- **檔案組織**（初期）：commonMain/kotlin/com/neojou/currencyviewer/

***

### **6️⃣ tasks.md（任務與里程碑）** ✅ v1.6 詳細

- **交付目標**：
    - Desktop（macOS）：latest + date + 30 天線圖 + hover tooltip + 錯誤狀態
    - Web（Wasm）：相同功能
    - 無刷新、無多幣別
- **Sprint 規劃**：
    - **Sprint 0**（1–2 天）：KMP 骨架 + GitHub Pages 部署 + 品質門檻
    - **Sprint 1**（3–5 天）：MVP 功能（domain model + API client + UI + chart + hover + error handling）
    - **Sprint 1.1**（1–2 天）：補強/修 bug
- **具體任務**：T0.1 ~ T0.3（腳手架），T1.1 ~ T1.6（MVP），T2.1 ~ T2.3（測試），T3.1 ~ T3.2（文件/發布）
- **標記規則**：Priority（P0/P1/P2）/ Size（S/M/L）/ Owner role / DoD / AC
- **Toolchain Policy**：Java 25 + Kotlin 2.3.0 + Gradle 9.1.0（Gradle 9.0.0 為最大完全支援）

***

### **7️⃣ github-labels.md（GitHub Labels SSOT）** ✅ v1.0 完整

- **SSOT 聲明**：本文件為 GitHub labels 的唯一真實來源；所有角色/流程皆以此為準
- **Priority**：P0 / P1 / P2
- **Type**：feature / bug / chore / docs / ci / test
- **Area**：common / desktop / wasm / build / ui / data / chart / docs-process
- **Role**：owner / pm / architect / coder / writer / qe / artist
- **Status**：ready / blocked / in-progress / needs-review / needs-decision / done
- **Cross-cutting**：decision / handoff / breaking-change / good-first-issue / sprint:process-0
- **嚴格規則**：
    - Open issue MUST：Priority ≥ 1 / Type = 1 / Role ≥ 1 / Status ≤ 1
    - 若貼 `decision`：MUST 連結 ADR
    - 若貼 `handoff`：MUST 在 issue comment 留 Handoff 格式
    - 若貼 `breaking-change`：MUST 描述破壞點 + 遷移方式

***

### **8️⃣ perplexity-pro-ai-team-compact.md（前期分析）** 📖

- 初期的專案空間分析文檔（可參考但已被本份新版總結取代）
- 包含「核心決策與待確認事項」清單

***

## 🔄 **標準工作流程（Issue → PR → Merge → Handoff）**

```
1. 開 Issue
   ├─ 用 template（ai-task.md 或 ai-process.md，未建立時先略過）
   ├─ 填充：Goal / Scope / AC / DoD / Verification
   ├─ 標籤：Priority（1） + Type（1） + Role（≥1） + Area（0..n） + Status（0–1） + Cross-cutting（0..n）
   └─ 指派主責角色

2. 執行
   ├─ Architect：可能先產出 ADR（若涉及決策）
   ├─ Coder / Writer 等：交付物符合 Work Request
   └─ 開 PR：填充 Closes #<issue>

3. Review
   ├─ Architect / QE：輸出 Review Notes（Must-fix / Should / Nice-to-have + Verification）
   └─ 修改 → re-review

4. Merge
   └─ PR 合併進 main

5. Handoff（若涉及跨角色交接）
   ├─ 貼 `handoff` label
   └─ 在 Issue comment 留 Handoff 格式（What changed + Where to look + How to verify + Risks）
```


***

## ✅ **當前協作規範要點**

| 項目 | 規則 |
| :-- | :-- |
| **角色切換** | 必須用 `角色名稱:` 明確指定；AI 不得自行切換 |
| **越權禁止** | 每張 Issue 只能有一個「主責角色」；協作用 co-owner label |
| **文件優先序** | Owner 指示 > tasks.md > ADR > architecture.md > requirements.md > docs/* |
| **Decision** | 政策/長期決策必須寫 ADR（docs/decisions/ADR-XXXX-*） |
| **交接 SSoT** | Handoff 必填在「Issue comment」中（最容易追蹤） |
| **驗收可重現** | 所有 Verification 必須能在新環境執行（指令/步驟清楚） |
| **無依賴聊天** | 交付物一律落在 repo（Issue / PR / ADR）；聊天紀錄只作通知用 |
| **Labels SSOT** | 所有 label 名稱、描述、色碼、使用原則一律依 `docs/github-labels.md` |


***

## 💡 **立即可執行的工作**

✅ **任何 PM 工作**：拆 Issue、規劃里程碑、確認依賴
✅ **任何 Architect 工作**：審視架構、補 ADR、code review
✅ **任何 Coder 工作**：實作功能、建立 PR
✅ **任何 QE 工作**：設計測試、驗收清單
✅ **任何 Writer 工作**：補文件、ADR、Issue templates、CI/CD 流程

***

## 📌 **核心決策與待確認事項**

| 項目 | 狀態 | 備註 |
| :-- | :-- | :-- |
| 圖表庫選型 | ⏳ 待決 | Sprint 1 再決定（Compose Charts？） |
| GitHub Issue Templates | ⏳ TODO | `.github/ISSUE_TEMPLATE/ai-task.md` / `ai-process.md` 尚未建立 |
| ADR 初版 | ⏳ TODO | `docs/decisions/` 資料夾與首個 ADR 待建立 |
| Prompt Templates | ⏳ TODO | `docs/prompt-templates/` 各角色專屬 prompt 待補 |
| GitHub Actions | ⏳ TODO | Build/Test/Deploy workflows 待建立 |


***

## 🚀 **下一步建議（優先序）**

1. **Owner 確認**：第一個 Sprint 0 里程碑（哪些功能優先）
2. **PM 拆 Issue**：把 tasks.md 的任務拆成 GitHub Issues（最小化依賴、可並行執行）
3. **Architect 補初版 ADR**：圖表選型、HTTP 客戶端政策、錯誤處理策略等
4. **各角色依指派開始交付**：遵守 Work Request / Review Notes / Handoff 格式

***

## 🎬 **開始協作**

**系統已初始化！**

現在請：

1. 若這是新聊天，複製貼上 ai-team.md 的「系統啟動模板」（見第 9 節）
2. 用 `角色名稱:` 開始指派工作（例如 `PM:`、`Architect:` 等）
3. 所有輸出必須符合 communication.md 的格式（Work Request / Review Notes / Handoff）

***

<div align="center">

### **CurrencyViewer AI 團隊工作區已建立！** 📦

**專案空間狀態：** ✅ 就緒
**協作規範版本：** v1.1（2026-02-15）
**Canonical Roles：** 7 個（Owner / PM / Architect / Coder / QE / Writer / Artist）
**文件優先序：** 已定義（SSoT）

***

**請指定角色開始工作** 👇

</div>
<span style="display:none">[^1_1][^1_2][^1_3][^1_4][^1_5][^1_6][^1_7][^1_8]</span>

<div align="center">⁂</div>

[^1_1]: communication.md

[^1_2]: ai-team.md

[^1_3]: perplexity-pro-ai-team-compact.md

[^1_4]: roles.md

[^1_5]: github-labels.md

[^1_6]: requirements.md

[^1_7]: tasks.md

[^1_8]: architecture.md

已理解，等待角色指定。[^1_1]
<span style="display:none">[^1_10][^1_2][^1_3][^1_4][^1_5][^1_6][^1_7][^1_8][^1_9]</span>

<div align="center">⁂</div>

[^1_1]: https://en.wikipedia.org/wiki/Single_source_of_truth

[^1_2]: https://www.mulesoft.com/resources/esb/what-is-single-source-of-truth-ssot

[^1_3]: https://www.atlassian.com/work-management/knowledge-sharing/documentation/building-a-single-source-of-truth-ssot-for-your-team

[^1_4]: https://document360.com/blog/single-source-of-truth/

[^1_5]: https://www.integrate.io/glossary/what-is-single-source-of-truth/

[^1_6]: https://skyvia.com/learn/single-source-of-true

[^1_7]: https://strapi.io/blog/what-is-single-source-of-truth

[^1_8]: https://www.talend.com/resources/single-source-truth/

[^1_9]: https://act-on.com/learn/blog/how-to-find-a-single-source-of-truth-for-marketing/

[^1_10]: https://www.perforce.com/blog/vcs/single-source-of-truth-examples-ssot


---

# Writer:

Writer: 我可以立刻把「可協作、可重現」所需的文件與 CI/CD 骨架補齊，讓後續所有工作都能用一致模板開 Issue、開 PR、部署到 GitHub Pages。[^2_1][^2_2]

## 我建議 Writer 先做的 3 件事

- 建立 GitHub Issue Templates：在 `.github/ISSUE_TEMPLATE/` 放入 `ai-task.md`、`ai-process.md`（GitHub 官方支援用該目錄集中管理多個 template，並以檔名排序；YAML 會排在 Markdown 前面）。[^2_3]
- 視需要改用 Issue Forms（YAML）：若你希望「必填欄位/驗證/預設 labels」可由表單強制，GitHub 提供 Issue forms 的 YAML 語法與欄位型別可用。[^2_4]
- 補 GitHub Pages 部署工作流：用 GitHub 官方 `actions/deploy-pages` 來把已上傳的 artifacts 部署到 Pages（符合「由 Actions 建置與部署」的 Pages 流程）。[^2_5][^2_6]


## 交付物（我會放進 repo）

- `.github/ISSUE_TEMPLATE/ai-task.md`、`.github/ISSUE_TEMPLATE/ai-process.md`（或改成 `.yml` issue forms 版本）。[^2_3][^2_4]
- `.github/pull_request_template.md`（讓 PR 描述、驗證步驟、handoff 一致）。[^2_1]
- `.github/workflows/pages.yml`（build/test → upload pages artifact → deploy pages）。[^2_2][^2_5]


## 我需要你指定的選項（擇一即可）

1) Issue template 要用 Markdown（自由填寫）還是 Issue Forms YAML（可強制必填/驗證/預設 labels）？[^2_4][^2_3]
2) Pages 部署要走官方 `deploy-pages`（artifact→deploy）還是你已有既定的 `gh-pages` 分支策略？[^2_6][^2_5]

你回覆「1) Markdown / YAML」+「2) deploy-pages / gh-pages」後，我就以 Writer 身分開始產出對應檔案與驗證步驟。
<span style="display:none">[^2_10][^2_11][^2_12][^2_13][^2_14][^2_15][^2_7][^2_8][^2_9]</span>

<div align="center">⁂</div>

[^2_1]: https://docs.github.com/en/communities/using-templates-to-encourage-useful-issues-and-pull-requests/about-issue-and-pull-request-templates

[^2_2]: https://resources.github.com/learn/pathways/automation/essentials/automating-deploying-workflows-with-github-actions/

[^2_3]: https://docs.github.com/en/communities/using-templates-to-encourage-useful-issues-and-pull-requests/configuring-issue-templates-for-your-repository

[^2_4]: https://docs.github.com/en/communities/using-templates-to-encourage-useful-issues-and-pull-requests/syntax-for-issue-forms

[^2_5]: https://github.com/actions/deploy-pages/tree/v4.0.5?tab=readme-ov-file

[^2_6]: https://github.blog/changelog/2021-12-16-github-pages-using-github-actions-for-builds-and-deployments-for-public-repositories/

[^2_7]: https://nijialin.com/2023/10/05/github-issue-report-yaml-template/

[^2_8]: https://stackoverflow.com/questions/46912624/is-it-possible-to-have-use-multiple-github-issue-templates-on-github

[^2_9]: https://leonardomontini.dev/github-issue-markdown-template/

[^2_10]: https://github.com/marketplace/actions/deploy-to-github-pages

[^2_11]: https://stackoverflow.com/questions/72079903/do-i-need-the-pages-build-deployment-github-action-when-i-have-another-action-f

[^2_12]: https://github.blog/developer-skills/github/issue-and-pull-request-templates/

[^2_13]: https://www.reddit.com/r/github/comments/1it0v2j/where_is_the_pages_build_and_deployment_workflow/

[^2_14]: https://github.com/github/docs/blob/main/content/communities/using-templates-to-encourage-useful-issues-and-pull-requests/configuring-issue-templates-for-your-repository.md

[^2_15]: https://github.com/JamesIves/github-pages-deploy-action


---

# 已有三個檔案如下, 請閱讀; 其他之後視情況再增加

閱讀理解後請回答：“OK, 已理解“

—-

PULL_REQUEST_TEMPLATE.md

## Linked Issue

Closes \#

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

From role → To role: [role:a](role:a) → [role:b](role:b)

- What changed:
- Where to look (files/PR/ADR):
- How to verify:
- Risks \& follow-ups:

—

ai-process.md

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
- Risks \& follow-ups:

—-

ai-task.md

---
name: "AI Task"
about: "一般工作單（功能 / bug / 文件 / CI / 重構）— 強制帶出 AC / DoD / Verification / Handoff"
title: "[task] "
---

## Role / Owner

Owner role: <PM / Architect / Coder / QE / Writer / Artist>
Co-owners (optional): [role:...](role:...)

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

From role → To role: [role:a](role:a) → [role:b](role:b)

- What changed:
- Where to look (files/PR/ADR):
- How to verify:
- Risks \& follow-ups:

OK, 已理解。[^3_1]
<span style="display:none">[^3_10][^3_2][^3_3][^3_4][^3_5][^3_6][^3_7][^3_8][^3_9]</span>

<div align="center">⁂</div>

[^3_1]: https://docs.github.com/en/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository

[^3_2]: https://stackoverflow.com/questions/52139192/github-pull-requests-template-not-showing

[^3_3]: https://docs.github.com/ja/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository

[^3_4]: https://docs.github.com/ko/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository

[^3_5]: https://docs.github.com/en/enterprise-server@3.0/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository

[^3_6]: https://docs.github.com/en/enterprise/2.15/user/articles/creating-a-pull-request-template-for-your-repository

[^3_7]: https://docs.github.com/en/enterprise-server@3.10/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository

[^3_8]: https://docs.github.com/en/enterprise-server@3.12/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository

[^3_9]: https://stackoverflow.com/questions/73771068/multiple-templates-for-pull-requests-on-github/73870512

[^3_10]: https://docs.github.com/pt/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository

