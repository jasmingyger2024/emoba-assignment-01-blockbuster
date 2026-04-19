package fhnw.emoba.blockbuster.data

import org.json.JSONObject

data class Person(
    val id: Int,
    val name: String,
    val adult: Boolean,
    val gender: Int,
    val known_for_department: String,
    val popularity: Double,
    val profile_path: String?,
    val known_for: List<KnownFor>
) {
    constructor(json: JSONObject) : this (
        id = json.getInt("id"),
        name = json.getString("name"),
        adult = json.getBoolean("adult"),
        gender = json.getInt("gender"),
        known_for_department = json.optString("known_for_department"),
        popularity = json.getDouble("popularity"),
        profile_path = json.optString("profile_path"),
        known_for = json.getJSONArray("known_for").let { knownForArray ->
            List(knownForArray.length()) { index ->
                KnownFor(knownForArray.getJSONObject(index))
            }
        }
    )

}

data class KnownFor(
    val id: Int,
    val title: String?,
    val original_title: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val media_type: String,
    val release_date: String?,
    val vote_average: Double,
    val vote_count: Int,
    val overview: String,
    val adult: Boolean = false
) {
    constructor(json: JSONObject) : this (
    id = json.getInt("id"),
    title = json.optString("title"),
    original_title = json.optString("original_title"),
    poster_path = json.optString("poster_path"),
    backdrop_path = json.optString("backdrop_path"),
    media_type = json.getString("media_type"),
    release_date = json.optString("release_date"),
    vote_average = json.getDouble("vote_average"),
    vote_count = json.getInt("vote_count"),
    overview = json.getString("overview"),
    adult = json.getBoolean("adult")
    )
}
