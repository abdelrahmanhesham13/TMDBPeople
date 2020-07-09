package com.example.tmdbpeople.dagger.modules.network

import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.PersonsService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkServiceModule {

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val requestInterceptor = Interceptor{ chain ->
            // Interceptor take only one argument which is a lambda function so parenthesis can be omitted
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter(Constants.API_KEYWORD, Constants.API_KEY_VALUE)
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request) //explicitly return a value from which @annotation. Lambda always returns the value of the last expression implicitly

        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
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