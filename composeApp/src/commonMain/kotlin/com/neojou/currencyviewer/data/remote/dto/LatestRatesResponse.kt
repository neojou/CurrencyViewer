package com.neojou.currencyviewer.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * DTO for Frankfurter `/latest` endpoint response.
 *
 * Example JSON:
 * {"base":"USD","date":"2025-02-20","rates":{"JPY":150.25}}
 *
 * Note: [date] is kept as String; conversion to LocalDate is handled in mapper.
 */
@Serializable
data class LatestRatesResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
)

