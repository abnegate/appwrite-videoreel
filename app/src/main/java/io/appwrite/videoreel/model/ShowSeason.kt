package io.appwrite.videoreel.model

import com.google.gson.annotations.SerializedName

data class ShowSeason(
    @SerializedName("\$id")
    val id: String,
    val showId: String,
    val sortIndex: UShort,
    val name: String,
    val description: String,
    val releaseYear: UShort,
) {
    constructor(map: Map<String, Any>) : this(
        id = map["\$id"].toString(),
        showId = map["showSeasonId"].toString(),
        sortIndex = map["sortIndex"] as UShort,
        name = map["name"].toString(),
        description = map["description"].toString(),
        releaseYear = map["releaseYear"] as UShort
    )
}