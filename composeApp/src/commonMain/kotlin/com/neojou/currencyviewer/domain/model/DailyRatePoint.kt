package com.neojou.currencyviewer.domain.model

import kotlinx.datetime.LocalDate

/**
 * Represents a single historical daily exchange rate point for a currency pair.
 *
 * @property date The date when this rate was recorded.
 * @property rate The closing exchange rate for that day (base/quote).
 */
data class DailyRatePoint(
    val date: LocalDate,
    val rate: Double
)