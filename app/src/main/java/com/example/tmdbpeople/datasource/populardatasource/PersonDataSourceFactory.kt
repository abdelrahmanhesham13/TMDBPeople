package com.example.tmdbpeople.datasource.populardatasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.PersonModel

//DataSource Factory used to create Person DataSource object and post it to LiveData
class PersonDataSourceFactory : DataSource.Factory<Int?, PersonModel?>() {
    private val loadingLiveData : MutableLiveData<Int> = MutableLiveData()
    private val errorLiveData : MutableLiveData<String> = MutableLiveData()
    private var personDataSource : PersonDataSource? = null
    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int?, PersonModel?>>()

    override fun create(): DataSource<Int?, PersonModel?> {
        personDataSource = PersonDataSource(loadingLiveData,errorLiveData)
        itemLiveDataSource.postValue(personDataSource)
        return personDataSource as PersonDataSource
    }

    fun getStateLiveData() : LiveData<Int>? {
        return loadingLiveData
    }

    fun getErrorLiveData() : LiveData<String>? {
        return errorLiveData
    }

    //Function to refresh data
    fun invalidate() {
        personDataSource?.invalidate()
    }

}