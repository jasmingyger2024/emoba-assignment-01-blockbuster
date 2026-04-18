package fhnw.emoba.blockbuster.ui.screens

import android.telecom.Call
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import fhnw.emoba.blockbuster.data.Trailer
import fhnw.emoba.blockbuster.ui.components.TrailerPlayer
import fhnw.emoba.blockbuster.viewmodel.BlockbusterModel
import fhnw.emoba.blockbuster.viewmodel.ScreenID

@Composable
fun DetailScreen(viewModel: BlockbusterModel,
                 navigateToScreen: (ScreenID) -> Unit,
                 navigateBack: () -> Unit,
) {
    Text("Welcome to Detail Screen!")

    if(viewModel.selectedMovieTrailer != null && viewModel.selectedMovieTrailer!!.key != "") {
        TrailerPlayer(viewModel.selectedMovieTrailer!!.key)
    }
}