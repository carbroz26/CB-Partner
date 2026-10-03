package com.carbroz.cbpartner.feature.dynamic.registry

internal abstract class TypedDefinition(
    override val type: String,
    override val category: DynamicDefinitionCategory,
) : DynamicDefinition {
    override val supportedCapabilities: Set<String> = emptySet()
}

class StackTemplateDefinition : TypedDefinition("stack_template", DynamicDefinitionCategory.TEMPLATE)
class StackComponentDefinition : TypedDefinition("stack_component", DynamicDefinitionCategory.COMPONENT)
class StackSectionDefinition : TypedDefinition("stack_section", DynamicDefinitionCategory.SECTION)
class StackGroupDefinition : TypedDefinition("stack_group", DynamicDefinitionCategory.GROUP)
class TextDefinition : TypedDefinition("text", DynamicDefinitionCategory.ELEMENT)
class ImageDefinition : TypedDefinition("image", DynamicDefinitionCategory.ELEMENT)

fun createInitialDynamicRegistry(): DynamicRegistry =
    DynamicRegistry().apply {
        register(StackTemplateDefinition())
        register(StackComponentDefinition())
        register(StackSectionDefinition())
        register(StackGroupDefinition())
        register(TextDefinition())
        register(ImageDefinition())
    }
