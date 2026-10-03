package com.carbroz.cbpartner.feature.dynamic.sdui.section

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.carbroz.cbpartner.domain.model.dynamic.DynamicSection
import com.carbroz.cbpartner.feature.dynamic.renderer.DynamicRenderer

class StackSection {
    val type = "stack_section"
    @Composable
    fun Render(section: DynamicSection, renderer: DynamicRenderer) {
        Column {
            renderer.renderElements(section.elements)
            renderer.renderGroups(section.groups)
        }
    }
}
