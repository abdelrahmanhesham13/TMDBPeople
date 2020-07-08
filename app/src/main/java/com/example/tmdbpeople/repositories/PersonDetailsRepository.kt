package com.example.tmdbpeople.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
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
        val popularPersons = MutableLiveData<PersonDetailsResponse?>()
        mPersonsService.personDetails(
            personId,
            Constants.API_KEY_VALUE
        ).enqueue(object : Callback<PersonDetailsResponse?> {
            override fun onResponse(
                call: Call<PersonDetailsResponse?>,
                response: Response<PersonDetailsResponse?>
            ) {
                if (response.isSuccessful) {
                    popularPersons.setValue(response.body())
                } else {
                    popularPersons.setValue(null)
                }
            }

            override fun onFailure(
                call: Call<PersonDetailsResponse?>,
                t: Throwable
            ) {
                t.printStackTrace()
                popularPersons.value = null
            }
        })
        return popularPersons
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