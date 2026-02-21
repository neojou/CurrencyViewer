package com.neojou.tools.web

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import kotlinx.serialization.SerializationException

/**
 * 通用 HTTP JSON 抓取工具，專門處理 JSON 回應。
 * 與業務邏輯無關，可跨專案重用。
 *
 * 依賴已安裝 ContentNegotiation + kotlinx-serialization 的 HttpClient。
 * 若 client 未正確設定 JSON 序列化，會在 runtime 拋出 SerializationException。
 *
 * @param fetcher 已初始化的 HttpFetcher 實例，負責底層請求與錯誤包裝。
 */
class HttpFetcherJson(
    internal val fetcher: HttpFetcher) {

    /**
     * 對指定 URL 發出 HTTP GET 請求，並嘗試將回應解析為指定的型別 T。
     *
     * @param url 目標網址
     * @param headers 自訂 HTTP headers（選填）
     * @param parameters 自訂 query parameters（選填）
     * @return FetchResult<T>，成功時包含解析後的物件，失敗時包含錯誤資訊
     */
    internal suspend inline fun <reified T> fetchJson(  // ← 加 internal
        url: String,
        headers: Map<String, String> = emptyMap(),
        queryParameters: Map<String, String> = emptyMap()
    ): FetchResult<T> {
        return try {
            val response = fetcher.client.get(url) {
                headers.forEach { (key, value) -> header(key, value) }
                if (!headers.containsKey(HttpHeaders.Accept)) {
                    header(HttpHeaders.Accept, ContentType.Application.Json.toString())
                }
                url {
                    queryParameters.forEach { (k, v) ->
                        parameters.append(k, v)
                    }
                }
            }

            val status = response.status.value
            if (status in 200..299) {
                val body = response.body<T>()
                FetchResult.Success(status, body)
            } else {
                FetchResult.Error.HttpError(
                    statusCode = status,
                    message = "HTTP error: $status ${response.status.description}",
                    bodyText = response.bodyAsText()
                )
            }
        } catch (e: SerializationException) {
            FetchResult.Error.UnknownError(
                message = "JSON 解析失敗：${e.message}",
                cause = e
            )
        } catch (e: Exception) {
            FetchResult.Error.NetworkError(
                message = e.message ?: "網路請求失敗",
                cause = e
            )
        }
    }}
