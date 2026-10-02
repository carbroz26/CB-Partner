package com.carbroz.cbpartner.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class RuntimeFoundationTest {

    @Test
    fun serializationRoundTrip() {
        val json = Json
        val encoded = json.encodeToString(SerializationProbe.serializer(), SerializationProbe("cb-partner"))
        val decoded = json.decodeFromString(SerializationProbe.serializer(), encoded)

        assertEquals(SerializationProbe("cb-partner"), decoded)
    }

    @Test
    fun coroutineTestEnvironmentRuns() = runTest {
        assertEquals(0L, currentTime)
    }

    @Serializable
    private data class SerializationProbe(
        val value: String,
    )
}
