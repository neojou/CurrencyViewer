package com.neojou.tools.web

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class HttpFetcherTest {

    @Test
    fun testFetchSuccess() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = "Hello, World!",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "text/plain")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
        }

        val fetcher = HttpFetcher(client)
        val result = fetcher.fetch("https://example.com/test")

        assertIs<FetchResult.Success<String>>(result)
        assertEquals(200, result.statusCode)
        assertEquals("Hello, World!", result.data)

        fetcher.close()
    }

    @Test
    fun testFetchJsonSuccess() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = """{"message": "Test OK"}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json()
            }
        }

        val fetcher = HttpFetcher(client)
        val jsonFetcher = HttpFetcherJson(fetcher)

        @Serializable
        data class TestResponse(val message: String)

        val result = jsonFetcher.fetchJson<TestResponse>("https://example.com/api")

        assertIs<FetchResult.Success<TestResponse>>(result)
        assertEquals(200, result.statusCode)
        assertEquals("Test OK", result.data.message)

        fetcher.close()
    }

    @Test
    fun testFetchError() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "Not Found",
                status = HttpStatusCode.NotFound
            )
        }

        val client = HttpClient(mockEngine)
        val fetcher = HttpFetcher(client)

        val result = fetcher.fetch("https://example.com/404")

        assertIs<FetchResult.Error>(result)
        assertIs<FetchResult.Error.HttpError>(result)
        assertEquals(404, (result as FetchResult.Error.HttpError).statusCode)

        fetcher.close()
    }
}
