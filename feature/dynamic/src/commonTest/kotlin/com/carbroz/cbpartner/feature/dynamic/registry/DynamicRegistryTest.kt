package com.carbroz.cbpartner.feature.dynamic.registry

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class DynamicRegistryTest {
    @Test
    fun registrationsRemainSeparatedByCategory() {
        val registry = DynamicRegistry().registerDefaults()

        assertNotNull(registry.getTemplate("form_template"))
        assertNotNull(registry.getComponent("stack_component"))
        assertNotNull(registry.getSection("stack_section"))
        assertNotNull(registry.getGroup("stack_group"))
        assertNotNull(registry.getElement("text"))

        assertNull(registry.getTemplate("text"))
        assertNull(registry.getElement("form_template"))
    }

    @Test
    fun unknownTypesAreNotFound() {
        val registry = DynamicRegistry().registerDefaults()

        assertNull(registry.getTemplate("unknown_template"))
        assertNull(registry.getElement("unknown_element"))
    }
}
