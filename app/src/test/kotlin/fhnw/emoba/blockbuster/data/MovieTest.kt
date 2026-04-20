package fhnw.emoba.blockbuster.data

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class MovieTest {

    @Test
    fun constructor_parsesAllFields_andBuildsImageUrls() {
        val movie = Movie(
            JSONObject(
                """
                {
                  "id": 101,
                  "title": "Inception",
                  "overview": "A dream within a dream.",
                  "poster_path": "/poster.jpg",
                  "backdrop_path": "/backdrop.jpg",
                  "release_date": "2010-07-16",
                  "vote_average": 8.8,
                  "vote_count": 21000
                }
                """.trimIndent()
            )
        )

        assertEquals(101, movie.id)
        assertEquals("Inception", movie.title)
        assertEquals("A dream within a dream.", movie.overview)
        assertEquals("/poster.jpg", movie.poster_path)
        assertNull(movie.poster)
        assertEquals("/backdrop.jpg", movie.backdrop_path)
        assertEquals("2010-07-16", movie.release_date)
        assertEquals(8.8, movie.vote_average, 0.0)
        assertEquals(21000, movie.vote_count)
        assertEquals("https://image.tmdb.org/t/p/w500/poster.jpg", movie.posterUrl)
        assertEquals("https://image.tmdb.org/t/p/w780/backdrop.jpg", movie.backdropUrl)
    }

    @Test
    fun directConstructor_withNullPaths_keepsCurrentUrlBehavior() {
        val movie = Movie(
            id = 5,
            title = "No Image",
            overview = "overview",
            poster_path = null,
            poster = null,
            backdrop_path = null,
            release_date = "2026-01-01",
            vote_average = 0.0,
            vote_count = 0
        )

        assertEquals("https://image.tmdb.org/t/p/w500null", movie.posterUrl)
        assertEquals("https://image.tmdb.org/t/p/w780null", movie.backdropUrl)
    }
}
