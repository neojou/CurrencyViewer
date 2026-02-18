package com.neojou.currencyviewer.domain.model

import kotlinx.datetime.LocalDate

/**
 * Represents the most recent (latest) exchange rate for a currency pair.
 *
 * @property date The date of the latest available rate.
 * @property rate The exchange rate value at that date.
 */
data class LatestRate(
    val date: LocalDate,
    val rate: Double
)