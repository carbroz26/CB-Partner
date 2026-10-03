package com.carbroz.cbpartner.feature.dynamic.store

import com.carbroz.cbpartner.domain.model.dynamic.DynamicAction
import com.carbroz.cbpartner.domain.model.dynamic.DynamicDestination
import com.carbroz.cbpartner.domain.model.dynamic.DynamicResponse
import com.carbroz.cbpartner.domain.model.dynamic.DynamicValue
import com.carbroz.cbpartner.domain.repository.DynamicRepository
import com.carbroz.cbpartner.feature.dynamic.action.DynamicActionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

sealed interface DynamicIntent {
    data object Load : DynamicIntent
    data object Retry : DynamicIntent
    data class Action(val action: DynamicAction) : DynamicIntent
    data class ValueChanged(val key: String, val value: String) : DynamicIntent
}

sealed interface DynamicEffect {
    data class Navigate(val destination: DynamicDestination) : DynamicEffect
    data class ExternalUri(val uri: String) : DynamicEffect
    data class Request(val action: DynamicAction) : DynamicEffect
}

sealed interface DynamicState {
    data object Initial : DynamicState
    data object Loading : DynamicState
    data class Success(val response: DynamicResponse) : DynamicState
    data class Failure(val message: String) : DynamicState
}

class DynamicStore(
    private val repository: DynamicRepository,
    private val destination: DynamicDestination,
    private val actionHandler: DynamicActionHandler = DynamicActionHandler(),
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
) {
    private val _state = MutableStateFlow<DynamicState>(DynamicState.Initial)
    val state: StateFlow<DynamicState> = _state.asStateFlow()

    private val _effects = MutableStateFlow<DynamicEffect?>(null)
    val effects: StateFlow<DynamicEffect?> = _effects.asStateFlow()

    private val values = mutableMapOf<String, String>()
    private var loadJob: Job? = null
    private var requestJob: Job? = null

    fun accept(intent: DynamicIntent) {
        when (intent) {
            DynamicIntent.Load, DynamicIntent.Retry -> load()
            is DynamicIntent.ValueChanged -> values[intent.key] = intent.value
            is DynamicIntent.Action -> handleAction(intent.action)
        }
    }

    private fun handleAction(action: DynamicAction) {
        if (action.type == "request") {
            request(action)
        } else {
            actionHandler.handle(action) { effect -> emit(effect) }
        }
    }

    private fun load() {
        if (loadJob?.isActive == true) return
        _state.value = DynamicState.Loading
        loadJob = scope.launch {
            repository.fetch(destination)
                .onSuccess { _state.value = DynamicState.Success(it) }
                .onFailure { _state.value = DynamicState.Failure(it.message ?: "Unable to load dynamic screen") }
        }
    }

    private fun request(action: DynamicAction) {
        if (requestJob?.isActive == true) return

        val endpoint = string(action.payload["endpoint"])
            ?: run {
                _state.value = DynamicState.Failure("Dynamic request endpoint is missing")
                return
            }
        val method = string(action.payload["method"]) ?: "POST"
        val authentication = string(action.payload["authentication"])
        val body = action.payload["body"]?.resolveBindings()?.let(Json::encodeToString)

        requestJob = scope.launch {
            repository.fetch(
                destination = DynamicDestination(
                    screenId = "action:$endpoint",
                    templateId = null,
                    templateType = null,
                    endpoint = endpoint,
                    method = method,
                    authentication = authentication,
                ),
                body = body,
            ).onSuccess { response ->
                val next = response.nextScreen
                if (next == null) {
                    _state.value = DynamicState.Failure("Dynamic request did not provide nextScreen")
                } else {
                    emit(
                        DynamicEffect.Navigate(
                            DynamicDestination(
                                screenId = next.screenId,
                                templateId = next.templateId,
                                templateType = next.templateType,
                                endpoint = next.endpoint,
                                method = next.method,
                                authentication = next.authentication,
                            ),
                        ),
                    )
                }
            }.onFailure {
                _state.value = DynamicState.Failure(it.message ?: "Unable to submit dynamic request")
            }
        }
    }

    private fun DynamicValue.resolveBindings(): JsonElement = when (this) {
        is DynamicValue.ObjectValue -> {
            val binding = (value["${'$'}binding"] as? DynamicValue.StringValue)?.value
            if (binding != null) {
                JsonPrimitive(values[binding] ?: "")
            } else {
                JsonObject(value.mapValues { (_, child) -> child.resolveBindings() })
            }
        }
        is DynamicValue.ArrayValue -> JsonArray(value.map { it.resolveBindings() })
        is DynamicValue.StringValue -> JsonPrimitive(value)
        is DynamicValue.NumberValue -> JsonPrimitive(value)
        is DynamicValue.BooleanValue -> JsonPrimitive(value)
        DynamicValue.NullValue -> JsonNull
    }

    private fun string(value: DynamicValue?): String? =
        (value as? DynamicValue.StringValue)?.value

    private fun emit(effect: DynamicEffect) {
        _effects.value = effect
    }

    fun clearEffect() {
        _effects.value = null
    }

    fun close() {
        loadJob?.cancel()
        requestJob?.cancel()
        scope.cancel()
    }
}
