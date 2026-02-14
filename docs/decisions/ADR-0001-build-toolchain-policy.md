# ADR-0001: Build & Toolchain Policy (Java 25 + Kotlin 2.3.0 + Gradle 9.1.0)

Status: Accepted  
Date: 2026-02-15  
Deciders: Owner (@neojou)  
Consulted: PM, Architect, Writer, QE  
Related:
- Issue: #9

## Context

本專案為 Kotlin Multiplatform / Compose Multiplatform 專案，需同時支援 Desktop 與 Wasm。  
我們需要一套「一致、可重現、可在 CI 固定」的 build toolchain baseline，讓協作者不會因本機環境差異導致無法建置或行為不一致。

Owner 的方向是：不要因為 Java 17 安穩而停留在舒適圈，採用較新的 LTS（Java 25）作為前進策略；遇到相容性問題以修正、升級與持續改善為主。  
同時，專案已採用 Kotlin 2.3.0（以及相關生態版本），需要將 toolchain policy 明確寫入 repo，避免日後反覆討論。

Kotlin 官方文件列出 KGP 2.3.0 的「最大 fully supported Gradle」為 9.0.0，但也允許使用更新 Gradle（可能出現 deprecation warnings 或部分新功能不完整）。  
本專案選擇 Gradle 9.1.0：接受「超出 fully-supported 上限」的風險，並以 CI 固定版本 + 警告追蹤的方式控管。

## Considered Options

### Option A: Java 17 (LTS) baseline
- Pros: 生態成熟、踩坑少。
- Cons: 偏保守，較不符合本專案「持續前進」的策略；且支援/授權與更新策略會隨發行版不同而有差異。

### Option B: Java 21 (LTS) baseline
- Pros: 相對新、成熟度高。
- Cons: 仍不符合 Owner 期望的前進速度；與既定的 Java 25 策略不一致。

### Option C: Java 25 (LTS) baseline
- Pros: 與 Owner 決策一致；為後續 2 年的專案演進提供較長的前瞻空間。
- Cons: 部分第三方工具或插件在新 JDK 上可能需要時間追上；需更嚴格的 CI 固定與排查指引。

Gradle versions considered:
- Gradle 9.0.0: Kotlin fully supported 上限（保守）
- Gradle 9.1.0: 專案目前使用且運作良好；Gradle 9.1.0 release notes 明示支援 Java 25（降低採用 Java 25 的 build 風險）

## Decision

採用以下 baseline 作為本專案 Build & Toolchain Policy（SSOT）：

- Java: 25
- Kotlin (KGP): 2.3.0
- Gradle Wrapper: 9.1.0

政策補充：
- 一律使用 Gradle Wrapper（`./gradlew`）建置，不要求使用者自行安裝 Gradle。
- CI 必須固定安裝相同 major JDK（Java 25），避免「本機能跑、CI 不能跑」或反之。
- 看到 deprecation warnings / 相容性警告：不要忽略，請建立 issue 追蹤（作為可管理的技術債），必要時以修正/升級方式處理。

## Consequences

Positive:
- 全隊（本機與 CI）有一致的 baseline，可重現、可交接。
- 可提早暴露新 toolchain 的相容性問題，避免長期累積到一次性爆炸式升級。

Negative / Costs:
- Gradle 9.1.0 超出 Kotlin fully supported 上限（9.0.0）時，可能遇到 deprecation warnings 或部分功能不完整。
- 部分第三方工具對 Java 25 的支援可能落後，需要較完善的排查指引與回退策略。

## Risks & Mitigations

Risk: Kotlin/Gradle 相容性警告累積，未來升級時變成 hard break。
- Mitigation:
  - CI 固定版本（JDK/Gradle/Kotlin），避免隱性漂移。
  - 任何 warning 視為可追蹤工作：開 i

