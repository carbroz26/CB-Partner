package com.carbroz.cbpartner.feature.dynamic.registry

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class DynamicRegistryTest {
    @Test
    fun initialRegistryContainsFrozenDefinitions() {
        val registry = createInitialDynamicRegistry()
        assertTrue(registry.resolveTemplate("stack_template") is DynamicResolution.Resolved)
        assertTrue(registry.resolveComponent("stack_component") is DynamicResolution.Resolved)
        assertTrue(registry.resolveSection("stack_section") is DynamicResolution.Resolved)
        assertTrue(registry.resolveGroup("stack_group") is DynamicResolution.Resolved)
        assertTrue(registry.resolveElement("text") is DynamicResolution.Resolved)
        assertTrue(registry.resolveElement("image") is DynamicResolution.Resolved)
    }

    @Test
    fun duplicateRegistrationIsRejected() {
        val registry = DynamicRegistry()
        registry.register(TextDefinition())
        assertFailsWith<RegistrationException.Duplicate> { registry.register(TextDefinition()) }
    }

    @Test
    fun categoryLookupDoesNotCrossResolve() {
        val registry = createInitialDynamicRegistry()
        assertTrue(registry.resolveTemplate("text") is DynamicResolution.Unknown)
        assertTrue(registry.resolveElement("stack_template") is DynamicResolution.Unknown)
    }

    @Test
    fun registeredDefinitionCanReturnUnsupportedResolution() {
        val registry = DynamicRegistry()
        registry.register(object : DynamicDefinition {
            override val type = "restricted"
            override val category = DynamicDefinitionCategory.ELEMENT
            override val supportedCapabilities = emptySet<String>()
            override fun supports(properties: Map<String, com.carbroz.cbpartner.domain.model.dynamic.DynamicValue>): Boolean = false
        })

        assertTrue(registry.resolveElement("restricted") is DynamicResolution.Unsupported)
    }
}
