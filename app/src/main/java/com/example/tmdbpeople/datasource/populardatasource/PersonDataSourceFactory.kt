package com.example.tmdbpeople.datasource.populardatasource

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.tmdbpeople.models.PersonModel
import io.reactivex.disposables.CompositeDisposable

//DataSource Factory used to create Person DataSource object and post it to LiveData
class PersonDataSourceFactory(val context: Context) : DataSource.Factory<Int?, PersonModel?>() {
    private val loadingLiveData : MutableLiveData<Int> = MutableLiveData()
    private val errorLiveData : MutableLiveData<String> = MutableLiveData()
    private var personDataSource : PersonDataSource? = null
    var compositeDisposable : CompositeDisposable?  = CompositeDisposable()
    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int?, PersonModel?>>()

    override fun create(): DataSource<Int?, PersonModel?> {
        personDataSource = PersonDataSource(context,loadingLiveData,errorLiveData,compositeDisposable)
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