package com.carbroz.cbpartner.data.dynamic.remote

import com.carbroz.cbpartner.data.network.RemoteDataSource
import com.carbroz.cbpartner.data.network.RemoteResponse
import io.ktor.http.HttpMethod

/**
 * Dynamic-specific remote flow built on the common application RemoteDataSource.
 *
 * This class contains no HTTP client ownership and no duplicated network
 * configuration. Dynamic only supplies the backend-defined request details.
 */
class DynamicRemoteDataSource(
    private val remoteDataSource: RemoteDataSource,
) {
    suspend fun fetch(
        endpoint: String,
        method: String,
        headers: Map<String, String>,
        body: String?,
    ): RemoteResponse =
        remoteDataSource.execute(
            method = HttpMethod.parse(method),
            url = endpoint,
            headers = headers,
            body = body,
        )
}
