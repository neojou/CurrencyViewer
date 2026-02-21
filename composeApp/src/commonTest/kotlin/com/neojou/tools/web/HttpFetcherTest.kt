package com.neojou.tools.web

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
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

    @Test
    fun testFetchNetworkError() = runTest {
        val mockEngine = MockEngine { _ ->
            throw ConnectTimeoutException("Connection timed out")
        }

        val client = HttpClient(mockEngine)
        val fetcher = HttpFetcher(client)

        val result = fetcher.fetch("https://example.com/timeout")

        assertIs<FetchResult.Error.NetworkError>(result)
        assertTrue(result.message.contains("逾時") || result.message.contains("timed out"))
        assertNotNull(result.cause)
        assertIs<ConnectTimeoutException>(result.cause)

        fetcher.close()
    }

    @Test
    fun testFetchUnknownError() = runTest {
        val mockEngine = MockEngine { _ ->
            throw IllegalStateException("Unexpected internal error")
        }

        val client = HttpClient(mockEngine)
        val fetcher = HttpFetcher(client)

        val result = fetcher.fetch("https://example.com/crash")

        assertIs<FetchResult.Error.UnknownError>(result)
        assertTrue(result.message.contains("未知錯誤") || result.message.contains("Unexpected"))
        assertNotNull(result.cause)
        assertIs<IllegalStateException>(result.cause)

        fetcher.close()
    }
}
