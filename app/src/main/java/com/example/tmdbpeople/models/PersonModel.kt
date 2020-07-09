package com.example.tmdbpeople.models

import com.example.tmdbpeople.R
import com.example.tmdbpeople.utils.networkutils.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PersonModel {
    @SerializedName("birthday")
    @Expose
    var birthday: String? = null
    @SerializedName("known_for_department")
    @Expose
    var knownForDepartment: String? = null
    @SerializedName("deathday")
    @Expose
    var deathday: Any? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("also_known_as")
    @Expose
    var alsoKnownAs: List<String>? = null
    @SerializedName("gender")
    @Expose
    var gender: Int? = null
    @SerializedName("biography")
    @Expose
    var biography: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: Double? = null
    @SerializedName("place_of_birth")
    @Expose
    var placeOfBirth: String? = null
    @SerializedName("profile_path")
    @Expose
    var profilePath: String? = null
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null
    @SerializedName("imdb_id")
    @Expose
    var imdbId: String? = null
    @SerializedName("homepage")
    @Expose
    var homepage: Any? = null
    @SerializedName("known_for")
    @Expose
    var movies: List<MovieModel>? = null

    fun getGenderStringResId() : Int {
        return if (gender == 1) {
            R.string.female
        } else {
            R.string.male
        }
    }

    override fun equals(other: Any?): Boolean {
        return this === other as PersonModel
    }

    fun getImageFullPath() : String {
        return Constants.IMAGE_BASE_URL_500W + profilePath
    }

}