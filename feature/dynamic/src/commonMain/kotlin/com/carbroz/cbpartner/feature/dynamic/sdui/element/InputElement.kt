package com.carbroz.cbpartner.feature.dynamic.sdui.element

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.carbroz.cbpartner.domain.model.dynamic.DynamicElement
import com.carbroz.cbpartner.domain.model.dynamic.DynamicValue

class InputElement {
    val type = "input"

    @Composable
    fun Render(
        element: DynamicElement,
        onValueChange: (String, String) -> Unit,
    ) {
        val label = (element.properties["label"] as? DynamicValue.StringValue)?.value ?: ""
        val (value, setValue) = remember(element.id) { mutableStateOf("") }
        OutlinedTextField(
            value = value,
            onValueChange = {
                setValue(it)
                element.binding?.key?.let { key -> onValueChange(key, it) }
            },
            label = { androidx.compose.material3.Text(label) },
        )
    }
}
