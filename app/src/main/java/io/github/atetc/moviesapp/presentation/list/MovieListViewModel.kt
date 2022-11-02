package io.github.atetc.moviesapp.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.atetc.domain.movie.MovieSearchParameters
import io.github.atetc.domain.interactors.Interactor
import io.github.atetc.domain.mappers.imdb.MovieSearch
import io.github.atetc.moviesapp.BuildConfig
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val searchInteractor: Interactor<MovieSearchParameters, MovieSearch>) : ViewModel() {
    private companion object {
        const val searchDebounce = 1000L
    }

    val state
        get() = _state

    private val _state = MutableLiveData<MoviesListState>(MoviesListState.Content(emptyList()))

    private var searchJob: Job? = null

    fun updateSearchWithDebounce(search: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(searchDebounce)
            _state.value = MoviesListState.Loading
            updateSearch(search)
        }
    }

    private suspend fun updateSearch(search: String) {
        try {
            _state.value = when(val result = searchInteractor.execute(MovieSearchParameters(search))){
                is MovieSearch.Error -> MoviesListState.Error(result.message)
                is MovieSearch.Success -> MoviesListState.Content(result.movies)
            }
        } catch (e: Exception) {
            _state.value = if (BuildConfig.DEBUG) {
                MoviesListState.Error(e.message.toString())
            } else {
                MoviesListState.Error("Network error")
            }
        }

    }
}
