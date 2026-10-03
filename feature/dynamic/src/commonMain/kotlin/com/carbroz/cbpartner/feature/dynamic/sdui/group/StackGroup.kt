package com.carbroz.cbpartner.feature.dynamic.sdui.group

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.carbroz.cbpartner.domain.model.dynamic.DynamicGroup
import com.carbroz.cbpartner.feature.dynamic.renderer.DynamicRenderer

class StackGroup {
    val type = "stack_group"
    @Composable
    fun Render(group: DynamicGroup, renderer: DynamicRenderer) {
        Column { renderer.renderElements(group.elements) }
    }
}
