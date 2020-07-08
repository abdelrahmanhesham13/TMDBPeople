package com.example.tmdbpeople.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tmdbpeople.models.PersonImage
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.models.responsemodels.PersonImagesResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.PersonsService
import com.example.tmdbpeople.networkutils.RetrofitService.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonDetailsRepository private constructor() {
    private val mPersonsService: PersonsService = service

    //Function to call person details api and returns LiveData object to observe changes and get person details response
    fun getPersonDetails(personId: Int): LiveData<PersonModel?> {
        val personDetails = MutableLiveData<PersonModel?>()
        mPersonsService.personDetails(
            personId,
            Constants.API_KEY_VALUE
        ).enqueue(object : Callback<PersonModel?> {
            override fun onResponse(
                call: Call<PersonModel?>,
                response: Response<PersonModel?>
            ) {
                if (response.isSuccessful) {
                    personDetails.value = response.body()
                } else {
                    personDetails.value = null
                }
            }

            override fun onFailure(
                call: Call<PersonModel?>,
                t: Throwable
            ) {
                t.printStackTrace()
                personDetails.value = null
            }
        })
        return personDetails
    }

    //Function to call person images api and returns LiveData object to observe changes and get person images response
    fun getPersonImages(personId: Int): LiveData<PersonImagesResponse?> {
        val personImages = MutableLiveData<PersonImagesResponse?>()
        mPersonsService.personImages(personId, Constants.API_KEY_VALUE).enqueue(object : Callback<PersonImagesResponse?> {
            override fun onResponse(call: Call<PersonImagesResponse?>, response: Response<PersonImagesResponse?>) {
                if (response.isSuccessful) {
                    (response.body()?.profiles as ArrayList<PersonImage>).add(0,PersonImage())
                    personImages.value = response.body()
                } else {
                    personImages.value = null
                }
            }

            override fun onFailure(call: Call<PersonImagesResponse?>, t: Throwable) {
                t.printStackTrace()
                personImages.value = null
            }
        })
        return personImages
    }

    //Singleton for repository
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