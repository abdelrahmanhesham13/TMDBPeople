package com.example.tmdbpeople.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.PersonsService
import com.example.tmdbpeople.networkutils.RetrofitService.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularPersonsRepository private constructor() {
    private val mPersonsService: PersonsService = service
    val popularPersons = MutableLiveData<PopularPersonResponse?>()

    fun getPopularPersons(page: Int): LiveData<PopularPersonResponse?> {
        mPersonsService.listPopularPersons(
            Constants.API_KEY_VALUE,
            page
        ).enqueue(object : Callback<PopularPersonResponse?> {
            override fun onResponse(
                call: Call<PopularPersonResponse?>,
                response: Response<PopularPersonResponse?>
            ) {
                if (response.isSuccessful) {
                    popularPersons.setValue(response.body())
                } else {
                    popularPersons.setValue(null)
                }
            }

            override fun onFailure(
                call: Call<PopularPersonResponse?>,
                t: Throwable
            ) {
                t.printStackTrace()
                popularPersons.value = null
            }
        })
        return popularPersons
    }

    companion object {
        private var mPopularPersonsRepository: PopularPersonsRepository? = null
        val instance: PopularPersonsRepository?
            get() {
                if (mPopularPersonsRepository == null) {
                    mPopularPersonsRepository =
                        PopularPersonsRepository()
                }
                return mPopularPersonsRepository
            }
    }

}