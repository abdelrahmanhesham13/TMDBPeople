package com.example.tmdbpeople.models

import com.example.tmdbpeople.networkutils.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PersonImage {
    @SerializedName("iso_639_1")
    @Expose
    var iso6391: Any? = null
    @SerializedName("width")
    @Expose
    var width: Int? = null
    @SerializedName("height")
    @Expose
    var height: Int? = null
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int? = null
    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double? = null
    @SerializedName("file_path")
    @Expose
    var filePath: String? = null
    @SerializedName("aspect_ratio")
    @Expose
    var aspectRatio: Double? = null

    fun getImageFullPath() : String {
        return Constants.IMAGE_BASE_URL_500W + filePath
    }

}