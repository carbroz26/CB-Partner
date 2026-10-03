package com.carbroz.cbpartner.domain.repository

import com.carbroz.cbpartner.domain.model.dynamic.DynamicDestination
import com.carbroz.cbpartner.domain.model.dynamic.DynamicResponse

/**
 * Domain boundary for loading a Dynamic destination.
 *
 * The repository owns the remote-data flow boundary but does not know about
 * Ktor, JSON, DTOs, or the concrete remote data source.
 */
interface DynamicRepository {
    suspend fun fetch(
        destination: DynamicDestination,
        headers: Map<String, String> = emptyMap(),
        body: String? = null,
    ): Result<DynamicResponse>
}
