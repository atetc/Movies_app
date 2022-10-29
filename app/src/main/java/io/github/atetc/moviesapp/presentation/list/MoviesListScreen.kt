package io.github.atetc.moviesapp.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.github.atetc.moviesapp.navigation.Screen
import io.github.atetc.moviesapp.presentation.common.CommonView
import io.github.atetc.moviesapp.presentation.list.MoviesListState
import io.github.atetc.moviesapp.presentation.list.MoviesListViewModel
import io.github.atetc.moviesapp.presentation.list.Views

@Composable
fun MoviesListScreen(navController: NavController, viewModel: MoviesListViewModel) {
    val state = viewModel.state.observeAsState()
    var search by rememberSaveable { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Views.SearchInput(search, onSearchChanged = {
            search = it
            viewModel.updateSearchWithDebounce(it)
        })
        when (val activeState = state.value) {
            is MoviesListState.Error -> CommonView.ErrorView(errorMessage = activeState.message)
            MoviesListState.Loading -> CommonView.LoadingView()
            is MoviesListState.Content -> Views.Content(activeState.content,
                onItemClick = { navController.navigate(Screen.Details.createRoute(it.imdbId)) })
            null -> throw Exception("Undesirable state")
        }
    }
}
