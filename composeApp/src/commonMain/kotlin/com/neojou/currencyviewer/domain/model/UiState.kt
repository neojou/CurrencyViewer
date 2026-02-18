package com.neojou.currencyviewer.domain.model

/**
 * Sealed interface representing the possible UI states for any data-fetching operation.
 *
 * Common pattern in Compose Multiplatform:
 * - [Loading]: Data is being fetched
 * - [Ready]: Data is successfully available
 * - [Error]: Fetch failed with optional cause
 *
 * @param T The type of data when in [Ready] state.
 */
sealed interface UiState<out T> {

    /** Indicates that data is currently being loaded. */
    data object Loading : UiState<Nothing>

    /**
     * Indicates successful data retrieval.
     *
     * @param data The loaded data of type T.
     */
    data class Ready<T>(val data: T) : UiState<T>

    /**
     * Indicates that an error occurred during data retrieval.
     *
     * @param message User-friendly error message.
     * @param cause Optional original exception for debugging/logging.
     */
    data class Error(
        val message: String,
        val cause: Throwable? = null
    ) : UiState<Nothing>
}