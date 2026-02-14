## Prerequisites

- JDK: Java 25 (recommended distribution: Eclipse Temurin)
- Use Gradle Wrapper: always run commands via `./gradlew`

## Toolchain policy (SSOT)

This project intentionally moves forward with a newer toolchain baseline:

- Java 25
- Kotlin 2.3.x
- Gradle Wrapper 9.1.0

Note: Kotlin documents Gradle 9.0.0 as the maximum *fully supported* Gradle version for KGP 2.3.0, but newer Gradle versions may still work (with possible deprecation warnings or incomplete support).  
Team policy: we accept this risk; CI pins versions, and any warnings should be tracked via issues instead of being ignored.

See ADR: `docs/decisions/ADR-0001-build-toolchain-policy.md`

## Run (Desktop)

```bash
./gradlew :composeApp:desktopRun

```

## Build (Wasm)

```bash
./gradlew wasmJsBrowserDistribution
```

## Troubleshooting (Wasm networking)
Browser/Wasm requests may fail due to CORS, offline mode, or network restrictions.
When it fails, check the browser DevTools (Console + Network tab) and verify API accessibility from the browser context.


README 裡提到的「KGP 2.3.0 fully supported Gradle max = 9.0.0、但可用更新版本但可能有 warnings」同樣是 Kotlin 官方文件原句意涵。


