package fhnw.emoba.blockbuster.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fhnw.emoba.blockbuster.data.Movie
import fhnw.emoba.blockbuster.data.TmdbService
import kotlinx.coroutines.launch

class BlockbusterModel : ViewModel() {
    var tmdbService = TmdbService()
    var movies by mutableStateOf<List<Movie>>(emptyList())
        private set
    var popularMovies by mutableStateOf<List<Movie>>(emptyList())
        private set

    var searchQuery by mutableStateOf("")




    var selectedScreen by mutableStateOf(ScreenID.MOVIES)
    var previousScreen by mutableStateOf(ScreenID.MOVIES)

    fun navigateTo(screen: ScreenID) {
        previousScreen = selectedScreen
        selectedScreen = screen
    }

    fun navigateBack() {
        selectedScreen = previousScreen
    }

    fun loadMovies() {
        viewModelScope.launch {
            val movieResults = tmdbService.getDefaultMovies()
            movies = movieResults
            popularMovies = movieResults
        }
    }

    fun searchMovies() {
        viewModelScope.launch {
            movies = tmdbService.searchMovies(searchQuery)
        }
    }

    fun clearSearchResults() {
        movies = popularMovies
    }



    init {
        loadMovies()
    }
}