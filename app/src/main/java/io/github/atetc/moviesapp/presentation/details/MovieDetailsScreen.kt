package io.github.atetc.moviesapp.theme

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.github.atetc.moviesapp.presentation.common.CommonView
import io.github.atetc.moviesapp.presentation.details.MovieDetailsState
import io.github.atetc.moviesapp.presentation.details.MovieDetailsViewModel
import io.github.atetc.moviesapp.presentation.details.Views.Content
import io.github.atetc.moviesapp.ui.theme.MoviesAppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MovieDetailsScreen(navController: NavController, viewModel: MovieDetailsViewModel) {
    val state = viewModel.state.observeAsState()
    MoviesAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    elevation = 0.dp,
                    backgroundColor = Color.Transparent,
                    title = { Text("Movie details")},
                    navigationIcon =
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                tint = MaterialTheme.colors.primaryVariant,
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )
            },
            content = {
                Surface(color = MaterialTheme.colors.background) {
                    when (val activeState = state.value) {
                    is MovieDetailsState.Error -> CommonView.ErrorView(errorMessage = activeState.message)
                    is MovieDetailsState.Loading -> CommonView.LoadingView()
                    is MovieDetailsState.Content -> Content(activeState.movie)
                    null -> throw Exception("Undesirable state")
                }
                }
            }
        )
    }

}
