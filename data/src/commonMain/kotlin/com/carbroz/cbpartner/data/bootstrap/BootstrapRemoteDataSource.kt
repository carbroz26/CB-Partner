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
import co.touchlab.kermit.Logger

internal class BootstrapRemoteDataSource(
    private val httpClient: HttpClient,
    private val json: Json = Json { ignoreUnknownKeys = true },
) {
    private val logger = Logger.withTag("Bootstrap")

    suspend fun fetch(): Result<BootstrapOutput> {
        logger.i { "Requesting $BASE_URL$BOOTSTRAP_ENDPOINT" }
        return try {
            val response = httpClient.get(BASE_URL + BOOTSTRAP_ENDPOINT) {
                header("X-CarBroz-Platform", "ANDROID")
                header("X-CarBroz-App-Version", "1.0.0")
                header("X-CarBroz-Build-Number", "1")
            }

            logger.i { "Response HTTP ${response.status.value}" }

            if (response.status.value !in 200..299) {
                logger.e { "HTTP failure: ${response.status.value}" }
                return Result.failure(BootstrapFailure.Http(response.status.value))
            }

            val dto = json.decodeFromString<BootstrapResponseDto>(response.bodyAsText())
            logger.i { "Response parsed successfully" }
            dto.toDomain()
        } catch (error: CancellationException) {
            throw error
        } catch (error: SerializationException) {
            logger.e(error) { "Serialization failure: ${error.message}" }
            Result.failure(BootstrapFailure.Serialization(error.message ?: "Unable to decode bootstrap response"))
        } catch (error: Throwable) {
            logger.e(error) { "Transport failure: ${error.message}" }
            Result.failure(BootstrapFailure.Transport(error.message ?: "Unable to reach bootstrap service"))
        }
    }

    private companion object {
        const val BASE_URL = "http://localhost:3000"
        const val BOOTSTRAP_ENDPOINT = "/api/v1/partner/config/bootstrap"
    }
}
