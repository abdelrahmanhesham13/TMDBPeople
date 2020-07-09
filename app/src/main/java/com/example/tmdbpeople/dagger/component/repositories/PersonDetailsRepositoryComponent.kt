package com.example.tmdbpeople.dagger.component.repositories

import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.viewmodels.PersonDetailsViewModel
import dagger.Component

@Component(modules = [ContextModule::class])
interface PersonDetailsRepositoryComponent {
    fun inject(personDetailsViewModel: PersonDetailsViewModel)
}