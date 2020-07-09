package com.example.tmdbpeople.dagger.component.adapters

import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.dagger.modules.clickhandlers.OnPersonClickedModule
import com.example.tmdbpeople.views.activities.PopularPersonsActivity
import com.example.tmdbpeople.views.activities.SearchPersonsActivity
import dagger.Component

@Component(modules = [ContextModule::class, OnPersonClickedModule::class])
interface PersonAdapterComponent {

    fun inject(popularPersonsActivity: PopularPersonsActivity)
    fun inject(searchPersonsActivity: SearchPersonsActivity)

}