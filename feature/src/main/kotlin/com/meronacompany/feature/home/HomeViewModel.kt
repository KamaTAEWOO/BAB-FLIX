package com.meronacompany.feature.home

import androidx.lifecycle.viewModelScope
import com.meronacompany.common.base.BaseViewModel
import com.meronacompany.domain.repository.HomeRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class HomeViewModel(
    private val homeRepository: HomeRepository
) : BaseViewModel<HomeState, HomeEvent>(
    initialState = HomeState()
) {

    override fun reduceState(currentState: HomeState, event: HomeEvent): HomeState {
        return when (event) {
            is HomeEvent.TestEvent -> currentState.copy(test = event.test)
        }
    }

    fun requestIsApiKey() {
        homeRepository.requestIsApiKey()
            .onEach {
                Timber.d("requestIsApiKey: $it")
            }
            .catch {
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }
}
