package fhnw.emoba.blockbuster.ui.screens


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import fhnw.emoba.blockbuster.viewmodel.ScreenID

@Composable
fun FavoritesScreen(
    navigateToScreen: (ScreenID) -> Unit,
) {
    Text("Favorites")
}