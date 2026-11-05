package fhnw.emoba.blockbuster.data

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PersonDetailsTest {

    @Test
    fun constructor_parsesAllFields() {
        val details = PersonDetails(
            JSONObject(
                """
                {
                  "id": 33,
                  "name": "Matthew McConaughey",
                  "biography": "Award-winning actor.",
                  "birthday": "1969-11-04",
                  "deathday": null,
                  "place_of_birth": "Uvalde, Texas, USA",
                  "profile_path": "/profile.jpg",
                  "popularity": 55.4,
                  "gender": 2,
                  "imdb_id": "nm0000190",
                  "homepage": "https://example.com/matthew",
                  "also_known_as": ["Matt", "McConaughey"],
                  "adult": false
                }
                """.trimIndent()
            )
        )

        assertEquals(33, details.id)
        assertEquals("Matthew McConaughey", details.name)
        assertEquals("Award-winning actor.", details.biography)
        assertEquals("1969-11-04", details.birthday)
        assertNull(details.deathday)
        assertEquals("Uvalde, Texas, USA", details.place_of_birth)
        assertEquals("/profile.jpg", details.profile_path)
        assertEquals(55.4, details.popularity, 0.0)
        assertEquals(2, details.gender)
        assertEquals("nm0000190", details.imdb_id)
        assertEquals("https://example.com/matthew", details.homepage)
        assertEquals(listOf("Matt", "McConaughey"), details.also_known_as)
        assertFalse(details.adult)
    }

    @Test
    fun constructor_withMissingOptionalFields_usesDefaults() {
        val details = PersonDetails(
            JSONObject(
                """
                {
                  "id": 44,
                  "name": "Minimal Person"
                }
                """.trimIndent()
            )
        )

        assertEquals(44, details.id)
        assertEquals("Minimal Person", details.name)
        assertEquals("", details.biography)
        assertNull(details.birthday)
        assertNull(details.deathday)
        assertNull(details.place_of_birth)
        assertNull(details.profile_path)
        assertEquals(0.0, details.popularity, 0.0)
        assertEquals(0, details.gender)
        assertNull(details.imdb_id)
        assertNull(details.homepage)
        assertTrue(details.also_known_as.isEmpty())
        assertFalse(details.adult)
    }
}
