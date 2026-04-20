package fhnw.emoba.blockbuster.viewmodel

import androidx.compose.runtime.MutableState
import androidx.test.ext.junit.runners.AndroidJUnit4
import fhnw.emoba.blockbuster.data.KnownFor
import fhnw.emoba.blockbuster.data.Movie
import fhnw.emoba.blockbuster.data.MovieDetails
import fhnw.emoba.blockbuster.data.Person
import fhnw.emoba.blockbuster.data.PersonDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class BlockbusterModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var model: BlockbusterModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        model = BlockbusterModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun navigateTo_updatesCurrentAndPreviousScreen() {
        model.navigateTo(ScreenID.FAVORITES)

        assertEquals(ScreenID.MOVIES, model.previousScreen)
        assertEquals(ScreenID.FAVORITES, model.selectedScreen)
    }

    @Test
    fun navigateBack_restoresThePreviousScreen() {
        model.navigateTo(ScreenID.PEOPLE)

        model.navigateBack()

        assertEquals(ScreenID.MOVIES, model.selectedScreen)
    }

    @Test
    fun clearSearchResults_resetsQueryAndRestoresPopularLists() {
        val popularMovies = listOf(movie(id = 1, title = "Popular Movie"))
        val popularPeople = listOf(person(id = 7, name = "Popular Person"))

        setStateProperty("movies", listOf(movie(id = 2, title = "Search Result")))
        setStateProperty("popularMovies", popularMovies)
        setStateProperty("people", listOf(person(id = 8, name = "Search Person")))
        setStateProperty("popularPeople", popularPeople)
        model.searchQuery = "batman"

        model.clearSearchResults()

        assertEquals("", model.searchQuery)
        assertEquals(popularMovies, model.movies)
        assertEquals(popularPeople, model.people)
    }

    @Test
    fun selectMovie_setsIdClearsSelectionAndNavigatesToDetail() {
        model.selectedMovie = MovieDetails(id = 99, title = "Already Loaded")

        model.selectMovie(42)

        assertEquals(42, model.selectedMovieId)
        assertNull(model.selectedMovie)
        assertEquals(ScreenID.MOVIES, model.previousScreen)
        assertEquals(ScreenID.DETAIL, model.selectedScreen)
    }

    @Test
    fun selectPerson_setsIdClearsSelectionAndNavigatesToPersonDetail() {
        model.selectedScreen = ScreenID.PEOPLE
        model.selectedPerson = PersonDetails(
            id = 12,
            name = "Existing Person",
            biography = "bio",
            birthday = null,
            deathday = null,
            place_of_birth = null,
            profile_path = null,
            popularity = 1.0,
            gender = 1,
            imdb_id = null,
            homepage = null,
            also_known_as = emptyList(),
            adult = false
        )

        model.selectPerson(15)

        assertEquals(15, model.selectedPersonId)
        assertNull(model.selectedPerson)
        assertEquals(ScreenID.PEOPLE, model.previousScreen)
        assertEquals(ScreenID.PERSONDETAIL, model.selectedScreen)
    }

    @Test
    fun toggleFavorite_addsMovieThatIsNotYetFavorite() {
        val movie = movie(id = 3, title = "New Favorite")

        model.toggleFavorite(movie)

        assertTrue(movie in model.favoriteMovies)
    }

    @Test
    fun toggleFavorite_removesMovieThatIsAlreadyFavorite() {
        val movie = movie(id = 4, title = "Existing Favorite")
        model.toggleFavorite(movie)

        model.toggleFavorite(movie)

        assertFalse(movie in model.favoriteMovies)
    }

    @Test
    fun loadMovieDetails_withNoSelectedMovieId_keepsCurrentSelectionUntouched() {
        val details = MovieDetails(id = 21, title = "Current Movie")
        model.selectedMovie = details
        model.selectedMovieId = 0

        model.loadMovieDetails()

        assertEquals(details, model.selectedMovie)
    }

    @Test
    fun loadPersonDetails_withNoSelectedPersonId_keepsCurrentSelectionUntouched() {
        val details = PersonDetails(
            id = 31,
            name = "Current Person",
            biography = "bio",
            birthday = null,
            deathday = null,
            place_of_birth = null,
            profile_path = null,
            popularity = 2.0,
            gender = 2,
            imdb_id = null,
            homepage = null,
            also_known_as = emptyList(),
            adult = false
        )
        model.selectedPerson = details
        model.selectedPersonId = 0

        model.loadPersonDetails()

        assertEquals(details, model.selectedPerson)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> setStateProperty(propertyName: String, value: T) {
        val field = BlockbusterModel::class.java.getDeclaredField("${propertyName}\$delegate")
        field.isAccessible = true
        val state = field.get(model) as MutableState<T>
        state.value = value
    }

    private fun movie(id: Int, title: String) = Movie(
        id = id,
        title = title,
        overview = "overview",
        poster_path = null,
        poster = null,
        backdrop_path = null,
        release_date = "2026-01-01",
        vote_average = 8.5,
        vote_count = 100
    )

    private fun person(id: Int, name: String) = Person(
        id = id,
        name = name,
        adult = false,
        gender = 1,
        known_for_department = "Acting",
        popularity = 10.0,
        profile_path = null,
        poster = null,
        known_for = listOf(
            KnownFor(
                id = 1,
                title = "Known Work",
                original_title = "Known Work",
                poster_path = null,
                backdrop_path = null,
                media_type = "movie",
                release_date = "2026-01-01",
                vote_average = 7.0,
                vote_count = 10,
                overview = "overview",
                adult = false
            )
        )
    )
}
