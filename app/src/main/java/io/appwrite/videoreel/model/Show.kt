package io.appwrite.videoreel.model

import com.google.gson.annotations.SerializedName

data class Show(
    @SerializedName("\$id")
    val id: String,
    val name: String,
    val description: String,
    val thumbnailImageId: String,
    val cast: String,
    val tags: String,
    val genres: String,
    val releaseYear: UShort = 0u,
) {
    constructor(map: Map<String, Any>) : this(
        id = map["\$id"].toString(),
        name = map["name"].toString(),
        description = map["description"].toString(),
        thumbnailImageId = map["thumbnailImageId"].toString(),
        cast = map["cast"].toString(),
        tags = map["tags"].toString(),
        genres = map["genres"].toString(),
        releaseYear = map["releaseYear"] as UShort
    )
}