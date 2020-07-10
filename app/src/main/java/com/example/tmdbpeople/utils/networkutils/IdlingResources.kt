package com.example.tmdbpeople.utils.networkutils

import androidx.test.espresso.IdlingRegistry
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.OkHttpClient


object IdlingResources {
    fun registerOkHttp(client: OkHttpClient) {
        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("okhttp", client))
    }
}