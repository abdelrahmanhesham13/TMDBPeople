package com.example.tmdbpeople.dagger.component

import com.example.tmdbpeople.dagger.modules.*
import com.example.tmdbpeople.views.activities.PersonDetailsActivity
import com.example.tmdbpeople.views.adapters.PersonAdapter
import dagger.Component

@Component(modules = [ContextModule::class, OnItemClickedImageModule::class , PersonImageArrayListModule::class , PersonDetailsModule::class])
interface PersonDetailsAdapterComponent {

    fun inject(personDetailsActivity: PersonDetailsActivity)

}