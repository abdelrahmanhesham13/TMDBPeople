package com.example.tmdbpeople.datasource.populardatasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.networkutils.LoadCallback

//DataSource Factory used to create Person DataSource object and post it to LiveData
class PersonDataSourceFactory(private val loadCallback: LoadCallback, private val query : String?) :
    DataSource.Factory<Int?, PersonDetailsResponse?>() {
    var personDataSource : PersonDataSource? = null
    val itemLiveDataSource =
        MutableLiveData<PageKeyedDataSource<Int?, PersonDetailsResponse?>>()

    override fun create(): DataSource<Int?, PersonDetailsResponse?> {
        personDataSource =
            PersonDataSource(
                loadCallback
            )
        itemLiveDataSource.postValue(personDataSource)
        return personDataSource as PersonDataSource
    }

    //Function to refresh data
    fun invalidate() {
        personDataSource?.invalidate()
    }

}