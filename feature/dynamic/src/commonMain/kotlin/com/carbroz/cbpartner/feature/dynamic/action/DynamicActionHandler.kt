package com.carbroz.cbpartner.feature.dynamic.action

import com.carbroz.cbpartner.domain.model.dynamic.DynamicAction
import com.carbroz.cbpartner.domain.model.dynamic.DynamicDestination
import com.carbroz.cbpartner.domain.model.dynamic.DynamicValue
import com.carbroz.cbpartner.feature.dynamic.store.DynamicEffect

class DynamicActionHandler {
    fun handle(action: DynamicAction, emit: (DynamicEffect) -> Unit) {
        when (action.type) {
            "navigate" -> {
                val screenId = string(action.payload["screenId"]) ?: return
                val endpoint = string(action.payload["endpoint"]) ?: return
                emit(
                    DynamicEffect.Navigate(
                        DynamicDestination(
                            screenId = screenId,
                            templateId = string(action.payload["templateId"]),
                            templateType = string(action.payload["templateType"]),
                            endpoint = endpoint,
                            method = string(action.payload["method"]) ?: "GET",
                            authentication = string(action.payload["authentication"]),
                        ),
                    ),
                )
            }
            "external_uri" -> {
                string(action.payload["uri"])?.let { emit(DynamicEffect.ExternalUri(it)) }
            }
            "request" -> emit(DynamicEffect.Request(action))
        }
    }

    private fun string(value: DynamicValue?): String? =
        (value as? DynamicValue.StringValue)?.value
}
