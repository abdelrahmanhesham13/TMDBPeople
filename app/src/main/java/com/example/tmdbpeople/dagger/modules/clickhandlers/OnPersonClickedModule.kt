package com.example.tmdbpeople.dagger.modules.clickhandlers

import com.example.tmdbpeople.views.adapters.PersonAdapter
import dagger.Module
import dagger.Provides

@Module
class OnPersonClickedModule(var onPersonClicked: PersonAdapter.OnPersonClicked) {

    @Provides
    fun provideOnPersonClicked(): PersonAdapter.OnPersonClicked {
        return onPersonClicked
    }

}