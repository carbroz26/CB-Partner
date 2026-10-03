package com.carbroz.cbpartner.data.dynamic.model

import com.carbroz.cbpartner.domain.model.dynamic.DynamicAction
import com.carbroz.cbpartner.domain.model.dynamic.DynamicActions
import com.carbroz.cbpartner.domain.model.dynamic.DynamicBinding
import com.carbroz.cbpartner.domain.model.dynamic.DynamicComponent
import com.carbroz.cbpartner.domain.model.dynamic.DynamicElement
import com.carbroz.cbpartner.domain.model.dynamic.DynamicGroup
import com.carbroz.cbpartner.domain.model.dynamic.DynamicProperties
import com.carbroz.cbpartner.domain.model.dynamic.DynamicResponse
import com.carbroz.cbpartner.domain.model.dynamic.DynamicScreen
import com.carbroz.cbpartner.domain.model.dynamic.DynamicSection
import com.carbroz.cbpartner.domain.model.dynamic.DynamicTemplate
import com.carbroz.cbpartner.domain.model.dynamic.DynamicTheme
import com.carbroz.cbpartner.domain.model.dynamic.DynamicValidation
import com.carbroz.cbpartner.domain.model.dynamic.DynamicValue
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull

fun DynamicResponseDto.toDomain(): DynamicResponse =
    DynamicResponse(status, code, message, data.toDomain(), traceId)

private fun DynamicScreenDto.toDomain() = DynamicScreen(
    screenId, schemaVersion, targetApp, template.toDomain(), theme?.toDomain()
)

private fun DynamicTemplateDto.toDomain() = DynamicTemplate(
    id, type, properties.toProperties(), components.map { it.toDomain() }
)

private fun DynamicComponentDto.toDomain() = DynamicComponent(
    id, type, properties.toProperties(),
    elements.map { it.toDomain() },
    sections.map { it.toDomain() },
)

private fun DynamicSectionDto.toDomain() = DynamicSection(
    id, type, properties.toProperties(),
    elements.map { it.toDomain() },
    groups.map { it.toDomain() },
)

private fun DynamicGroupDto.toDomain() = DynamicGroup(
    id, type, properties.toProperties(),
    elements.map { it.toDomain() },
)

private fun DynamicElementDto.toDomain() = DynamicElement(
    id = id,
    type = type,
    properties = properties.toProperties(),
    validation = validation?.let { DynamicValidation(it.required, it.pattern, it.message) },
    binding = binding?.let { DynamicBinding(it.key) },
    actions = actions?.let {
        DynamicActions(
            onClick = it.onClick?.toDomain(),
            onLongClick = it.onLongClick?.toDomain(),
            onValueChange = it.onValueChange?.toDomain(),
            onFocus = it.onFocus?.toDomain(),
            onSubmit = it.onSubmit?.toDomain(),
        )
    },
)

private fun DynamicActionDto.toDomain() =
    DynamicAction(type, payload.toDynamicMap())

private fun DynamicThemeDto.toDomain() =
    DynamicTheme(theme, statusBar, properties.toDynamicMap())

private fun JsonObject.toProperties() = DynamicProperties(toDynamicMap())

private fun JsonObject.toDynamicMap(): Map<String, DynamicValue> =
    entries.associate { (key, value) -> key to value.toDynamicValue() }

private fun JsonElement.toDynamicValue(): DynamicValue =
    when (this) {
        is JsonObject -> DynamicValue.ObjectValue(toDynamicMap())
        is kotlinx.serialization.json.JsonArray ->
            DynamicValue.ArrayValue(map { it.toDynamicValue() })
        is JsonPrimitive -> when {
            isString -> DynamicValue.StringValue(content)
            booleanOrNull != null -> DynamicValue.BooleanValue(booleanOrNull!!)
            doubleOrNull != null -> DynamicValue.NumberValue(doubleOrNull!!)
            else -> DynamicValue.NullValue
        }
        else -> DynamicValue.NullValue
    }
