package io.appwrite.videoreel.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("\$id")
    val id: String,
    val name: String,
    val description: String,
    val thumbnailImageId: String,
    val cast: List<String>,
    val tags: List<String>,
    val genres: List<String>,
    val releaseYear: Long,
    val durationMinutes: Long,
    val ageRestriction: AgeRestriction,
) {
    constructor(map: Map<String, Any>) : this(
        id = map["\$id"].toString(),
        name = map["name"].toString(),
        description = map["description"].toString(),
        thumbnailImageId = map["thumbnailImageId"].toString(),
        cast = map["cast"] as List<String>,
        tags = map["tags"] as List<String>,
        genres = map["genres"] as List<String>,
        releaseYear = map["releaseYear"] as Long,
        durationMinutes = map["durationMinutes"] as Long,
        ageRestriction = AgeRestriction.valueOf(map["ageRestriction"].toString())
    )
}

