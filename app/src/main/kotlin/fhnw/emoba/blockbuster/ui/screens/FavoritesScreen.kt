package fhnw.emoba.blockbuster.ui.screens


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.emoba.blockbuster.ui.components.MovieCard
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel

@Composable
fun FavoritesScreen(viewModel: BlockbusterModel) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(viewModel.favoriteMovies) { movie ->
            MovieCard(
                movie = movie,
                isFavorite = movie in viewModel.favoriteMovies,
                onClick = { viewModel.selectMovie(it.id) },
                onFavoriteClick = { viewModel.toggleFavorite(it) },
            )
        }
    }
}
