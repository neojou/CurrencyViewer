package com.neojou.currencyviewer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for Frankfurter `/timeseries` endpoint response.
 *
 * Example JSON:
 * {"start_date":"2025-01-20","end_date":"2025-01-22","base":"USD",
 *  "rates":{"2025-01-20":{"JPY":150.0},"2025-01-22":{"JPY":151.0}}}
 *
 * Note: date strings are kept as String; conversion to LocalDate is handled in mapper.
 */
@Serializable
data class TimeSeriesResponse(
    @SerialName("start_date") val startDate: String,
    @SerialName("end_date")   val endDate: String,
    val base: String,
    val rates: Map<String, Map<String, Double>>,
)

