package com.example.tmdbpeople.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PersonModel {
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("known_for_department")
    @Expose
    var knownForDepartment: String? = null
    @SerializedName("gender")
    @Expose
    var gender: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("profile_path")
    @Expose
    var profilePath: String? = null
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null
    @SerializedName("known_for")
    @Expose
    var movies: List<MovieModel>? = null
    @SerializedName("name")
    @Expose
    var name: String? = null

}