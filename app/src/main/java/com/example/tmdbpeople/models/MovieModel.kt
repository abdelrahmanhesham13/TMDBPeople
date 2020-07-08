package com.example.tmdbpeople.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieModel {
    @SerializedName("release_date")
    @Expose
    var releaseDate: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null
    @SerializedName("video")
    @Expose
    var video: Boolean? = null
    @SerializedName("media_type")
    @Expose
    var mediaType: String? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("genre_ids")
    @Expose
    var genreIds: List<Int>? = null
    @SerializedName("original_title")
    @Expose
    var originalTitle: String? = null
    @SerializedName("original_language")
    @Expose
    var originalLanguage: String? = null
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null
    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = null

}