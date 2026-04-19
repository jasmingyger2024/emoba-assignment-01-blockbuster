package fhnw.emoba.blockbuster.data

import org.json.JSONObject

// AI Generated based on output preview on https://developer.themoviedb.org/reference/person-details
data class PersonDetails(
    val id: Int,
    val name: String,
    val biography: String,
    val birthday: String?,
    val deathday: String?,
    val place_of_birth: String?,
    val profile_path: String?,
    val popularity: Double,
    val gender: Int,
    val imdb_id: String?,
    val homepage: String?,
    val also_known_as: List<String>,
    val adult: Boolean
) {
    constructor(json: JSONObject) : this(
        id = json.getInt("id"),
        name = json.getString("name"),
        biography = json.optString("biography", ""),
        birthday = json.optString("birthday", null),
        deathday = json.optString("deathday", null),
        place_of_birth = json.optString("place_of_birth", null),
        profile_path = json.optString("profile_path", null),
        popularity = json.optDouble("popularity", 0.0),
        gender = json.optInt("gender", 0),
        imdb_id = json.optString("imdb_id", null),
        homepage = json.optString("homepage", null),
        also_known_as = json.optJSONArray("also_known_as")?.let { array ->
            List(array.length()) { array.getString(it) }
        } ?: emptyList(),
        adult = json.optBoolean("adult", false)
    )
}

