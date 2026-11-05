package fhnw.emoba.blockbuster.data

import androidx.compose.ui.graphics.ImageBitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.json.JSONException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import java.net.URLStreamHandler
import java.net.URLStreamHandlerFactory
import java.util.Base64
import java.util.concurrent.ConcurrentHashMap

@RunWith(AndroidJUnit4::class)
class TmdbServiceTest {

    private lateinit var service: TmdbService

    @Before
    fun setUp() {
        FakeHttps.install()
        FakeHttps.clear()
        service = TmdbService()
    }

    @Test
    fun requestPoster_returnsImageBitmap_whenImageCanBeLoaded() = runBlocking {
        FakeHttps.respondWithBytes(
            "https://image.tmdb.org/t/p/w185/poster.png",
            tinyPngBytes()
        )

        val poster = service.requestPoster("/poster.png")

        assertEquals(1, poster.width)
        assertEquals(1, poster.height)
    }

    @Test
    fun requestPoster_whenImageRequestFails_throwsClassCastException() = runBlocking {
        FakeHttps.respondWithError(
            "https://image.tmdb.org/t/p/w185/missing.png",
            IOException("missing image")
        )

        assertThrows(ClassCastException::class.java) {
            runBlocking {
                service.requestPoster("/missing.png")
            }
        }
    }

    @Test
    fun getDefaultMovies_parsesMovies_andLoadsPosterBitmap() = runBlocking {
        FakeHttps.respondWithJson(
            apiUrl("movie/popular?language=en-US&page=1&api_key=${token()}"),
            """
            {
              "results": [
                {
                  "id": 10,
                  "title": "Popular Movie",
                  "overview": "overview",
                  "poster_path": "/poster.png",
                  "backdrop_path": "/backdrop.png",
                  "release_date": "2025-01-01",
                  "vote_average": 7.5,
                  "vote_count": 900
                }
              ]
            }
            """.trimIndent()
        )
        FakeHttps.respondWithBytes(
            "https://image.tmdb.org/t/p/w185/poster.png",
            tinyPngBytes()
        )

        val movies = service.getDefaultMovies()

        assertEquals(1, movies.size)
        assertEquals("Popular Movie", movies.first().title)
        assertNotNull(movies.first().poster)
    }

    @Test
    fun getDefaultMovies_whenRequestFails_returnsEmptyList() = runBlocking {
        FakeHttps.respondWithError(
            apiUrl("movie/popular?language=en-US&page=1&api_key=${token()}"),
            IOException("network down")
        )

        val movies = service.getDefaultMovies()

        assertEquals(emptyList<Movie>(), movies)
    }

    @Test
    fun searchMovies_parsesMovies_withoutLoadingPosterBitmap() = runBlocking {
        FakeHttps.respondWithJson(
            apiUrl("search/movie?query=batman&include_adult=false&language=en-US&page=1&api_key=${token()}"),
            """
            {
              "results": [
                {
                  "id": 11,
                  "title": "Batman Begins",
                  "overview": "overview",
                  "poster_path": "/poster.png",
                  "backdrop_path": "/backdrop.png",
                  "release_date": "2005-06-15",
                  "vote_average": 8.2,
                  "vote_count": 15000
                }
              ]
            }
            """.trimIndent()
        )

        val movies = service.searchMovies("batman")

        assertEquals(1, movies.size)
        assertEquals("Batman Begins", movies.first().title)
        assertNull(movies.first().poster)
    }

    @Test
    fun getMovieDetails_parsesMovieDetails() = runBlocking {
        FakeHttps.respondWithJson(
            apiUrl("movie/42?language=en-US&api_key=${token()}"),
            """
            {
              "adult": false,
              "backdrop_path": "/backdrop.png",
              "budget": 150000000,
              "homepage": "https://example.com/movie",
              "id": 42,
              "imdb_id": "tt1234567",
              "original_language": "en",
              "original_title": "Original Title",
              "overview": "overview",
              "popularity": 10.5,
              "poster_path": "/poster.png",
              "release_date": "2026-01-01",
              "revenue": 300000000,
              "runtime": 120,
              "status": "Released",
              "tagline": "tagline",
              "title": "Movie Title",
              "video": false,
              "vote_average": 7.9,
              "vote_count": 1000
            }
            """.trimIndent()
        )

        val details = service.getMovieDetails(42)

        assertEquals(42, details.id)
        assertEquals("Movie Title", details.title)
        assertEquals("tagline", details.tagline)
    }

    @Test
    fun getMovieDetails_whenRequestFails_throwsJsonException() = runBlocking {
        FakeHttps.respondWithError(
            apiUrl("movie/42?language=en-US&api_key=${token()}"),
            IOException("network down")
        )

        assertThrows(JSONException::class.java) {
            runBlocking {
                service.getMovieDetails(42)
            }
        }
    }

    @Test
    fun getMovieTrailer_returnsFirstOfficialTrailer() = runBlocking {
        FakeHttps.respondWithJson(
            apiUrl("movie/42/videos?language=en-US&api_key=${token()}"),
            """
            {
              "results": [
                {
                  "id": "teaser",
                  "iso_639_1": "en",
                  "iso_3166_1": "US",
                  "name": "Teaser",
                  "key": "teaser-key",
                  "site": "YouTube",
                  "size": 1080,
                  "type": "Teaser",
                  "official": true,
                  "published_at": "2024-01-01T00:00:00.000Z"
                },
                {
                  "id": "trailer",
                  "iso_639_1": "en",
                  "iso_3166_1": "US",
                  "name": "Official Trailer",
                  "key": "trailer-key",
                  "site": "YouTube",
                  "size": 1080,
                  "type": "Trailer",
                  "official": true,
                  "published_at": "2024-01-02T00:00:00.000Z"
                }
              ]
            }
            """.trimIndent()
        )

        val trailer = requireNotNull(service.getMovieTrailer(42))

        assertEquals("trailer", trailer.id)
        assertEquals("trailer-key", trailer.key)
    }

    @Test
    fun getMovieTrailer_whenNoOfficialTrailerExists_throwsNoSuchElementException() = runBlocking {
        FakeHttps.respondWithJson(
            apiUrl("movie/42/videos?language=en-US&api_key=${token()}"),
            """
            {
              "results": [
                {
                  "id": "clip",
                  "iso_639_1": "en",
                  "iso_3166_1": "US",
                  "name": "Clip",
                  "key": "clip-key",
                  "site": "YouTube",
                  "size": 1080,
                  "type": "Clip",
                  "official": false,
                  "published_at": "2024-01-03T00:00:00.000Z"
                }
              ]
            }
            """.trimIndent()
        )

        assertThrows(NoSuchElementException::class.java) {
            runBlocking {
                service.getMovieTrailer(42)
            }
        }
    }

    @Test
    fun getDefaultPeople_parsesPeople() = runBlocking {
        FakeHttps.respondWithJson(
            apiUrl("person/popular?language=en-US&page=1&api_key=${token()}"),
            """
            {
              "results": [
                {
                  "id": 20,
                  "name": "Popular Person",
                  "adult": false,
                  "gender": 2,
                  "known_for_department": "Acting",
                  "popularity": 88.0,
                  "profile_path": "/profile.png",
                  "known_for": [
                    {
                      "id": 21,
                      "title": "Known Work",
                      "original_title": "Known Work",
                      "poster_path": "/poster.png",
                      "backdrop_path": "/backdrop.png",
                      "media_type": "movie",
                      "release_date": "2024-02-02",
                      "vote_average": 7.0,
                      "vote_count": 100,
                      "overview": "overview",
                      "adult": false
                    }
                  ]
                }
              ]
            }
            """.trimIndent()
        )

        val people = service.getDefaultPeople()

        assertEquals(1, people.size)
        assertEquals("Popular Person", people.first().name)
        assertEquals("Known Work", people.first().known_for.first().title)
    }

    @Test
    fun searchPeople_whenRequestFails_returnsEmptyList() = runBlocking {
        FakeHttps.respondWithError(
            apiUrl("search/person?query=emma&include_adult=false&language=en-US&page=1&api_key=${token()}"),
            IOException("network down")
        )

        val people = service.searchPeople("emma")

        assertEquals(emptyList<Person>(), people)
    }

    @Test
    fun getPersonDetails_parsesPersonDetails() = runBlocking {
        FakeHttps.respondWithJson(
            apiUrl("person/33?language=en-US&api_key=${token()}"),
            """
            {
              "id": 33,
              "name": "Actor Name",
              "biography": "biography",
              "birthday": "1970-01-01",
              "deathday": null,
              "place_of_birth": "Somewhere",
              "profile_path": "/profile.png",
              "popularity": 55.0,
              "gender": 2,
              "imdb_id": "nm0000001",
              "homepage": "https://example.com/person",
              "also_known_as": ["Alias 1", "Alias 2"],
              "adult": false
            }
            """.trimIndent()
        )

        val details = service.getPersonDetails(33)

        assertEquals(33, details.id)
        assertEquals("Actor Name", details.name)
        assertEquals(listOf("Alias 1", "Alias 2"), details.also_known_as)
    }

    @Test
    fun getPersonDetails_whenRequestFails_throwsJsonException() = runBlocking {
        FakeHttps.respondWithError(
            apiUrl("person/33?language=en-US&api_key=${token()}"),
            IOException("network down")
        )

        assertThrows(JSONException::class.java) {
            runBlocking {
                service.getPersonDetails(33)
            }
        }
    }

    private fun apiUrl(path: String) = "${baseUrl()}/$path"

    private fun baseUrl(): String {
        val field = TmdbService::class.java.getDeclaredField("baseUrl")
        field.isAccessible = true
        return field.get(service) as String
    }

    private fun token(): String {
        val field = TmdbService::class.java.getDeclaredField("token")
        field.isAccessible = true
        return field.get(service) as String
    }

    private fun tinyPngBytes(): ByteArray {
        return Base64.getDecoder().decode(
            "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR4nGNgYAAAAAMAASsJTYQAAAAASUVORK5CYII="
        )
    }
}

private object FakeHttps {
    private val responses = ConcurrentHashMap<String, FakeResponse>()
    private var installed = false

    fun install() {
        if (installed) return
        try {
            URL.setURLStreamHandlerFactory(FakeHttpsStreamHandlerFactory)
        } catch (_: Error) {
        }
        installed = true
    }

    fun clear() {
        responses.clear()
    }

    fun respondWithJson(url: String, json: String) {
        responses[url] = FakeResponse(bytes = json.toByteArray())
    }

    fun respondWithBytes(url: String, bytes: ByteArray) {
        responses[url] = FakeResponse(bytes = bytes)
    }

    fun respondWithError(url: String, error: IOException) {
        responses[url] = FakeResponse(error = error)
    }

    fun responseFor(url: String): FakeResponse {
        return responses[url] ?: FakeResponse(error = IOException("No fake response registered for $url"))
    }
}

private object FakeHttpsStreamHandlerFactory : URLStreamHandlerFactory {
    override fun createURLStreamHandler(protocol: String): URLStreamHandler? {
        return if (protocol == "https") FakeHttpsStreamHandler else null
    }
}

private object FakeHttpsStreamHandler : URLStreamHandler() {
    override fun openConnection(url: URL): URLConnection {
        val response = FakeHttps.responseFor(url.toString())
        return object : URLConnection(url) {
            override fun connect() {
            }

            override fun getInputStream(): InputStream {
                response.error?.let { throw it }
                return ByteArrayInputStream(response.bytes ?: ByteArray(0))
            }
        }
    }
}

private data class FakeResponse(
    val bytes: ByteArray? = null,
    val error: IOException? = null
)
