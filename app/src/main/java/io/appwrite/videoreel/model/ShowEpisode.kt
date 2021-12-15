package io.appwrite.videoreel.model

import com.google.gson.annotations.SerializedName

data class ShowEpisode(
    @SerializedName("\$id")
    val id: String,
    val showSeasonId: String,
    val sortIndex: UShort,
    val name: String,
    val description: String,
    val releaseYear: UShort,
    val durationMinutes: UShort,
) {
    constructor(map: Map<String, Any>) : this(
        id = map["\$id"].toString(),
        showSeasonId = map["showSeasonId"].toString(),
        sortIndex = map["sortIndex"] as UShort,
        name = map["name"].toString(),
        description = map["description"].toString(),
        releaseYear = map["releaseYear"] as UShort,
        durationMinutes = map["releaseYear"] as UShort
    )
}