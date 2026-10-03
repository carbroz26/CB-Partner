package com.carbroz.cbpartner.feature.dynamic.sdui.element

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.carbroz.cbpartner.domain.model.dynamic.DynamicElement
import com.carbroz.cbpartner.domain.model.dynamic.DynamicValue

class ImageElement {
    val type = "image"
    @Composable
    fun Render(element: DynamicElement) {
        val description = (element.properties["contentDescription"] as? DynamicValue.StringValue)?.value
            ?: (element.properties["url"] as? DynamicValue.StringValue)?.value
            ?: "Image"
        Text(text = description)
    }
}
