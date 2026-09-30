package com.carbroz.cbpartner.data.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.client.engine.mock.addHandler
import kotlin.test.Test
import kotlin.test.assertEquals
import org.koin.dsl.koinApplication

class NetworkModuleTest {

    @Test
    fun networkModuleResolvesHttpClient() {
        val application = koinApplication {
            modules(networkModule)
        }

        val client = application.koin.get<HttpClient>()
        client.close()
        application.close()
    }

    @Test
    fun mockEngineExecutesRequestWithoutRealNetwork() = kotlinx.coroutines.test.runTest {
        val client = HttpClient(MockEngine) {
            engine {
                addHandler {
                    respond(
                        content = "",
                        status = HttpStatusCode.OK,
                    )
                }
            }
        }

        try {
            val response = client.get("https://example.test")
            assertEquals(HttpStatusCode.OK, response.status)
        } finally {
            client.close()
        }
    }
}
