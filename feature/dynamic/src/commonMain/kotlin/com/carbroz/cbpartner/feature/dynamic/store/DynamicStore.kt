package com.carbroz.cbpartner.feature.dynamic.store

import com.carbroz.cbpartner.domain.model.dynamic.DynamicAction
import com.carbroz.cbpartner.domain.model.dynamic.DynamicDestination
import com.carbroz.cbpartner.domain.model.dynamic.DynamicResponse
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

sealed interface DynamicIntent {
    data object Load : DynamicIntent
    data object Retry : DynamicIntent
    data class Action(val action: DynamicAction) : DynamicIntent
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

    private var loadJob: Job? = null

    fun accept(intent: DynamicIntent) {
        when (intent) {
            DynamicIntent.Load, DynamicIntent.Retry -> load()
            is DynamicIntent.Action -> actionHandler.handle(intent.action) { effect -> emit(effect) }
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

    private fun emit(effect: DynamicEffect) {
        _effects.value = effect
    }

    fun clearEffect() {
        _effects.value = null
    }

    fun close() {
        loadJob?.cancel()
        scope.cancel()
    }
}
