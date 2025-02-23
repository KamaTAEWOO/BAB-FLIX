package com.meronacompany.feature.home

import com.meronacompany.common.base.BaseViewModel
import timber.log.Timber

class HomeViewModel : BaseViewModel<HomeState, HomeEvent>(
    initialState = HomeState()
) {

    override fun reduceState(currentState: HomeState, event: HomeEvent): HomeState {
        return when (event) {
            is HomeEvent.TestEvent -> currentState.copy(test = event.test)
        }
    }

    fun requestIsApiKey() {
        Timber.d("requestIsApiKey")
    }
}
