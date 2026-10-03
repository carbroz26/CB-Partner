package com.carbroz.cbpartner.feature.dynamic.sdui.element

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.carbroz.cbpartner.domain.model.dynamic.DynamicElement
import com.carbroz.cbpartner.domain.model.dynamic.DynamicValue

class TextElement {
    val type = "text"
    @Composable
    fun Render(element: DynamicElement) {
        val text = (element.properties["text"] as? DynamicValue.StringValue)?.value ?: ""
        Text(text = text)
    }
}
