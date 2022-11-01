package io.github.atetc.moviesapp.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.atetc.domain.interactors.Interactor
import io.github.atetc.domain.mappers.imdb.MovieDetails
import io.github.atetc.moviesapp.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieDetailsInteractor: Interactor<String, MovieDetails>) : ViewModel() {
    val state
        get() = _state

    private val _state = MutableLiveData<MovieDetailsState>(MovieDetailsState.Loading)

    fun getMovieDetail(movieId: String) {
        _state.value = MovieDetailsState.Loading
        viewModelScope.launch {
            _state.value = withContext(Dispatchers.Default) {
                try {
                    when(val result = movieDetailsInteractor.execute(movieId)){
                        is MovieDetails.Error ->  MovieDetailsState.Error(result.message)
                        is MovieDetails.Success ->
                            MovieDetailsState.Content(result.movie)
                    }
                } catch (e: Exception) {
                    if (BuildConfig.DEBUG) {
                        MovieDetailsState.Error(e.message.toString())
                    } else {
                        MovieDetailsState.Error("Network error")
                    }
                }
            }
        }
    }
}
