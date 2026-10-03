package com.carbroz.cbpartner.feature.dynamic.sdui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.carbroz.cbpartner.domain.model.dynamic.DynamicComponent
import com.carbroz.cbpartner.feature.dynamic.renderer.DynamicRenderer

class StackComponent {
    val type = "stack_component"
    @Composable
    fun Render(component: DynamicComponent, renderer: DynamicRenderer) {
        Column {
            renderer.renderElements(component.elements)
            renderer.renderSections(component.sections)
        }
    }
}
