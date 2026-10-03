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

    fun resolveTemplate(type: String) = templates.resolve(type)
    fun resolveComponent(type: String) = components.resolve(type)
    fun resolveSection(type: String) = sections.resolve(type)
    fun resolveGroup(type: String) = groups.resolve(type)
    fun resolveElement(type: String) = elements.resolve(type)
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

    fun resolve(type: String): DynamicResolution<DynamicDefinition> =
        definitions[type]?.let(DynamicResolution<DynamicDefinition>::Resolved)
            ?: DynamicResolution.Unknown(category, type)
}

class TemplateRegistry : DynamicCategoryRegistry(DynamicDefinitionCategory.TEMPLATE)
class ComponentRegistry : DynamicCategoryRegistry(DynamicDefinitionCategory.COMPONENT)
class SectionRegistry : DynamicCategoryRegistry(DynamicDefinitionCategory.SECTION)
class GroupRegistry : DynamicCategoryRegistry(DynamicDefinitionCategory.GROUP)
class ElementRegistry : DynamicCategoryRegistry(DynamicDefinitionCategory.ELEMENT)
