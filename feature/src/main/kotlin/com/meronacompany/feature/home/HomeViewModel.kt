package com.meronacompany.feature.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.meronacompany.common.base.BaseInjection
import com.meronacompany.common.base.BaseViewModel
import com.meronacompany.common.base.BaseViewModelFactory
import com.meronacompany.domain.model.VideoResult
import com.meronacompany.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class HomeViewModel(
    private val homeRepository: HomeRepository
) : BaseViewModel<HomeState, HomeEvent>(
    initialState = HomeState()
) {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    val movieVideoKeyList = MutableStateFlow<List<VideoResult>>(emptyList())
    val tvVideoKeyList = MutableStateFlow<List<VideoResult>>(emptyList())

    private val _movieVideoKey = MutableStateFlow("")
    var movieVideoKey: String
        get() = _movieVideoKey.value
        set(value) {
            _movieVideoKey.value = value
        }

    private val _tvVideoKey = MutableStateFlow("")
    var tvVideoKey: String
        get() = _tvVideoKey.value
        set(value) {
            _tvVideoKey.value = value
        }

    private val _moviePagerIndex = MutableStateFlow(0)
    val moviePagerIndex: StateFlow<Int> = _moviePagerIndex

    private val _tvPagerIndex = MutableStateFlow(0)
    val tvPagerIndex: StateFlow<Int> = _tvPagerIndex

    fun setMoviePagerIndex(index: Int) {
        _moviePagerIndex.value = index
    }

    fun setTvPagerIndex(index: Int) {
        _tvPagerIndex.value = index
    }

    private val _movieScrollStates = mutableMapOf<Int, LazyListState>()
    val movieScrollStates: MutableMap<Int, LazyListState>
        get() = _movieScrollStates

    private val _tvScrollStates = mutableMapOf<Int, LazyListState>()
    val tvScrollStates: MutableMap<Int, LazyListState>
        get() = _tvScrollStates

    override fun reduceState(currentState: HomeState, event: HomeEvent): HomeState {
        return when (event) {
            is HomeEvent.PopularMoviesEvent -> currentState.copy(
                popularMovies = event.popularMovies,
                allPopularMoviesData = currentState.allPopularMoviesData + (event.popularMovies.page to event.popularMovies.results)
            )
            is HomeEvent.PopularTVsEvent -> currentState.copy(
                popularTVs = event.popularTVs,
                allPopularTVsData = currentState.allPopularTVsData + (event.popularTVs.page to event.popularTVs.results)
            )
            is HomeEvent.GenresMoviesEvent -> currentState.genresMovies?.let {
                currentState.copy(genresMovies = it)
            } ?: run {
                currentState.copy(genresMovies = event.genres)
            }
            is HomeEvent.GenresTVsEvent -> currentState.genresTVs?.let {
                currentState.copy(genresTVs = it)
            } ?: run {
                currentState.copy(genresTVs = event.genres)
            }
//            is HomeEvent.MovieVideoEvent -> currentState.copy(
//                movieVideo = event.movieVideo,
//                movieVideoKey = event.movieVideo.results[0].key
//            )
//            is HomeEvent.TvVideoEvent -> currentState.copy(
//                tvVideo = event.tvVideo,
//                tvVideoKey = event.tvVideo.results[0].key
//            )
            is HomeEvent.MovieDetailEvent -> currentState.copy(movieDetail = event.movieDetail)
            is HomeEvent.TvDetailEvent -> currentState.copy(tvDetail = event.tvDetail)
            is HomeEvent.MovieCreditsEvent -> currentState.copy(movieCredits = event.movieCredits)
            is HomeEvent.MovieCertificationEvent -> currentState.copy(movieCertification = event.movieCertification)
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
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestPopularMovies(pageCount: Int = 1) {
        homeRepository.requestPopularMovies(pageNumber = pageCount)
            .onEach {
                sendAction(HomeEvent.PopularMoviesEvent(it))
                if (it.results.isEmpty()) {
                    requestPopularMovies()
                }
            }
            .catch {
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
                Timber.e(it)
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestPopularTVs(pageNumber: Int = 1) {
        homeRepository.requestPopularTVs(pageNumber = pageNumber)
            .onEach {
//                Timber.d("requestPopularTVs: $it")
                sendAction(HomeEvent.PopularTVsEvent(it))
                if (it.results.isEmpty()) {
                    requestPopularTVs()
                }
            }
            .catch {
                Timber.e(it)
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
                FirebaseCrashlytics.getInstance().recordException(it)
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
                FirebaseCrashlytics.getInstance().recordException(it)
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
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestTVGenres() {
        homeRepository.requestTVGenres()
            .onEach {
                Timber.d("requestTVGenres: $it")
                sendAction(HomeEvent.GenresTVsEvent(it))
            }
            .catch {
                Timber.e(it)
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestMovieVideo(id: Int) {
        homeRepository.requestMovieVideo(id)
            .onEach {
//                Timber.d("requestMovieVideo: $it")
                if (it.results.isNotEmpty()) {
                    movieVideoKey = it.results[0].key
                    movieVideoKeyList.value = it.results
//                    sendAction(HomeEvent.MovieVideoEvent(it))
                } else {
                    sendAction(HomeEvent.ErrorEvent("No video found"))
                }
            }
            .catch {
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
                Timber.e(it)
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestTvVideo(id: Int) {
        homeRepository.requestTvVideo(id)
            .onEach {
                Timber.d("requestTvVideo: $it")
                if (it.results.isNotEmpty()) {
//                    sendAction(HomeEvent.TvVideoEvent(it))
                    tvVideoKey = it.results[0].key
                    tvVideoKeyList.value = it.results
                } else {
                    sendAction(HomeEvent.ErrorEvent("No video found"))
                }
            }
            .catch {
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
                Timber.e(it)
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestMovieDetail(movieId: Int) {
        homeRepository.requestMovieDetail(movieId)
            .onEach {
                Timber.d("requestMovieDetail: $it")
                sendAction(HomeEvent.MovieDetailEvent(it))
            }
            .catch {
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
                Timber.e(it)
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestTvDetail(tvId: Int) {
        homeRepository.requestTvDetail(tvId)
            .onEach {
                Timber.d("requestTvDetail: $it")
                sendAction(HomeEvent.TvDetailEvent(it))
            }
            .catch {
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
                Timber.e(it)
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestMovieCertification(movieId: Int) {
        homeRepository.requestMovieCertification(movieId)
            .onEach {
                Timber.d("requestMovieCertification: $it")
                sendAction(HomeEvent.MovieCertificationEvent(it))
            }
            .catch {
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
                Timber.e(it)
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestMovieCredits(movieId: Int) {
        homeRepository.requestMovieCredits(movieId)
            .onEach {
                Timber.d("requestMovieCredits: $it")
                sendAction(HomeEvent.MovieCreditsEvent(it))
            }
            .catch {
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
                Timber.e(it)
                FirebaseCrashlytics.getInstance().recordException(it)
            }
            .launchIn(viewModelScope)
    }

    fun requestTvCredits(tvId: Int) {
        homeRepository.requestTvCredits(tvId)
            .onEach {
                Timber.d("requestTvCredits: $it")
                sendAction(HomeEvent.MovieCreditsEvent(it))
            }
            .catch {
                sendAction(HomeEvent.ErrorEvent(it.message ?: "Unknown error"))
                Timber.e(it)
                FirebaseCrashlytics.getInstance().recordException(it)
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
