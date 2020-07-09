package com.example.tmdbpeople.datasource.searchdatasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.PersonModel
import io.reactivex.disposables.CompositeDisposable

//DataSource Factory used to create Person DataSource object and post it to LiveData
class PersonSearchDataSourceFactory (val context: Context,var query : String) : DataSource.Factory<Int?, PersonModel?>() {

    private val loadingLiveData : MutableLiveData<Int> = MutableLiveData()
    private val errorLiveData : MutableLiveData<Int> = MutableLiveData()
    var personDataSource : PersonSearchDataSource? = null
    var compositeDisposable : CompositeDisposable? = CompositeDisposable()
    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int?, PersonModel?>>()

    override fun create(): DataSource<Int?, PersonModel?> {
        personDataSource = PersonSearchDataSource(context,query, loadingLiveData,errorLiveData,compositeDisposable)
        itemLiveDataSource.postValue(personDataSource)
        return personDataSource as PersonSearchDataSource
    }

    fun getStateLiveData() : LiveData<Int>? {
        return loadingLiveData
    }

    fun getErrorLiveData() : LiveData<Int>? {
        return errorLiveData
    }

    //Function to refresh data
    fun invalidate() {
        personDataSource?.invalidate()
    }



}