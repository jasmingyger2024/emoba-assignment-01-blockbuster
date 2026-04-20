package fhnw.emoba.blockbuster.ui.screens

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import fhnw.emoba.blockbuster.ui.components.TrailerPlayer
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel
import fhnw.emoba.blockbuster.viewmodel.ScreenID

@Composable
fun PersonDetailScreen(
    viewModel: BlockbusterModel,
) {
    Text(viewModel.selectedPerson?.name ?: "")
}