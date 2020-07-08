package com.example.tmdbpeople.datasource.searchdatasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.datasource.PersonDataSource
import com.example.tmdbpeople.models.PersonModel

class PersonSearchDataSourceFactory (var query : String?) :
    DataSource.Factory<Int?, PersonModel?>() {
    var personDataSource : PersonSearchDataSource? = null
    val itemLiveDataSource =
        MutableLiveData<PageKeyedDataSource<Int?, PersonModel?>>()

    override fun create(): DataSource<Int?, PersonModel?> {
        personDataSource = PersonSearchDataSource(query)
        itemLiveDataSource.postValue(personDataSource)
        return personDataSource as PersonSearchDataSource
    }


    fun invalidate() {
        personDataSource?.invalidate()
    }



}