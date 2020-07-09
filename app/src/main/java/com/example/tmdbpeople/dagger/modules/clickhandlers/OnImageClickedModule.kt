package com.example.tmdbpeople.dagger.modules.clickhandlers

import com.example.tmdbpeople.views.adapters.PersonAdapter
import com.example.tmdbpeople.views.adapters.PersonDetailsAdapter
import dagger.Module
import dagger.Provides

@Module
class OnImageClickedModule(var onImageClicked: PersonDetailsAdapter.OnImageClicked) {

    @Provides
    fun providesOnImageClicked(): PersonDetailsAdapter.OnImageClicked {
        return onImageClicked
    }

}