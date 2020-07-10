package com.example.tmdbpeople.dagger.modules.data

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class QueryModule() {

    private lateinit var query : String

    constructor(query : String) : this() {
        this.query = query
    }

    @Provides
    fun provideQuery(): String {
        return query
    }

}