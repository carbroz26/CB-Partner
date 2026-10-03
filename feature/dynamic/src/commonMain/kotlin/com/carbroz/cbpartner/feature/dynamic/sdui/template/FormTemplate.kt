package com.carbroz.cbpartner.feature.dynamic.sdui.template

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.carbroz.cbpartner.domain.model.dynamic.DynamicTemplate
import com.carbroz.cbpartner.feature.dynamic.renderer.DynamicRenderer

class FormTemplate {
    val type = "form_template"
    @Composable
    fun Render(template: DynamicTemplate, renderer: DynamicRenderer) {
        Column { renderer.renderComponents(template.components) }
    }
}
