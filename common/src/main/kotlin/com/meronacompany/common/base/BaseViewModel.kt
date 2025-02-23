package com.meronacompany.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : BaseState, EVENT : BaseEvent>(
    initialState: STATE
) : ViewModel() {

    private val events = Channel<EVENT>(Channel.UNLIMITED)
    val uiState: StateFlow<STATE> = events.receiveAsFlow()
        .runningFold(initialState, ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialState)

    protected abstract fun reduceState(currentState: STATE, event: EVENT): STATE

    protected fun sendAction(event: EVENT) {
        viewModelScope.launch {
            events.send(event)
        }
    }

    override fun onCleared() {
        super.onCleared()
        events.close()
    }
}
