package io.github.atetc.moviesapp.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.atetc.domain.interactors.Interactor
import io.github.atetc.domain.mappers.imdb.MovieDetails
import io.github.atetc.moviesapp.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val movieDetailsInteractor: Interactor<String, MovieDetails>) : ViewModel() {
    val state
        get() = _state

    private val _state = MutableLiveData<MovieDetailsState>(MovieDetailsState.Loading)

    fun getMovieDetail(movieId: String) {
        _state.value = MovieDetailsState.Loading
        CoroutineScope(Dispatchers.Main).launch {
            try {
                when(val result = movieDetailsInteractor.execute(movieId)){
                    is MovieDetails.Error ->  _state.value = MovieDetailsState.Error(result.message)
                    is MovieDetails.Success -> _state.value =
                        MovieDetailsState.Content(result.movie)
                }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    _state.value = MovieDetailsState.Error(e.message.toString())
                } else {
                    _state.value = MovieDetailsState.Error("Network error")
                }
            }
        }
    }
}
