package com.carbroz.cbpartner.data.bootstrap

import com.carbroz.cbpartner.domain.model.bootstrap.BootstrapFailure
import com.carbroz.cbpartner.domain.model.bootstrap.BootstrapOutput
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

internal class BootstrapRemoteDataSource(
    private val httpClient: HttpClient,
    private val json: Json = Json { ignoreUnknownKeys = true },
) {
    suspend fun fetch(): Result<BootstrapOutput> {
        return try {
            val response = httpClient.get(BASE_URL + BOOTSTRAP_ENDPOINT) {
                header("X-CarBroz-Platform", "ANDROID")
                header("X-CarBroz-App-Version", "1.0.0")
                header("X-CarBroz-Build-Number", "1")
            }

            if (response.status.value !in 200..299) {
                return Result.failure(BootstrapFailure.Http(response.status.value))
            }

            val dto = json.decodeFromString<BootstrapResponseDto>(response.bodyAsText())
            dto.toDomain()
        } catch (error: CancellationException) {
            throw error
        } catch (error: SerializationException) {
            Result.failure(BootstrapFailure.Serialization(error.message ?: "Unable to decode bootstrap response"))
        } catch (error: Throwable) {
            Result.failure(BootstrapFailure.Transport(error.message ?: "Unable to reach bootstrap service"))
        }
    }

    private companion object {
        const val BASE_URL = "https://localhost:300"
        const val BOOTSTRAP_ENDPOINT = "/api/v1/partner/config/bootstrap"
    }
}
