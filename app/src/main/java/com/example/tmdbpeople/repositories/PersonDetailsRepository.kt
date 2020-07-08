package com.example.tmdbpeople.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.models.responsemodels.PersonImagesResponse
import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.PersonsService
import com.example.tmdbpeople.networkutils.RetrofitService.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonDetailsRepository private constructor() {
    private val mPersonsService: PersonsService = service
    
    fun getPersonDetails(personId: Int): LiveData<PersonDetailsResponse?> {
        val personDetails = MutableLiveData<PersonDetailsResponse?>()
        mPersonsService.personDetails(
            personId,
            Constants.API_KEY_VALUE
        ).enqueue(object : Callback<PersonDetailsResponse?> {
            override fun onResponse(
                call: Call<PersonDetailsResponse?>,
                response: Response<PersonDetailsResponse?>
            ) {
                if (response.isSuccessful) {
                    personDetails.setValue(response.body())
                } else {
                    personDetails.setValue(null)
                }
            }

            override fun onFailure(
                call: Call<PersonDetailsResponse?>,
                t: Throwable
            ) {
                t.printStackTrace()
                personDetails.value = null
            }
        })
        return personDetails
    }

    fun getPersonImages(personId: Int): LiveData<PersonImagesResponse?> {
        val personImages = MutableLiveData<PersonImagesResponse?>()
        mPersonsService.personImages(
            personId,
            Constants.API_KEY_VALUE
        ).enqueue(object : Callback<PersonImagesResponse?> {
            override fun onResponse(
                call: Call<PersonImagesResponse?>,
                response: Response<PersonImagesResponse?>
            ) {
                if (response.isSuccessful) {
                    personImages.setValue(response.body())
                } else {
                    personImages.setValue(null)
                }
            }

            override fun onFailure(
                call: Call<PersonImagesResponse?>,
                t: Throwable
            ) {
                t.printStackTrace()
                personImages.value = null
            }
        })
        return personImages
    }

    companion object {
        private var mPopularPersonsRepository: PersonDetailsRepository? = null
        val instance: PersonDetailsRepository?
            get() {
                if (mPopularPersonsRepository == null) {
                    mPopularPersonsRepository =
                        PersonDetailsRepository()
                }
                return mPopularPersonsRepository
            }
    }

}