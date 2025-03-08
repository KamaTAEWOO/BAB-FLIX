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
    override fun reduceState(currentState: HomeState, event: HomeEvent): HomeState {
        return when (event) {
            is HomeEvent.PopularMoviesEvent -> currentState.copy(
                popularMovies = event.popularMovies,
                allPopularMoviesData = currentState.allPopularMoviesData + (event.popularMovies.page to event.popularMovies.results)
            )
            is HomeEvent.GenresMoviesEvent -> currentState.genresMovies?.let {
                currentState.copy(genresMovies = it)
            } ?: run {
                currentState.copy(genresMovies = event.genres)
            }
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

    fun requestPopularMovies(pageCount: Int = 1) {
        homeRepository.requestPopularMovies(pageNumber = pageCount)
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
        homeRepository.requestPopularTVs(pageNumber = 1)
            .onEach {
                Timber.d("requestPopularTVs: $it")
            }
            .catch {
                Timber.e(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestWatchProviders(movieId: Int) {
        homeRepository.requestWatchProviders(movieId)
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
                sendAction(HomeEvent.GenresMoviesEvent(it))
            }
            .catch {
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
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
