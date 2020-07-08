package com.example.tmdbpeople.networkutils

import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonsService {

    @GET(Constants.POPULAR_PATH)
    fun listPopularPersons(@Query(Constants.API_KEYWORD) apiKey : String,
                           @Query(Constants.PAGE_KEY) page : Int) : Call<PopularPersonResponse>
}