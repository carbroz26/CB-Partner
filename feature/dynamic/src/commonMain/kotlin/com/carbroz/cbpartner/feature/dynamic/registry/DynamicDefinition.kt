package com.carbroz.cbpartner.feature.dynamic.registry

import com.carbroz.cbpartner.domain.model.dynamic.DynamicValue

enum class DynamicDefinitionCategory { TEMPLATE, COMPONENT, SECTION, GROUP, ELEMENT }

sealed interface DynamicResolution<out T> {
    data class Resolved<T>(val definition: T) : DynamicResolution<T>
    data class Unknown(val category: DynamicDefinitionCategory, val type: String) : DynamicResolution<Nothing>
    data class Unsupported(
        val category: DynamicDefinitionCategory,
        val type: String,
        val reason: String,
    ) : DynamicResolution<Nothing>
}

interface DynamicDefinition {
    val type: String
    val category: DynamicDefinitionCategory
    val supportedCapabilities: Set<String>

    fun supports(properties: Map<String, DynamicValue>): Boolean = true
}
