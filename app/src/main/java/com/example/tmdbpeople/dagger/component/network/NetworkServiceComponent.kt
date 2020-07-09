package com.example.tmdbpeople.dagger.component.network

import com.example.tmdbpeople.dagger.modules.network.NetworkServiceModule
import com.example.tmdbpeople.datasource.populardatasource.PersonDataSource
import com.example.tmdbpeople.datasource.searchdatasource.PersonSearchDataSource
import com.example.tmdbpeople.utils.networkutils.PersonsService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkServiceModule::class])
interface NetworkServiceComponent {
    fun inject(personDataSource: PersonDataSource)
    fun inject(personSearchDataSource: PersonSearchDataSource)
    fun getService() : PersonsService
}