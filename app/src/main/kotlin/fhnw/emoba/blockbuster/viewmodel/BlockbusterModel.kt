package fhnw.emoba.blockbuster.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BlockbusterModel : ViewModel() {
    var selectedScreen by mutableStateOf(ScreenID.MOVIES)
    var previousScreen by mutableStateOf(ScreenID.MOVIES)

    fun navigateTo(screen: ScreenID) {
        previousScreen = selectedScreen
        selectedScreen = screen
    }

    fun navigateBack() {
        selectedScreen = previousScreen
    }
}