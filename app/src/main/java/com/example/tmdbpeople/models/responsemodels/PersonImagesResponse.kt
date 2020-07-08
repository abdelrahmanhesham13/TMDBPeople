package com.example.tmdbpeople.models.responsemodels

import com.example.tmdbpeople.models.PersonImage
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PersonImagesResponse {
    @SerializedName("profiles")
    @Expose
    var profiles: List<PersonImage>? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null

}