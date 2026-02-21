package com.neojou.tools.web

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class HttpFetcherJsonTest {

    @Test
    fun testFetchJsonSuccess_frankfurterLatest() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = """
                    {
                      "base": "EUR",
                      "date": "2025-02-20",
                      "rates": {
                        "USD": 1.085,
                        "JPY": 162.45,
                        "TWD": 34.78
                      }
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine)
        val jsonFetcher = HttpFetcherJson(client)

        @Serializable
        data class FrankfurterLatestDto(
            val base: String,
            val date: String,
            val rates: Map<String, Double>
        )

        val result = jsonFetcher.fetchJson<FrankfurterLatestDto>(
            url = "https://api.frankfurter.app/latest",
            queryParameters = mapOf("from" to "EUR", "to" to "USD,JPY,TWD")
        )

        assertIs<FetchResult.Success<FrankfurterLatestDto>>(result)
        assertEquals(200, result.statusCode)
        assertEquals("EUR", result.data.base)
        assertEquals("2025-02-20", result.data.date)
        assertEquals(1.085, result.data.rates["USD"])
        assertEquals(162.45, result.data.rates["JPY"])
        assertEquals(34.78, result.data.rates["TWD"])

        jsonFetcher.close()
    }

    @Test
    fun testFetchJsonSerializationError_invalidJson() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "{ invalid json here }",  // 故意壞掉的 JSON
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine)
        val jsonFetcher = HttpFetcherJson(client)

        @Serializable
        data class TestDto(val base: String)

        val result = jsonFetcher.fetchJson<TestDto>("https://api.frankfurter.app/latest")

        assertIs<FetchResult.Error.UnknownError>(result)
        assertTrue(
            result.message.contains("JSON 解析失敗") ||
                    result.message.contains("SerializationException")
        )
        assertNotNull(result.cause)
        assertTrue(result.cause is SerializationException)

        jsonFetcher.close()
    }

    @Test
    fun testFetchJsonHttpError_404() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "Not Found - The requested resource could not be found.",
                status = HttpStatusCode.NotFound,
                headers = headersOf(HttpHeaders.ContentType, "text/plain")
            )
        }

        val client = HttpClient(mockEngine)
        val jsonFetcher = HttpFetcherJson(client)

        @Serializable
        data class TestDto(val base: String)

        val result = jsonFetcher.fetchJson<TestDto>("https://api.frankfurter.app/invalid")

        assertIs<FetchResult.Error.HttpError>(result)
        assertEquals(404, result.statusCode)
        assertTrue(result.message.contains("HTTP") || result.message.contains("404"))
        result.bodyText?.let {
            assertTrue(it.contains("Not Found"))
        }

        jsonFetcher.close()
    }
}