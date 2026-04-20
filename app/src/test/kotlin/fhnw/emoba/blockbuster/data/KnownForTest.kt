package fhnw.emoba.blockbuster.data

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class KnownForTest {

    @Test
    fun constructor_parsesAllFields() {
        val knownFor = KnownFor(
            JSONObject(
                """
                {
                  "id": 77,
                  "title": "Interstellar",
                  "original_title": "Interstellar",
                  "poster_path": "/poster2.jpg",
                  "backdrop_path": "/backdrop2.jpg",
                  "media_type": "movie",
                  "release_date": "2014-11-07",
                  "vote_average": 8.6,
                  "vote_count": 19000,
                  "overview": "Space exploration.",
                  "adult": false
                }
                """.trimIndent()
            )
        )

        assertEquals(77, knownFor.id)
        assertEquals("Interstellar", knownFor.title)
        assertEquals("Interstellar", knownFor.original_title)
        assertEquals("/poster2.jpg", knownFor.poster_path)
        assertEquals("/backdrop2.jpg", knownFor.backdrop_path)
        assertEquals("movie", knownFor.media_type)
        assertEquals("2014-11-07", knownFor.release_date)
        assertEquals(8.6, knownFor.vote_average, 0.0)
        assertEquals(19000, knownFor.vote_count)
        assertEquals("Space exploration.", knownFor.overview)
        assertFalse(knownFor.adult)
    }

    @Test
    fun constructor_withMissingOptionalStrings_usesEmptyStrings() {
        val knownFor = KnownFor(
            JSONObject(
                """
                {
                  "id": 88,
                  "media_type": "tv",
                  "vote_average": 0.0,
                  "vote_count": 0,
                  "overview": "",
                  "adult": true
                }
                """.trimIndent()
            )
        )

        assertEquals("", knownFor.title)
        assertEquals("", knownFor.original_title)
        assertEquals("", knownFor.poster_path)
        assertEquals("", knownFor.backdrop_path)
        assertEquals("", knownFor.release_date)
        assertTrue(knownFor.adult)
    }
}
