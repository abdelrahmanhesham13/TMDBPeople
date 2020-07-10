package com.example.tmdbpeople.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.tmdbpeople.dagger.component.datasource.DaggerDataSourceComponent
import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.dagger.modules.data.QueryModule

import com.example.tmdbpeople.datasource.searchdatasource.PersonSearchDataSourceFactory
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.utils.networkutils.Constants
import javax.inject.Inject

class SearchPersonsViewModel(application: Application) : AndroidViewModel(application) {

    var personPagedList: LiveData<PagedList<PersonModel?>> = MutableLiveData()
    private var liveDataSource = MutableLiveData<PageKeyedDataSource<Int?, PersonModel?>>()
    @Inject
    lateinit var personDataSource: PersonSearchDataSourceFactory

    init {
        DaggerDataSourceComponent.builder()
            .contextModule(ContextModule(application))
            .queryModule(QueryModule(Constants.EMPTY_STRING))
            .build()
            .inject(this)
        liveDataSource = personDataSource.itemLiveDataSource
        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(Constants.PAGE_SIZE)
            .setEnablePlaceholders(false).build()
        personPagedList = LivePagedListBuilder(personDataSource, pagedListConfig)
            .build()
    }

    fun getStateLiveData() : LiveData<Constants.State>? {
        return personDataSource.getStateLiveData()
    }

    fun getErrorLiveData() : LiveData<Int>? {
        return personDataSource.getErrorLiveData()
    }

    override fun onCleared() {
        super.onCleared()
        personDataSource.compositeDisposable?.dispose()
        personDataSource.compositeDisposable?.clear()
        personDataSource.compositeDisposable = null
    }

    fun doSearch(query: String) {
        if (query.isNotEmpty()) {
            personDataSource.query = query
            personDataSource.invalidate()
        }
    }
}
