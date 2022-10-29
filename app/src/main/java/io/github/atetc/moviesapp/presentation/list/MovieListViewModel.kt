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
            updateSearch(search)
        }
    }

    private fun updateSearch(search: String) {
        _state.value = MoviesListState.Loading
        CoroutineScope(Dispatchers.Main).launch {
            try {
                when(val result = searchInteractor.execute(MovieSearchParameters(search))){
                    is MovieSearch.Error -> _state.value = MoviesListState.Error(result.message)
                    is MovieSearch.Success -> _state.value = MoviesListState.Content(result.movies)
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    _state.value = MoviesListState.Error(e.message.toString())
                } else {
                    _state.value = MoviesListState.Error("Network error")
                }
            }
        }
    }
}
