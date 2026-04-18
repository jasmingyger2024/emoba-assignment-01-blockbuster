package fhnw.emoba.blockbuster.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel
import fhnw.emoba.blockbuster.viewmodel.ScreenID

@Composable
fun MoviesScreen( viewModel: BlockbusterModel,
    navigateToScreen: (ScreenID) -> Unit,
) {
    Column() {
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = {
                viewModel.searchQuery = it
                if (it.isBlank()) {
                    viewModel.clearSearchResults()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("What Movie are you looking for?") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },

            keyboardActions = KeyboardActions(
                onSearch = { viewModel.searchMovies() }),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search),
            singleLine = true,
        )

        LazyColumn() {
            items(viewModel.movies) { movie ->
                Text(movie.title)
            }
        }
    }
}