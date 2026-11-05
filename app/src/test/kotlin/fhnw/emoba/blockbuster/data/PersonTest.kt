package fhnw.emoba.blockbuster.data

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PersonTest {

    @Test
    fun constructor_parsesAllFields_andNestedKnownForList() {
        val person = Person(
            JSONObject(
                """
                {
                  "id": 33,
                  "name": "Matthew McConaughey",
                  "adult": false,
                  "gender": 2,
                  "known_for_department": "Acting",
                  "popularity": 55.4,
                  "profile_path": "/profile.jpg",
                  "known_for": [
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
                  ]
                }
                """.trimIndent()
            )
        )

        assertEquals(33, person.id)
        assertEquals("Matthew McConaughey", person.name)
        assertFalse(person.adult)
        assertEquals(2, person.gender)
        assertEquals("Acting", person.known_for_department)
        assertEquals(55.4, person.popularity, 0.0)
        assertEquals("/profile.jpg", person.profile_path)
        assertNull(person.poster)
        assertEquals(1, person.known_for.size)
        assertEquals("Interstellar", person.known_for.first().title)
    }

    @Test
    fun constructor_withMissingOptionalFields_usesEmptyStringsAndEmptyKnownForList() {
        val person = Person(
            JSONObject(
                """
                {
                  "id": 34,
                  "name": "Minimal Person",
                  "adult": true,
                  "gender": 0,
                  "popularity": 0.0,
                  "known_for": []
                }
                """.trimIndent()
            )
        )

        assertEquals("", person.known_for_department)
        assertEquals("", person.profile_path)
        assertTrue(person.known_for.isEmpty())
        assertTrue(person.adult)
    }
}
