package com.example.tmdbpeople.dagger.modules

import com.example.tmdbpeople.views.adapters.PersonAdapter
import com.example.tmdbpeople.views.adapters.PersonDetailsAdapter
import dagger.Module
import dagger.Provides

@Module
class OnItemClickedImageModule(var onItemClicked: PersonDetailsAdapter.OnItemClicked) {

    @Provides
    fun providesOnItemClicked(): PersonDetailsAdapter.OnItemClicked {
        return onItemClicked
    }

}