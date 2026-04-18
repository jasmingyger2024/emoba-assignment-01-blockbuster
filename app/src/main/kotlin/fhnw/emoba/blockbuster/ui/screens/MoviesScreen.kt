package fhnw.emoba.blockbuster.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel
import fhnw.emoba.blockbuster.viewmodel.ScreenID

@Composable
fun MoviesScreen( viewModel: BlockbusterModel,
    navigateToScreen: (ScreenID) -> Unit,
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = viewModel.searchQuery,
                onValueChange = {
                    viewModel.searchQuery = it
                    if (it.isBlank()) {
                        viewModel.clearSearchResults()
                    }
                },
                placeholder = { Text("What Movie are you looking for?") },
                keyboardActions = KeyboardActions(
                    onSearch = { viewModel.searchMovies() }),
                singleLine = true,
            )

            IconButton(onClick = { viewModel.searchMovies() }) {
                Icon(Icons.Default.Search, contentDescription = null)
            }
        }


        LazyColumn() {
            items(viewModel.movies) { movie ->

                Card(onClick = {
                    viewModel.selectMovie(movie.id)

                }) {
                    Text(movie.title, )
                }
            }
        }
    }
}