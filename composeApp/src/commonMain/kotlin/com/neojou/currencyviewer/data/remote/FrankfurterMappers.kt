package com.neojou.currencyviewer.data.remote.mapper

import com.neojou.currencyviewer.data.remote.dto.LatestRatesResponse
import com.neojou.currencyviewer.data.remote.dto.TimeSeriesResponse
import com.neojou.currencyviewer.domain.model.DailyRatePoint
import com.neojou.currencyviewer.domain.model.LatestRate
import kotlinx.datetime.LocalDate

/** v1.6 固定 quote currency，對齊 Frankfurter Mapping Policy（architecture.md）。 */
private const val QUOTE = "JPY"

/**
 * Maps [LatestRatesResponse] to domain model [LatestRate].
 *
 * @throws IllegalStateException if [QUOTE] currency is missing from [rates].
 */
fun LatestRatesResponse.toLatestRate(): LatestRate {
    val rate = rates[QUOTE]
        ?: error("Missing $QUOTE in rates (base=$base, date=$date)")
    return LatestRate(
        date = LocalDate.parse(date),
        rate = rate,
    )
}

/**
 * Maps [TimeSeriesResponse] to a list of [DailyRatePoint].
 *
 * - Days missing [QUOTE] are silently skipped (per Mapping Policy).
 * - Result is sorted by date ascending.
 */
fun TimeSeriesResponse.toDailyRatePoints(): List<DailyRatePoint> =
    rates.entries
        .mapNotNull { (dateStr, dayRates) ->
            val rate = dayRates[QUOTE] ?: return@mapNotNull null
            DailyRatePoint(
                date = LocalDate.parse(dateStr),
                rate = rate,
            )
        }
        .sortedBy { it.date }

