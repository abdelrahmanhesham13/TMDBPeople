package com.example.tmdbpeople.dagger.component

import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.dagger.modules.OnItemClickPersonModule
import com.example.tmdbpeople.views.adapters.PersonAdapter
import dagger.Component

@Component(modules = [ContextModule::class, OnItemClickPersonModule::class])
interface PersonAdapterComponent {

    fun getPersonAdapter() : PersonAdapter

}