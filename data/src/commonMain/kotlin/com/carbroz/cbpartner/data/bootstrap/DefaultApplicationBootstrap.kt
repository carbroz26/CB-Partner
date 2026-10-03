package com.carbroz.cbpartner.data.bootstrap

import co.touchlab.kermit.Logger
import com.carbroz.cbpartner.data.network.RemoteDataSource
import com.carbroz.cbpartner.domain.bootstrap.ApplicationBootstrap
import com.carbroz.cbpartner.domain.model.bootstrap.BootstrapFailure
import com.carbroz.cbpartner.domain.model.bootstrap.BootstrapOutput
import io.ktor.http.HttpMethod
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

internal class DefaultApplicationBootstrap(
    private val remoteDataSource: RemoteDataSource,
    private val json: Json = Json { ignoreUnknownKeys = true },
) : ApplicationBootstrap {
    private val logger = Logger.withTag("Bootstrap")

    override suspend fun invoke(): Result<BootstrapOutput> {
        logger.i { "Requesting $BOOTSTRAP_ENDPOINT" }

        return try {
            val response = remoteDataSource.execute(
                method = HttpMethod.Get,
                url = BOOTSTRAP_ENDPOINT,
                headers = mapOf(
                    "X-CarBroz-Platform" to "ANDROID",
                    "X-CarBroz-App-Version" to "1.0.0",
                    "X-CarBroz-Build-Number" to "1",
                ),
            )

            logger.i { "Response HTTP ${response.statusCode}" }

            if (response.statusCode !in 200..299) {
                logger.e { "HTTP failure: ${response.statusCode}" }
                return Result.failure(BootstrapFailure.Http(response.statusCode))
            }

            val dto = json.decodeFromString<BootstrapResponseDto>(response.body)
            logger.i { "Response parsed successfully" }
            dto.toDomain()
        } catch (error: CancellationException) {
            throw error
        } catch (error: SerializationException) {
            logger.e(error) { "Serialization failure: ${error.message}" }
            Result.failure(
                BootstrapFailure.Serialization(
                    error.message ?: "Unable to decode bootstrap response",
                ),
            )
        } catch (error: Throwable) {
            logger.e(error) { "Transport failure: ${error.message}" }
            Result.failure(
                BootstrapFailure.Transport(
                    error.message ?: "Unable to reach bootstrap service",
                ),
            )
        }
    }

    private companion object {
        const val BOOTSTRAP_ENDPOINT = "/api/v1/partner/config/bootstrap"
    }
}
