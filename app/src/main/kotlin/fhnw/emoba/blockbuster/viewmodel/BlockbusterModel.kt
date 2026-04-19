package fhnw.emoba.blockbuster.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fhnw.emoba.blockbuster.data.Movie
import fhnw.emoba.blockbuster.data.MovieDetails
import fhnw.emoba.blockbuster.data.PersonDetails
import fhnw.emoba.blockbuster.data.Person
import fhnw.emoba.blockbuster.data.TmdbService
import fhnw.emoba.blockbuster.data.Trailer
import kotlinx.coroutines.launch

class BlockbusterModel : ViewModel() {
    var tmdbService = TmdbService()
    var movies by mutableStateOf<List<Movie>>(emptyList())
        private set
    var popularMovies by mutableStateOf<List<Movie>>(emptyList())
        private set
    var people by mutableStateOf<List<Person>>(emptyList())
        private set
    var popularPeople by mutableStateOf<List<Person>>(emptyList())
        private set
    var searchQuery by mutableStateOf("")
    var selectedMovieId by mutableIntStateOf(0)
    var selectedMovie by mutableStateOf<MovieDetails?>(null)
    var selectedPersonId by mutableIntStateOf(0)
    var selectedPerson by mutableStateOf<PersonDetails?>(null)
    var selectedMovieTrailer by mutableStateOf<Trailer?>(null)
    var favoriteMovies by mutableStateOf<List<Movie>>(emptyList())
        private set
    var selectedScreen by mutableStateOf(ScreenID.MOVIES)
    var previousScreen by mutableStateOf(ScreenID.MOVIES)

    // Navigation Functions
    fun navigateTo(screen: ScreenID) {
        previousScreen = selectedScreen
        selectedScreen = screen
    }
    fun navigateBack() {
        selectedScreen = previousScreen
    }

    // Loading Functions
    fun loadMovies() {
        viewModelScope.launch {
            val movieResults = tmdbService.getDefaultMovies()
            movies = movieResults
            popularMovies = movieResults
        }
    }

    fun loadPersons() {
        viewModelScope.launch {
            val peopleResults = tmdbService.getDefaultPeople()
            people = peopleResults
            popularPeople = peopleResults
        }
    }

    // Search Functions
    fun searchMovies() {
        viewModelScope.launch {
            movies = tmdbService.searchMovies(searchQuery)
        }
    }

    fun searchPeople() {
        viewModelScope.launch {
            people = tmdbService.searchPeople(searchQuery)
        }
    }

    fun clearSearchResults() {
        movies = popularMovies
        people = popularPeople
    }

    // Movie Detail Functions
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

    // Person Detail Functions
    fun selectPerson(personId: Int) {
        selectedPersonId = personId
        selectedPerson = null
        loadPersonDetails()
        navigateTo(ScreenID.DETAIL)
    }

    fun loadPersonDetails() {
        if (selectedPersonId == 0) return

        viewModelScope.launch {
            selectedPerson = tmdbService.getPersonDetails(selectedPersonId)
        }
    }

    // Favorite Functions
    fun toggleFavorite(movie: Movie) {
        if (movie in favoriteMovies) {
            favoriteMovies = favoriteMovies-movie
        } else {
            favoriteMovies = favoriteMovies+movie
        }
    }


    init {
        loadMovies()
        loadPersons()
    }
}