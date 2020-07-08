package com.example.tmdbpeople.networkutils

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//Retrofit Service Singleton Class
object RetrofitService {
    private var retrofit: Retrofit? = null
    private val client: Retrofit?

        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

    val service: PersonsService
        get() = client!!.create(PersonsService::class.java)
}