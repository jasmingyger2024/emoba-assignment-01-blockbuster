package fhnw.emoba.blockbuster.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets


class TmdbService {

    private val baseUrl = "https://api.themoviedb.org/3"
    private val token = "3d97d9d2541c14791107e7d0da0b1bd9"

    private suspend fun request(path: String): JSONArray {
        return withContext(Dispatchers.IO) {
            var url = "$baseUrl/$path"

            try {
                val response = URL(url).readText()
                val json = JSONObject(response)
                val results = json.getJSONArray("results")
                results
            } catch (e: Exception) {
                println(e.message)
                JSONArray()
            }
        }
    }

    private suspend fun requestDetails(path: String): JSONObject {
        return withContext(Dispatchers.IO) {
            var url = "$baseUrl/$path"

            try {
                val response = URL(url).readText()
                val results = JSONObject(response)
                results
            } catch (e: Exception) {
                println(e.message)
                JSONObject()
            }
        }
    }

    // Movies
    suspend fun getDefaultMovies() : List<Movie> {
        return withContext(Dispatchers.IO) {
            val results = request("movie/popular?language=en-US&page=1&api_key=$token")
            val movies = mutableListOf<Movie>()
            for (i in 0 until results.length()) {
                val movie = results.getJSONObject(i)
                movies.add(Movie(movie))
            }
            movies
        }
    }

    suspend fun searchMovies(query: String) : List<Movie> {
        return withContext(Dispatchers.IO) {
            val results = request("search/movie?query=$query&include_adult=false&language=en-US&page=1&api_key=$token")
            val movies = mutableListOf<Movie>()
            for (i in 0 until results.length()) {
                val movie = results.getJSONObject(i)
                movies.add(Movie(movie))
            }
            movies
        }
    }

    suspend fun getMovieDetails(movieId : Int) : MovieDetails {
        return withContext(Dispatchers.IO) {
            val results = requestDetails("movie/$movieId?language=en-US&api_key=$token")

            val movieDetail = MovieDetails(results)
            movieDetail
        }
    }

    suspend fun getMovieTrailer(movieId : Int) : Trailer {
        return withContext(Dispatchers.IO) {
            val results = request("movie/$movieId/videos?language=en-US&api_key=$token")

            val trailers = mutableListOf<Trailer>()
            for (i in 0 until results.length()) {
                val trailer = results.getJSONObject(i)
                trailers.add(Trailer(trailer))
            }
            trailers.filter { it.official && it.type == "Trailer" }.first()
        }
    }


    // People
    suspend fun getDefaultPeople() : List<Person> {
        return withContext(Dispatchers.IO) {
            val results = request("person/popular?language=en-US&page=1&api_key=$token")
            val persons = mutableListOf<Person>()
            for (i in 0 until results.length()) {
                val person = results.getJSONObject(i)
                persons.add(Person(person))
            }
            persons
        }
    }

    fun searchPeople() : List<Person> {
        return listOf()
    }

    fun getPersonDetails() : Int {
        return 0
    }


}
