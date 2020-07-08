package com.example.tmdbpeople.networkutils

import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonsService {

    @GET(Constants.POPULAR_PATH)
    fun listPopularPersons(@Query(Constants.API_KEYWORD) apiKey : String,
                           @Query(Constants.PAGE_KEY) page : Int) : Call<PopularPersonResponse>

    @GET(Constants.SEARCH_PATH)
    fun listPopularPersonsForSearch(@Query(Constants.API_KEYWORD) apiKey : String,
                                    @Query(Constants.PAGE_KEY) page : Int,
                                    @Query(Constants.QUERY_KEY) query : String?) : Call<PopularPersonResponse>

    @GET(Constants.DETAILS_PATH)
    fun personDetails(@Path(Constants.PERSON_ID_PATH) personId : Int,
                      @Query(Constants.API_KEYWORD) apiKey : String) : Call<PersonDetailsResponse>
}