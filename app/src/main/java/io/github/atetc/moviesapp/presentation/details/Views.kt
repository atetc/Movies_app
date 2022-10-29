package io.github.atetc.moviesapp.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import io.github.atetc.domain.movie.Movie
import io.github.atetc.moviesapp.ui.theme.MoviesAppTheme

object Views {
    @Composable
    fun Content(movie: Movie) {
        MoviesAppTheme {
            Surface(color = MaterialTheme.colors.background) {
                Image(
                    painter = rememberAsyncImagePainter(movie.poster),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                        .alpha(0.07f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(Color.Transparent)
                ) {
                    Text(
                        text = movie.title,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp),
                        style = TextStyle(textAlign = TextAlign.Center)
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(movie.poster),
                            contentDescription = null,
                            modifier = Modifier.size(128.dp),
                        )

                        Text(
                            text = movie.plot,
                            fontSize = 16.sp,
                            modifier = Modifier.fillMaxSize(),
                            style = TextStyle(textAlign = TextAlign.Start)
                        )
                    }

                    InfoRow(key = "Year", value = movie.year)
                    InfoRow(key = "Actors", value = movie.actors)
                    InfoRow(key = "Language", value = movie.language)
                    InfoRow(key = "Runtime", value = movie.runtime)
                }
            }
        }
    }

    @Composable
    fun InfoRow(key: String, value: String) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .padding(
                    top = 8.dp,
                    bottom = 8.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Text(
                text = key,
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxSize(),
                style = TextStyle(
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "\t· $value",
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxSize(),
                style = TextStyle(textAlign = TextAlign.Start)
            )
        }
    }
}