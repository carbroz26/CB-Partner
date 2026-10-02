package com.carbroz.cbpartner.data.bootstrap

import com.carbroz.cbpartner.domain.model.bootstrap.BootstrapFailure
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class BootstrapMapperTest {
    @Test
    fun apiFailureCodeAndMessageAreMapped() {
        val dto = Json.decodeFromString<BootstrapResponseDto>(
            """
            {
              "status": 200,
              "code": "INVALID_REQUEST",
              "message": "Bootstrap request rejected",
              "data": null,
              "traceId": "req-error"
            }
            """.trimIndent(),
        )

        val failure = dto.toDomain().exceptionOrNull()

        assertIs<BootstrapFailure.Api>(failure)
        assertEquals("INVALID_REQUEST", failure.code)
        assertEquals("Bootstrap request rejected", failure.message)
    }
}
