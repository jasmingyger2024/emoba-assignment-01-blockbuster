package fhnw.emoba.blockbuster.ui.screens


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import fhnw.emoba.blockbuster.ui.components.MovieCard
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel
import fhnw.emoba.blockbuster.viewmodel.ScreenID

@Composable
fun FavoritesScreen(viewModel: BlockbusterModel,
                    navigateToScreen: (ScreenID) -> Unit,
) {
    LazyColumn() {
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
