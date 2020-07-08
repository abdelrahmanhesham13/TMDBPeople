package com.example.tmdbpeople.models.responsemodels

import com.example.tmdbpeople.models.PersonModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PopularPersonResponse {
    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null
    @SerializedName("results")
    @Expose
    var persons: List<PersonModel>? = null
}