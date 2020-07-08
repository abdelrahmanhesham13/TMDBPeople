package com.example.tmdbpeople.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.networkutils.LoadCallback

class PersonDataSourceFactory(private val loadCallback: LoadCallback, private val query : String?) :
    DataSource.Factory<Int?, PersonModel?>() {
    var personDataSource : PersonDataSource? = null
    val itemLiveDataSource =
        MutableLiveData<PageKeyedDataSource<Int?, PersonModel?>>()

    override fun create(): DataSource<Int?, PersonModel?> {
        personDataSource = PersonDataSource(loadCallback)
        itemLiveDataSource.postValue(personDataSource)
        return personDataSource as PersonDataSource
    }


    fun invalidate() {
        personDataSource?.invalidate()
    }

}