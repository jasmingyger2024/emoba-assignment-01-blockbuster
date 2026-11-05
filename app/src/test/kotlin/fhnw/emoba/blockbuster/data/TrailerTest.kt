package fhnw.emoba.blockbuster.data

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TrailerTest {

    @Test
    fun constructor_parsesAllFields() {
        val trailer = Trailer(
            JSONObject(
                """
                {
                  "id": "abc123",
                  "iso_639_1": "en",
                  "iso_3166_1": "US",
                  "name": "Official Trailer",
                  "key": "xyz789",
                  "site": "YouTube",
                  "size": 1080,
                  "type": "Trailer",
                  "official": true,
                  "published_at": "2024-01-10T12:00:00.000Z"
                }
                """.trimIndent()
            )
        )

        assertEquals("abc123", trailer.id)
        assertEquals("en", trailer.iso_639_1)
        assertEquals("US", trailer.iso_3166_1)
        assertEquals("Official Trailer", trailer.name)
        assertEquals("xyz789", trailer.key)
        assertEquals("YouTube", trailer.site)
        assertEquals(1080, trailer.size)
        assertEquals("Trailer", trailer.type)
        assertTrue(trailer.official)
        assertEquals("2024-01-10T12:00:00.000Z", trailer.published_at)
    }

    @Test
    fun constructor_withMissingFields_usesOptDefaults() {
        val trailer = Trailer(JSONObject("{}"))

        assertEquals("", trailer.id)
        assertEquals("", trailer.iso_639_1)
        assertEquals("", trailer.iso_3166_1)
        assertEquals("", trailer.name)
        assertEquals("", trailer.key)
        assertEquals("", trailer.site)
        assertEquals(0, trailer.size)
        assertEquals("", trailer.type)
        assertFalse(trailer.official)
        assertEquals("", trailer.published_at)
    }
}
