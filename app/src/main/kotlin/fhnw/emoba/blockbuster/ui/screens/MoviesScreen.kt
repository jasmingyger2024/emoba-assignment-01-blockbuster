package fhnw.emoba.blockbuster.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel
import fhnw.emoba.blockbuster.viewmodel.ScreenID

@Composable
fun MoviesScreen( viewModel: BlockbusterModel,
    navigateToScreen: (ScreenID) -> Unit,
) {
    if(viewModel.movies != null) {
        LazyColumn() {
            items(viewModel.movies) { movie ->
                Text(movie.title)
            }
        }
    } else  {
        Text("Loading")

    }
}