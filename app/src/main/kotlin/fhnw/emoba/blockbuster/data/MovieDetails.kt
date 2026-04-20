package fhnw.emoba.blockbuster.data

import org.json.JSONObject

// AI Generated based on output preview on https://developer.themoviedb.org/reference/movie-details
data class MovieDetails(
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val budget: Long = 0,
    val homepage: String? = null,
    val id: Int = 0,
    val imdb_id: String? = null,
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String? = null,
    val release_date: String = "",
    val revenue: Long = 0,
    val runtime: Int = 0,
    val status: String = "",
    val tagline: String? = null,
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
) {
    val posterUrl: String
        get() = poster_path?.takeIf { it.isNotBlank() }?.let { "https://image.tmdb.org/t/p/w500$it" } ?: ""
    val backdropUrl: String
        get() = backdrop_path?.takeIf { it.isNotBlank() }?.let { "https://image.tmdb.org/t/p/w780$it" } ?: ""

    constructor(json: JSONObject) : this(
        adult = json.getBoolean("adult"),
        backdrop_path = json.optString("backdrop_path"),
        budget = json.optLong("budget"),
        homepage = json.optString("homepage"),
        id = json.optInt("id"),
        imdb_id = json.optString("imdb_id"),
        original_language = json.optString("original_language"),
        original_title = json.optString("original_title"),
        overview = json.optString("overview"),
        popularity = json.optDouble("popularity"),
        poster_path = json.optString("poster_path"),
        release_date = json.optString("release_date"),
        revenue = json.optLong("revenue"),
        runtime = json.optInt("runtime"),
        status = json.optString("status"),
        tagline = json.optString("tagline"),
        title = json.optString("title"),
        video = json.optBoolean("video"),
        vote_average = json.optDouble("vote_average"),
        vote_count = json.optInt("vote_count")
        )
}


