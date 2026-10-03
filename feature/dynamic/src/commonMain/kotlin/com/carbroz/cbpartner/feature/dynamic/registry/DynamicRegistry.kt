package com.carbroz.cbpartner.feature.dynamic.registry

class DynamicRegistry(
    val templates: TemplateRegistry = TemplateRegistry(),
    val components: ComponentRegistry = ComponentRegistry(),
    val sections: SectionRegistry = SectionRegistry(),
    val groups: GroupRegistry = GroupRegistry(),
    val elements: ElementRegistry = ElementRegistry(),
) {
    fun register(definition: DynamicDefinition) {
        when (definition.category) {
            DynamicDefinitionCategory.TEMPLATE -> templates.register(definition)
            DynamicDefinitionCategory.COMPONENT -> components.register(definition)
            DynamicDefinitionCategory.SECTION -> sections.register(definition)
            DynamicDefinitionCategory.GROUP -> groups.register(definition)
            DynamicDefinitionCategory.ELEMENT -> elements.register(definition)
        }
    }

    fun resolveTemplate(type: String, properties: Map<String, com.carbroz.cbpartner.domain.model.dynamic.DynamicValue> = emptyMap()) = templates.resolve(type, properties)
    fun resolveComponent(type: String, properties: Map<String, com.carbroz.cbpartner.domain.model.dynamic.DynamicValue> = emptyMap()) = components.resolve(type, properties)
    fun resolveSection(type: String, properties: Map<String, com.carbroz.cbpartner.domain.model.dynamic.DynamicValue> = emptyMap()) = sections.resolve(type, properties)
    fun resolveGroup(type: String, properties: Map<String, com.carbroz.cbpartner.domain.model.dynamic.DynamicValue> = emptyMap()) = groups.resolve(type, properties)
    fun resolveElement(type: String, properties: Map<String, com.carbroz.cbpartner.domain.model.dynamic.DynamicValue> = emptyMap()) = elements.resolve(type, properties)
}

sealed class RegistrationException(message: String) : IllegalArgumentException(message) {
    class Duplicate(category: DynamicDefinitionCategory, type: String) :
        RegistrationException("Definition already registered: " + category + "/" + type)
}

open class DynamicCategoryRegistry(
    private val category: DynamicDefinitionCategory,
) {
    private val definitions = mutableMapOf<String, DynamicDefinition>()

    fun register(definition: DynamicDefinition) {
        require(definition.category == category) {
            "Definition category mismatch: expected " + category + ", got " + definition.category
        }
        if (definitions.containsKey(definition.type)) {
            throw RegistrationException.Duplicate(category, definition.type)
        }
        definitions[definition.type] = definition
    }

    fun resolve(
        type: String,
        properties: Map<String, com.carbroz.cbpartner.domain.model.dynamic.DynamicValue> = emptyMap(),
    ): DynamicResolution<DynamicDefinition> {
        val definition = definitions[type]
            ?: return DynamicResolution.Unknown(category, type)

        return if (definition.supports(properties)) {
            DynamicResolution.Resolved(definition)
        } else {
            DynamicResolution.Unsupported(
                category = category,
                type = type,
                reason = "Definition does not support the requested configuration",
            )
        }
    }
}

class TemplateRegistry : DynamicCategoryRegistry(DynamicDefinitionCategory.TEMPLATE)
class ComponentRegistry : DynamicCategoryRegistry(DynamicDefinitionCategory.COMPONENT)
class SectionRegistry : DynamicCategoryRegistry(DynamicDefinitionCategory.SECTION)
class GroupRegistry : DynamicCategoryRegistry(DynamicDefinitionCategory.GROUP)
class ElementRegistry : DynamicCategoryRegistry(DynamicDefinitionCategory.ELEMENT)
