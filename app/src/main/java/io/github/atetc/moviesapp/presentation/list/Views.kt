package io.github.atetc.moviesapp.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import io.github.atetc.domain.movie.MovieShort
import io.github.atetc.moviesapp.ui.theme.MoviesAppTheme

object Views {
    @Composable
    fun Content(
        content: List<MovieShort>,
        onItemClick: (movieShort: MovieShort) -> Unit
    ) {
        MoviesAppTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
            ) {
                content.forEach {
                    SearchItemView(it, onItemClick = onItemClick)
                }
            }
        }
    }

    @Composable
    fun SearchInput(search: String, onSearchChanged: (text: String) -> Unit) {
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            singleLine = true,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            value = search,
            onValueChange = { onSearchChanged.invoke(it) },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() } ),
            label = { Text("Movie search") }
        )
    }

    @Composable
    private fun SearchItemView(
        searchItem: MovieShort,
        onItemClick: (movieShort: MovieShort) -> Unit
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp, start = 8.dp, end = 8.dp)
            .clickable { onItemClick.invoke(searchItem) }) {
            Image(
                painter = rememberAsyncImagePainter(searchItem.poster),
                contentDescription = null,
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = searchItem.title,
                    fontSize = 24.sp,
                    style = TextStyle(textAlign = TextAlign.Start)
                )

                Text(
                    text = searchItem.year,
                    fontSize = 16.sp,
                    style = TextStyle(textAlign = TextAlign.Center)
                )
            }
        }
    }
}
