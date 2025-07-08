package com.meronacompany.feature.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.meronacompany.common.base.BaseInjection
import com.meronacompany.common.base.BaseViewModel
import com.meronacompany.common.base.BaseViewModelFactory
import com.meronacompany.core.utility.Locales
import com.meronacompany.domain.model.VideoResult
import com.meronacompany.domain.repository.HomeRepository
import com.meronacompany.design.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val homeRepository: HomeRepository,
    private val context: Context
) : BaseViewModel<HomeState, HomeEvent>(
    initialState = HomeState()
) {
    val apiLimit: Int = 700
    var apiUsageCount: Int = 0

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
            HomeEvent.ResetEvent -> {
                HomeState() // Reset the state to initial state
            }
        }
    }

    fun requestIsApiKey() {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
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
    }

    fun requestPopularMovies(pageCount: Int = 1) {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            homeRepository.requestPopularMovies(pageNumber = if(pageCount > 1) pageCount else 1)
                .onEach {
                    sendAction(HomeEvent.PopularMoviesEvent(it))
                    if (it.results.isEmpty()) {
                        requestPopularMovies(pageCount)
                    }
                }
                .catch {
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    Timber.e(it)
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestPopularTVs(pageNumber: Int = 1) {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            _isLoading.value = true
            homeRepository.requestPopularTVs(pageNumber = pageNumber)
                .onEach {
//                    Timber.d("requestPopularTVs: $it")
                    sendAction(HomeEvent.PopularTVsEvent(it))
                    _isLoading.value = false
                    if (it.results.isEmpty()) {
                        requestPopularTVs()
                    }
                }
                .catch {
                    Timber.e(it)
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    FirebaseCrashlytics.getInstance().recordException(it)
                    _isLoading.value = false
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestWatchProviders(movieId: Int) {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
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
    }

    fun requestMovieGenres() {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            homeRepository.requestMovieGenres()
                .onEach {
                    sendAction(HomeEvent.GenresMoviesEvent(it))
                }
                .catch {
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    Timber.e(it)
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestTVGenres() {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            homeRepository.requestTVGenres()
                .onEach {
                    Timber.d("requestTVGenres: $it")
                    sendAction(HomeEvent.GenresTVsEvent(it))
                }
                .catch {
                    Timber.e(it)
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestMovieVideo(id: Int) {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            homeRepository.requestMovieVideo(id)
                .onEach {
//                    Timber.d("requestMovieVideo: $it")
                    if (it.results.isNotEmpty()) {
                        movieVideoKey = it.results[0].key
                        movieVideoKeyList.value = it.results
//                        sendAction(HomeEvent.MovieVideoEvent(it))
                    } else {
                        sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    }
                }
                .catch {
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    Timber.e(it)
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestTvVideo(id: Int) {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            homeRepository.requestTvVideo(id)
                .onEach {
                    Timber.d("requestTvVideo: $it")
                    if (it.results.isNotEmpty()) {
//                        sendAction(HomeEvent.TvVideoEvent(it))
                        tvVideoKey = it.results[0].key
                        tvVideoKeyList.value = it.results
                    } else {
                        sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    }
                }
                .catch {
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    Timber.e(it)
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestMovieDetail(movieId: Int) {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            homeRepository.requestMovieDetail(movieId)
                .onEach {
                    Timber.d("requestMovieDetail: $it")
                    sendAction(HomeEvent.MovieDetailEvent(it))
                }
                .catch {
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    Timber.e(it)
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestTvDetail(tvId: Int) {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            homeRepository.requestTvDetail(tvId)
                .onEach {
                    Timber.d("requestTvDetail: $it")
                    sendAction(HomeEvent.TvDetailEvent(it))
                }
                .catch {
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    Timber.e(it)
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestMovieCertification(movieId: Int) {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            homeRepository.requestMovieCertification(movieId)
                .onEach {
                    Timber.d("requestMovieCertification: $it")
                    sendAction(HomeEvent.MovieCertificationEvent(it))
                }
                .catch {
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    Timber.e(it)
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestMovieCredits(movieId: Int) {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            homeRepository.requestMovieCredits(movieId)
                .onEach {
                    Timber.d("requestMovieCredits: $it")
                    sendAction(HomeEvent.MovieCreditsEvent(it))
                }
                .catch {
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    Timber.e(it)
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun requestTvCredits(tvId: Int) {
        viewModelScope.launch {
            if (!checkApiCallCount()) return@launch
            homeRepository.requestTvCredits(tvId)
                .onEach {
                    Timber.d("requestTvCredits: $it")
                    sendAction(HomeEvent.MovieCreditsEvent(it))
                }
                .catch {
                    sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
                    Timber.e(it)
                    FirebaseCrashlytics.getInstance().recordException(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun checkApiCallCount(): Boolean {
        val count = homeRepository.getApiCallCount()
        apiUsageCount = count
        return if (count > apiLimit) {
            Timber.d("taewoo - API call limit exceeded")
            sendAction(HomeEvent.ErrorEvent(context.getString(R.string.unknown_error)))
            false
        } else {
            true
        }
    }

    fun setLanguage(language: String) {
        homeRepository.setLanguage(language)
        updateLocaleResources(context, language)

        val msg = context.getString(R.string.language_changed, if (language == Locales.KO_KR) "한국어" else "English")
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        sendAction(HomeEvent.ResetEvent)

        requestPopularMovies() // 언어 반영된 인기 영화 다시 요청
        requestPopularTVs()    // 언어 반영된 인기 TV 다시 요청
    }

    /**
     * Updates the application's resources and locale to match the specified language.
     */
    fun updateLocaleResources(context: Context, language: String) {
        val currentLanguage = if (language == Locales.KO_KR) {
            "ko"
        } else {
            "en"
        }

        val locale = java.util.Locale(currentLanguage)
        java.util.Locale.setDefault(locale)
        val resources = context.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory {
            return BaseViewModelFactory(HomeViewModel::class) {
                HomeViewModel(BaseInjection.provideHomeRepository(context), context)
            }
        }
    }
}
