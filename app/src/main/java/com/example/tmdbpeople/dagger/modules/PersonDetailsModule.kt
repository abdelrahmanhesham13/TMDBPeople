package com.example.tmdbpeople.dagger.modules

import com.example.tmdbpeople.models.PersonImage
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import dagger.Module
import dagger.Provides
import java.util.ArrayList

@Module
class PersonDetailsModule {
    @Provides
    fun providePersonDetails(): PersonDetailsResponse {
        return PersonDetailsResponse()
    }
}