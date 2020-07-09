package com.example.tmdbpeople.dagger.component.adapters

import com.example.tmdbpeople.dagger.modules.*
import com.example.tmdbpeople.dagger.modules.clickhandlers.OnImageClickedModule
import com.example.tmdbpeople.dagger.modules.data.PersonImageArrayListModule
import com.example.tmdbpeople.views.activities.PersonDetailsActivity
import dagger.Component

@Component(modules = [ContextModule::class, OnImageClickedModule::class , PersonImageArrayListModule::class])
interface PersonDetailsAdapterComponent {
    fun inject(personDetailsActivity: PersonDetailsActivity)
}