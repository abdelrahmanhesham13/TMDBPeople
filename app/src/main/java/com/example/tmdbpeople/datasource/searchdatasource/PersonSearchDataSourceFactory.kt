package com.example.tmdbpeople.datasource.searchdatasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.networkutils.LoadCallback

//DataSource Factory used to create Person DataSource object and post it to LiveData
class PersonSearchDataSourceFactory (var loadCallback: LoadCallback ,var query : String?) :
    DataSource.Factory<Int?, PersonDetailsResponse?>() {
    var personDataSource : PersonSearchDataSource? = null
    val itemLiveDataSource =
        MutableLiveData<PageKeyedDataSource<Int?, PersonDetailsResponse?>>()

    override fun create(): DataSource<Int?, PersonDetailsResponse?> {
        personDataSource = PersonSearchDataSource(loadCallback, query)
        itemLiveDataSource.postValue(personDataSource)
        return personDataSource as PersonSearchDataSource
    }

    //Function to refresh data
    fun invalidate() {
        personDataSource?.invalidate()
    }



}