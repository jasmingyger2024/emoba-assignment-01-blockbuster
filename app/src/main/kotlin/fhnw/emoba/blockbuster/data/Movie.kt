package fhnw.emoba.blockbuster.data

import androidx.compose.ui.graphics.ImageBitmap
import org.json.JSONObject

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    var poster: ImageBitmap?,
    val backdrop_path: String?,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int,

    ) {
    val posterUrl: String get() = "https://image.tmdb.org/t/p/w500$poster_path"
    val backdropUrl: String get() = "https://image.tmdb.org/t/p/w780$backdrop_path"

    constructor(json : JSONObject) : this(
        id = json.getInt("id"),
        title = json.getString("title"),
        overview = json.getString("overview"),
        poster_path = json.getString("poster_path"),
        poster = null,
        backdrop_path = json.getString("backdrop_path"),
        release_date = json.getString("release_date"),
        vote_average = json.getDouble("vote_average"),
        vote_count = json.getInt("vote_count"),
    )
}

