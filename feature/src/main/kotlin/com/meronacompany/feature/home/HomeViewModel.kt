package com.meronacompany.feature.home

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.meronacompany.common.base.BaseInjection
import com.meronacompany.common.base.BaseViewModel
import com.meronacompany.common.base.BaseViewModelFactory
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
    private var currentPage = 1
    private var isLastPage = false

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

    fun requestPopularMovies() {
        homeRepository.requestPopularMovies(pageNumber = currentPage)
            .onEach {
                Timber.d("requestPopularMovies: $it")
            }
            .catch {
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestPopularTVs() {
        homeRepository.requestPopularTVs(pageNumber = currentPage)
            .onEach {
                Timber.d("requestPopularTVs: $it")
            }
            .catch {
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory {
            return BaseViewModelFactory(HomeViewModel::class) {
                HomeViewModel(BaseInjection.homeRepository)
            }
        }
    }
}
