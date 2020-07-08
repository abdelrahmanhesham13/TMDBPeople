package com.example.tmdbpeople.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.PersonModel

class PersonDataSourceFactory :
    DataSource.Factory<Any?, Any?>() {
    val itemLiveDataSource =
        MutableLiveData<PageKeyedDataSource<Int, PersonModel>>()

    override fun create(): DataSource<Any?, Any?> {
        val personDataSource = PersonDataSource()
        itemLiveDataSource.postValue(personDataSource)
        return personDataSource
    }

}