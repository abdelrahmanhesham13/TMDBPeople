package com.example.tmdbpeople.networkutils

import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.models.responsemodels.PersonImagesResponse
import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonsService {

    //Call function for popular persons
    @GET(Constants.POPULAR_PATH)
    fun listPopularPersons(@Query(Constants.PAGE_KEY) page : Int) : Single<PopularPersonResponse>

    //Call function for search for person
    @GET(Constants.SEARCH_PATH)
    fun listPopularPersonsForSearch(@Query(Constants.PAGE_KEY) page : Int,
                                    @Query(Constants.QUERY_KEY) query : String?) : Single<PopularPersonResponse>

    //Call function for person details
    @GET(Constants.DETAILS_PATH)
    fun personDetails(@Path(Constants.PERSON_ID_PATH) personId : Int) : Single<PersonModel>


    //Call function for person images
    @GET(Constants.IMAGES_PATH)
    fun personImages(@Path(Constants.PERSON_ID_PATH) personId : Int) : Single<PersonImagesResponse>
}