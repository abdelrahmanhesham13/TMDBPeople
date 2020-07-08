package com.example.tmdbpeople.dagger.modules

import com.example.tmdbpeople.models.PersonImage
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class PersonImageArrayListModule {
    @Provides
    fun providePersonImagesArrayList(): ArrayList<PersonImage> {
        return ArrayList()
    }
}