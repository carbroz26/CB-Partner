package com.carbroz.cbpartner.feature.dynamic.registry

import com.carbroz.cbpartner.feature.dynamic.sdui.component.StackComponent
import com.carbroz.cbpartner.feature.dynamic.sdui.element.ButtonElement
import com.carbroz.cbpartner.feature.dynamic.sdui.element.ImageElement
import com.carbroz.cbpartner.feature.dynamic.sdui.element.InputElement
import com.carbroz.cbpartner.feature.dynamic.sdui.element.TextElement
import com.carbroz.cbpartner.feature.dynamic.sdui.group.StackGroup
import com.carbroz.cbpartner.feature.dynamic.sdui.section.StackSection
import com.carbroz.cbpartner.feature.dynamic.sdui.template.FormTemplate
import com.carbroz.cbpartner.feature.dynamic.sdui.template.StackTemplate

class DynamicRegistry {
    private val templates = mutableMapOf<String, Any>()
    private val components = mutableMapOf<String, Any>()
    private val sections = mutableMapOf<String, Any>()
    private val groups = mutableMapOf<String, Any>()
    private val elements = mutableMapOf<String, Any>()

    fun registerTemplate(type: String, template: Any) { templates[type] = template }
    fun registerComponent(type: String, component: Any) { components[type] = component }
    fun registerSection(type: String, section: Any) { sections[type] = section }
    fun registerGroup(type: String, group: Any) { groups[type] = group }
    fun registerElement(type: String, element: Any) { elements[type] = element }

    fun getTemplate(type: String): Any? = templates[type]
    fun getComponent(type: String): Any? = components[type]
    fun getSection(type: String): Any? = sections[type]
    fun getGroup(type: String): Any? = groups[type]
    fun getElement(type: String): Any? = elements[type]

    fun registerDefaults(): DynamicRegistry = apply {
        registerTemplate("form_template", FormTemplate())
        registerTemplate("stack_template", StackTemplate())
        registerComponent("stack_component", StackComponent())
        registerSection("stack_section", StackSection())
        registerGroup("stack_group", StackGroup())
        registerElement("text", TextElement())
        registerElement("image", ImageElement())
        registerElement("input", InputElement())
        registerElement("button", ButtonElement())
    }
}
