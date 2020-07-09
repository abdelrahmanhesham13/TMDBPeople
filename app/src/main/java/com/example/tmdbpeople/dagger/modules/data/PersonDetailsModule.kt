package com.example.tmdbpeople.dagger.modules.data

import com.example.tmdbpeople.models.PersonModel
import dagger.Module
import dagger.Provides

@Module
class PersonDetailsModule {
    @Provides
    fun providePersonDetails(): PersonModel {
        return PersonModel()
    }
}