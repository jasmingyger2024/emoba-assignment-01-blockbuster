package fhnw.emoba.blockbuster.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fhnw.emoba.blockbuster.data.Movie
import fhnw.emoba.blockbuster.data.MovieDetails
import fhnw.emoba.blockbuster.data.TmdbService
import fhnw.emoba.blockbuster.data.Trailer
import kotlinx.coroutines.launch

class BlockbusterModel : ViewModel() {
    var tmdbService = TmdbService()
    var movies by mutableStateOf<List<Movie>>(emptyList())
        private set
    var popularMovies by mutableStateOf<List<Movie>>(emptyList())
        private set

    var searchQuery by mutableStateOf("")

    var selectedMovieId by mutableIntStateOf(0)
    var selectedMovie by mutableStateOf<MovieDetails?>(null)

    var selectedMovieTrailer by mutableStateOf<Trailer?>(null)


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

    fun selectMovie(movieId: Int) {
        selectedMovieId = movieId
        selectedMovie = null
        loadMovieDetails()
        navigateTo(ScreenID.DETAIL)
    }

    fun loadMovieDetails() {
        if (selectedMovieId == 0) return

        viewModelScope.launch {
            selectedMovie = tmdbService.getMovieDetails(selectedMovieId)
            selectedMovieTrailer = tmdbService.getMovieTrailer(selectedMovieId)
        }
    }



    init {
        loadMovies()
    }
}