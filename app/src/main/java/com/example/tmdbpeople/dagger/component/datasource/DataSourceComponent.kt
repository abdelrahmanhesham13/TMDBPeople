package com.example.tmdbpeople.dagger.component.datasource

import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.dagger.modules.data.QueryModule
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel
import com.example.tmdbpeople.viewmodels.SearchPersonsViewModel
import dagger.Component

@Component(modules = [ContextModule::class , QueryModule::class])
interface DataSourceComponent {

    fun inject(popularPersonsViewModel: PopularPersonsViewModel)
    fun inject(searchPersonsViewModel: SearchPersonsViewModel)

}