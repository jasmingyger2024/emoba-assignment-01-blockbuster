package fhnw.emoba.blockbuster.data

import org.json.JSONObject

// AI Generated based on output preview on https://developer.themoviedb.org/reference/movie-videos
data class Trailer(
    val id: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int,
    val type: String,
    val official: Boolean,
    val published_at: String
)
{
    constructor(json: JSONObject) : this(
        id = json.optString("id"),
        iso_639_1 = json.optString("iso_639_1"),
        iso_3166_1 = json.optString("iso_3166_1"),
        name = json.optString("name"),
        key = json.optString("key"),
        site = json.optString("site"),
        size = json.optInt("size"),
        type = json.optString("type"),
        official = json.optBoolean("official"),
        published_at = json.optString("published_at")
    )

}