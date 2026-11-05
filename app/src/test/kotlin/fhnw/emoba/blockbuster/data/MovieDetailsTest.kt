package fhnw.emoba.blockbuster.data

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class MovieDetailsTest {

    @Test
    fun constructor_parsesAllFields_andBuildsImageUrls() {
        val details = MovieDetails(
            JSONObject(
                """
                {
                  "adult": false,
                  "backdrop_path": "/detail-backdrop.jpg",
                  "budget": 165000000,
                  "homepage": "https://example.com/inception",
                  "id": 101,
                  "imdb_id": "tt1375666",
                  "original_language": "en",
                  "original_title": "Inception",
                  "overview": "A mind-bending thriller.",
                  "popularity": 99.5,
                  "poster_path": "/detail-poster.jpg",
                  "release_date": "2010-07-16",
                  "revenue": 836800000,
                  "runtime": 148,
                  "status": "Released",
                  "tagline": "Your mind is the scene of the crime.",
                  "title": "Inception",
                  "video": false,
                  "vote_average": 8.8,
                  "vote_count": 21000
                }
                """.trimIndent()
            )
        )

        assertFalse(details.adult)
        assertEquals("/detail-backdrop.jpg", details.backdrop_path)
        assertEquals(165000000L, details.budget)
        assertEquals("https://example.com/inception", details.homepage)
        assertEquals(101, details.id)
        assertEquals("tt1375666", details.imdb_id)
        assertEquals("en", details.original_language)
        assertEquals("Inception", details.original_title)
        assertEquals("A mind-bending thriller.", details.overview)
        assertEquals(99.5, details.popularity, 0.0)
        assertEquals("/detail-poster.jpg", details.poster_path)
        assertEquals("2010-07-16", details.release_date)
        assertEquals(836800000L, details.revenue)
        assertEquals(148, details.runtime)
        assertEquals("Released", details.status)
        assertEquals("Your mind is the scene of the crime.", details.tagline)
        assertEquals("Inception", details.title)
        assertFalse(details.video)
        assertEquals(8.8, details.vote_average, 0.0)
        assertEquals(21000, details.vote_count)
        assertEquals("https://image.tmdb.org/t/p/w500/detail-poster.jpg", details.posterUrl)
        assertEquals("https://image.tmdb.org/t/p/w780/detail-backdrop.jpg", details.backdropUrl)
    }

    @Test
    fun directConstructor_withBlankPaths_returnsEmptyImageUrls() {
        val details = MovieDetails(
            poster_path = "",
            backdrop_path = ""
        )

        assertEquals("", details.posterUrl)
        assertEquals("", details.backdropUrl)
    }

    @Test
    fun constructor_withOnlyRequiredAdultField_usesDefaultValuesForOptionalFields() {
        val details = MovieDetails(
            JSONObject(
                """
                {
                  "adult": true
                }
                """.trimIndent()
            )
        )

        assertEquals("", details.backdrop_path)
        assertEquals(0L, details.budget)
        assertEquals("", details.homepage)
        assertEquals(0, details.id)
        assertEquals("", details.title)
        assertEquals(0.0, details.vote_average, 0.0)
        assertEquals("", details.posterUrl)
        assertEquals("", details.backdropUrl)
    }
}
