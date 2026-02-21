package com.neojou.currencyviewer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neojou.tools.LogLevel
import com.neojou.tools.MyLog
import com.neojou.tools.web.*

/**
 * Log tag used by [CurrencyViewer] for logging UI events.
 */
private const val TAG = "CurrencyViewer"

suspend fun test_web_access() {
    // 假設你已經有 HttpClient 實例（例如從 createHttpClient() 取得）
    val client = createHttpClient()  // 或你的 HttpClient 來源
    val fetcher = HttpFetcher(client)

    val TAG = "ExampleFetch"  // 建議根據實際情境定義 TAG

    try {
        val result = fetcher.fetch(
            url = "https://api.frankfurter.app/latest",
            // 可選：傳入自訂 headers 或 query parameters
            // headers = mapOf("Authorization" to "Bearer xxx"),
            // queryParameters = mapOf("base" to "EUR", "symbols" to "USD,JPY")
        )

        when (result) {
            is FetchResult.Success -> {
                val statusCode = result.statusCode
                val bodyText = result.data  // 這就是 response.bodyAsText() 的內容

                MyLog.add(TAG, "Status: $statusCode", LogLevel.DEBUG)
                // 注意：FetchResult 沒有直接保留 headers
                // 若需要 Content-Type 等 headers，需改用回傳 HttpResponse 的版本
                // MyLog.add(TAG, "Content-Type: ${response.headers["Content-Type"]}", LogLevel.DEBUG)

                MyLog.add(TAG, "Body text: $bodyText", LogLevel.DEBUG)
            }

            is FetchResult.Error.HttpError -> {
                MyLog.add(TAG, "HTTP Error: ${result.statusCode} - ${result.message}", LogLevel.DEBUG)
                result.bodyText?.let {
                    MyLog.add(TAG, "Response body snippet: $it", LogLevel.DEBUG)
                }
            }

            is FetchResult.Error.NetworkError -> {
                MyLog.add(TAG, "Network Error: ${result.message}", LogLevel.DEBUG)
                result.cause?.let { cause ->
                    MyLog.add(TAG, "Cause: ${cause.message}", LogLevel.DEBUG)
                    // 如果需要完整 stack trace，可視情況 log
                }
            }

            is FetchResult.Error.UnknownError -> {
                MyLog.add(TAG, "Unknown Error: ${result.message}", LogLevel.DEBUG)
                result.cause?.let { cause ->
                    MyLog.add(TAG, "Cause: ${cause.message}", LogLevel.DEBUG)
                }
            }
        }
    } finally {
        fetcher.close()  // 記得釋放資源
        MyLog.add(TAG, "Fetcher closed", LogLevel.DEBUG)
    }
}

/**
 * The primary UI screen for the CurrencyViewer application.
 *
 * Responsibilities (current stage):
 * - Logs a one-time "Enter" event when the screen first enters the Composition.
 * - Displays a centered placeholder UI for the upcoming data layer integration.
 * - Exposes a user action entry point ("Fetch Exchange Rates") for the next task.
 *
 * Note:
 * Logging is executed in [LaunchedEffect] to avoid being triggered on every recomposition.
 * Compose recommends using Effect APIs for side effects to ensure predictable execution timing. [web:99]
 */
@Composable
fun CurrencyViewer() {
    LaunchedEffect(Unit) {
        MyLog.add(TAG, "Enter", LogLevel.DEBUG)
        //test_web_access()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Currency Viewer",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Requirements.md status: Integrating Data Layer...",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    MyLog.add(TAG, "Fetch Exchange Rates clicked", LogLevel.DEBUG)
                    // TODO(Task 2): Trigger latest + time-series fetch.
                }
            ) {
                Text("Fetch Exchange Rates")
            }
        }
    }
}
