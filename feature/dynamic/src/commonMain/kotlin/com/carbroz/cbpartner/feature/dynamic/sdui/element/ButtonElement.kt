package com.carbroz.cbpartner.feature.dynamic.sdui.element

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.carbroz.cbpartner.domain.model.dynamic.DynamicAction
import com.carbroz.cbpartner.domain.model.dynamic.DynamicElement
import com.carbroz.cbpartner.domain.model.dynamic.DynamicValue

class ButtonElement {
    val type = "button"
    @Composable
    fun Render(element: DynamicElement, onAction: (DynamicAction) -> Unit) {
        val title = (element.properties["text"] as? DynamicValue.StringValue)?.value ?: "Button"
        Button(onClick = { element.actions["onClick"]?.let(onAction) }) {
            Text(title)
        }
    }
}
