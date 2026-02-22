package com.neojou.currencyviewer.data.remote

import com.neojou.currencyviewer.data.remote.dto.LatestRatesResponse
import com.neojou.currencyviewer.data.remote.dto.TimeSeriesResponse
import com.neojou.currencyviewer.data.remote.mapper.toDailyRatePoints
import com.neojou.currencyviewer.data.remote.mapper.toLatestRate
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class FrankfurterDtoMappingTest {

    private val json = Json { ignoreUnknownKeys = true }

    // ─── Fixtures ────────────────────────────────────────────────────────────

    private val latestNormal = """
        {"base":"USD","date":"2025-02-20","rates":{"JPY":150.25}}
    """.trimIndent()

    private val latestMissingJpy = """
        {"base":"USD","date":"2025-02-20","rates":{"EUR":0.92}}
    """.trimIndent()

    private val timeseriesWithMissingDay = """
        {
          "start_date": "2025-01-20",
          "end_date":   "2025-01-22",
          "base":       "USD",
          "rates": {
            "2025-01-22": {"JPY": 151.00},
            "2025-01-21": {"EUR": 0.91},
            "2025-01-20": {"JPY": 150.00}
          }
        }
    """.trimIndent()

    // ─── T1：latest 正常 decode + mapping ────────────────────────────────────

    @Test
    fun given_latest_normal_then_maps_to_correct_LatestRate() {
        val dto = json.decodeFromString<LatestRatesResponse>(latestNormal)
        val model = dto.toLatestRate()

        assertEquals(LocalDate.parse("2025-02-20"), model.date)
        assertEquals(150.25, model.rate)
    }

    // ─── T2：latest 缺 JPY → 丟出明確例外 ────────────────────────────────────

    @Test
    fun given_latest_missing_jpy_then_throws_with_message() {
        val dto = json.decodeFromString<LatestRatesResponse>(latestMissingJpy)

        val ex = assertFailsWith<IllegalStateException> {
            dto.toLatestRate()
        }
        // 確保錯誤訊息包含關鍵資訊，方便 debug
        assertTrue(
            ex.message?.contains("JPY") == true,
            "Error message should mention missing currency, was: ${ex.message}"
        )
    }

    // ─── T3：timeseries decode + mapping，缺 JPY 的日期被跳過，結果升冪排序 ──

    @Test
    fun given_timeseries_with_missing_day_then_skips_and_sorts_ascending() {
        val dto = json.decodeFromString<TimeSeriesResponse>(timeseriesWithMissingDay)
        val points = dto.toDailyRatePoints()

        // 2025-01-21 缺 JPY，應被跳過 → 只剩 2 筆
        assertEquals(2, points.size)

        // 升冪排序
        assertEquals(LocalDate.parse("2025-01-20"), points[0].date)
        assertEquals(150.00, points[0].rate)

        assertEquals(LocalDate.parse("2025-01-22"), points[1].date)
        assertEquals(151.00, points[1].rate)
    }
}

