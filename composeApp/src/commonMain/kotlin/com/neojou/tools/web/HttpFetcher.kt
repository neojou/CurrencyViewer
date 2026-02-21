package com.neojou.tools.web

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.sockets.SocketTimeoutException
import io.ktor.util.network.*

/**
 * 通用 HTTP 抓取工具，與業務邏輯無關，可跨專案重用。
 *
 * 使用 Ktor [HttpClient] 執行 GET 請求，並將結果包裝為 [FetchResult]。
 * 支援自訂 headers 與 query parameters。
 *
 * 生命週期：呼叫端負責在不再使用時呼叫 [close]。
 *
 * @param client 已設定完畢的 [HttpClient] 實例，由外部注入（例如透過 createHttpClient()）。
 */
class HttpFetcher(internal val client: HttpClient) {

    /**
     * 發出 HTTP GET 請求。
     *
     * @param url 完整 URL（含 scheme）
     * @param headers 自訂 headers（選填）
     * @param parameters 自訂 query parameters（選填）
     * @return FetchResult<String>，body 為原始文字
     */
    suspend fun fetch(
        url: String,
        headers: Map<String, String> = emptyMap(),
        queryParameters: Map<String, String> = emptyMap()
    ): FetchResult<String> = try {
        val response = client.get(url) {
            // 套用自訂 headers
            headers.forEach { (k, v) -> header(k, v) }
            // 預設 Accept（可被 headers 覆蓋）
            if (!headers.containsKey(HttpHeaders.Accept)) {
                header(HttpHeaders.Accept, "*/*")
            }
            // 套用 query parameters
            url {
                queryParameters.forEach { (k, v) ->
                    parameters.append(k, v)
                }
            }
        }

        val status = response.status.value
        val bodyText = response.bodyAsText()

        if (status in 200..299) {
            FetchResult.Success(status, bodyText)
        } else {
            FetchResult.Error.HttpError(
                statusCode = status,
                message = "HTTP ${response.status}",
                bodyText = bodyText.take(200) // 避免過長 log
            )
        }
    } catch (e: Throwable) {
        when (e) {
            is ConnectTimeoutException, is SocketTimeoutException ->
                FetchResult.Error.NetworkError("連線逾時：${e.message}", cause = e)
            is UnresolvedAddressException ->
                FetchResult.Error.NetworkError("無法解析網址：${e.message}", cause = e)
            else ->
                FetchResult.Error.UnknownError("未知錯誤：${e.message}", cause = e)
        }
    }

    fun close() = client.close()
}

/**
 * 統一的請求結果型別（建議獨立成 FetchResult.kt）
 */
sealed class FetchResult<out T> {
    data class Success<out T>(val statusCode: Int, val data: T) : FetchResult<T>()

    sealed class Error : FetchResult<Nothing>() {
        data class HttpError(
            val statusCode: Int,
            val message: String,
            val bodyText: String? = null
        ) : Error()

        data class NetworkError(
            val message: String,
            val cause: Throwable? = null
        ) : Error()

        data class UnknownError(
            val message: String,
            val cause: Throwable? = null
        ) : Error()
    }
}