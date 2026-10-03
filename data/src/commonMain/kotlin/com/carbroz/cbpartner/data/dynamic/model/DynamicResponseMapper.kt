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

fun DynamicResponseDto.toDomain(): DynamicResponse =
    DynamicResponse(
        status = status,
        code = code,
        message = message,
        data = data.toDomain(),
        traceId = traceId,
    )

private fun DynamicScreenDto.toDomain() = DynamicScreen(
    screenId = screenId,
    schemaVersion = schemaVersion,
    targetApp = targetApp,
    template = template.toDomain(),
    theme = theme?.toDomain(),
)

private fun DynamicTemplateDto.toDomain() = DynamicTemplate(
    id = id,
    type = type,
    properties = DynamicProperties(properties),
    components = components.map { it.toDomain() },
)

private fun DynamicComponentDto.toDomain() = DynamicComponent(
    id = id,
    type = type,
    properties = DynamicProperties(properties),
    elements = elements.map { it.toDomain() },
    sections = sections.map { it.toDomain() },
)

private fun DynamicSectionDto.toDomain() = DynamicSection(
    id = id,
    type = type,
    properties = DynamicProperties(properties),
    elements = elements.map { it.toDomain() },
    groups = groups.map { it.toDomain() },
)

private fun DynamicGroupDto.toDomain() = DynamicGroup(
    id = id,
    type = type,
    properties = DynamicProperties(properties),
    elements = elements.map { it.toDomain() },
)

private fun DynamicElementDto.toDomain() = DynamicElement(
    id = id,
    type = type,
    properties = DynamicProperties(properties),
    validation = validation?.let {
        DynamicValidation(it.required, it.pattern, it.message)
    },
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
    DynamicAction(type = type, payload = payload)

private fun DynamicThemeDto.toDomain() =
    DynamicTheme(
        theme = theme,
        statusBar = statusBar,
        properties = properties,
    )
