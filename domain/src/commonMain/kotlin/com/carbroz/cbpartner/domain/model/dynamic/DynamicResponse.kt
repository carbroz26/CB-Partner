package com.carbroz.cbpartner.domain.model.dynamic

data class DynamicResponse(
    val status: Int,
    val code: String,
    val message: String,
    val data: DynamicScreen,
    val traceId: String?,
)

data class DynamicScreen(
    val screenId: String,
    val schemaVersion: String,
    val targetApp: String,
    val template: DynamicTemplate,
    val theme: DynamicTheme?,
)

data class DynamicTemplate(
    val id: String,
    val type: String,
    val properties: DynamicProperties,
    val components: List<DynamicComponent>,
)

data class DynamicComponent(
    val id: String,
    val type: String,
    val properties: DynamicProperties,
    val elements: List<DynamicElement>,
    val sections: List<DynamicSection>,
)

data class DynamicSection(
    val id: String,
    val type: String,
    val properties: DynamicProperties,
    val elements: List<DynamicElement>,
    val groups: List<DynamicGroup>,
)

data class DynamicGroup(
    val id: String,
    val type: String,
    val properties: DynamicProperties,
    val elements: List<DynamicElement>,
)

data class DynamicElement(
    val id: String,
    val type: String,
    val properties: DynamicProperties,
    val validation: DynamicValidation?,
    val binding: DynamicBinding?,
    val actions: DynamicActions?,
)

data class DynamicProperties(
    val values: Map<String, Any?>,
)

data class DynamicValidation(
    val required: Boolean?,
    val pattern: String?,
    val message: String?,
)

data class DynamicBinding(
    val key: String,
)

data class DynamicActions(
    val onClick: DynamicAction?,
    val onLongClick: DynamicAction?,
    val onValueChange: DynamicAction?,
    val onFocus: DynamicAction?,
    val onSubmit: DynamicAction?,
)

data class DynamicAction(
    val type: String,
    val payload: Map<String, Any?>,
)

data class DynamicTheme(
    val theme: String?,
    val statusBar: String?,
    val properties: Map<String, Any?>,
)
