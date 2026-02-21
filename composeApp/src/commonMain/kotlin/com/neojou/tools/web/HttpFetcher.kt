package com.neojou.tools.web

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

/**
 * 通用 HTTP 抓取工具，與業務邏輯無關，可跨專案重用。
 *
 * 使用 Ktor [HttpClient] 執行 GET 請求，並將結果包裝為
 * [FetchResult] sealed class，避免將 Ktor 例外直接暴露給上層。
 *
 * 生命週期：
 * 呼叫端負責在不再使用時呼叫 [close] 釋放底層連線資源。
 *
 * @param client 已設定完畢的 [HttpClient] 實例，由外部注入。
 * @see createHttpClient
 */
class HttpFetcher(private val client: HttpClient) {

    /**
     * 對指定 URL 發出 HTTP GET 請求並回傳結果。
     *
     * @param url 目標網址，需包含 scheme（`http://` 或 `https://`）。
     * @return [FetchResult.Success] 包含 HTTP 狀態碼與 body 字串；
     *   [FetchResult.Error] 包含錯誤訊息字串。
     */
    suspend fun fetch(url: String): FetchResult = try {
        val response = client.get(url)
        FetchResult.Success(statusCode = response.status.value, body = response.bodyAsText())
    } catch (e: Exception) {
        FetchResult.Error(message = e.message ?: "Unknown error")
    }

    /**
     * 釋放底層 [HttpClient] 持有的連線資源。
     * 應在 StateHolder 的 [com.neojou.webbrowser.WebBrowserStateHolder.close] 中一併呼叫。
     */
    fun close() = client.close()
}

/**
 * [HttpFetcher.fetch] 的回傳結果。
 *
 * 以 sealed class 封裝成功與失敗兩種狀態，
 * 避免將 Ktor 特定的例外型別暴露至上層。
 */
sealed class FetchResult {

    /**
     * 請求成功。
     *
     * @property statusCode HTTP 狀態碼（例如 200、301）。
     * @property body HTTP 回應的 body 字串（通常為原始 HTML）。
     */
    data class Success(val statusCode: Int, val body: String) : FetchResult()

    /**
     * 請求失敗（網路錯誤、逾時、DNS 失敗等）。
     *
     * @property message 描述錯誤原因的字串。
     */
    data class Error(val message: String) : FetchResult()
}
