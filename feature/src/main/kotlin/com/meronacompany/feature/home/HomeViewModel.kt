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
import java.sql.Time

class HomeViewModel(
    private val homeRepository: HomeRepository
) : BaseViewModel<HomeState, HomeEvent>(
    initialState = HomeState()
) {
    private var currentPage = 1

    override fun reduceState(currentState: HomeState, event: HomeEvent): HomeState {
        return when (event) {
            is HomeEvent.PopularMoviesEvent -> currentState.copy(popularMovies = event.popularMovies)
            is HomeEvent.ErrorEvent -> currentState.copy(errorMessage = event.errorMessage)
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
                sendAction(HomeEvent.PopularMoviesEvent(it))
            }
            .catch {
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
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

    fun requestWatchProviders() {
        homeRepository.requestWatchProviders(movieId = 950396)
            .onEach {
                Timber.d("requestWatchProviders: $it")
            }
            .catch {
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestMovieGenres() {
        homeRepository.requestMovieGenres()
            .onEach {
                Timber.d("requestMovieGenres: $it")
            }
            .catch {
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestTVGenres() {
        homeRepository.requestTVGenres()
            .onEach {
                Timber.d("requestTVGenres: $it")
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
