package com.example.tmdbpeople.dagger.modules.network

import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.PersonsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkServiceModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): PersonsService {
        return retrofit
            .create(PersonsService::class.java)
    }

}