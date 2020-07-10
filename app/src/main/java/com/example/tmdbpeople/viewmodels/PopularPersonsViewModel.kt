package com.example.tmdbpeople.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.tmdbpeople.dagger.component.datasource.DaggerDataSourceComponent
import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.dagger.modules.data.QueryModule
import com.example.tmdbpeople.datasource.populardatasource.PersonDataSourceFactory
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.utils.networkutils.Constants
import javax.inject.Inject

//PopularPersonsViewModel create DataSource Factory for Person List Pagination and create LiveData object to observe on it
class PopularPersonsViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var personDataSourceFactory : PersonDataSourceFactory
    val personPagedList: LiveData<PagedList<PersonModel?>>
    private val liveDataSource: LiveData<PageKeyedDataSource<Int?, PersonModel?>>

    init {
        DaggerDataSourceComponent.builder()
            .contextModule(ContextModule(application))
            .build()
            .inject(this)
        liveDataSource = personDataSourceFactory.itemLiveDataSource
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.PAGE_SIZE).build()
        personPagedList = LivePagedListBuilder(personDataSourceFactory, pagedListConfig).build()
    }

    fun getStateLiveData() : LiveData<Constants.State>? {
        return personDataSourceFactory.getStateLiveData()
    }

    fun getErrorLiveData() : LiveData<Int>? {
        return personDataSourceFactory.getErrorLiveData()
    }

    fun invalidate() {
        personDataSourceFactory.invalidate()
    }

    override fun onCleared() {
        super.onCleared()
        personDataSourceFactory.compositeDisposable?.dispose()
        personDataSourceFactory.compositeDisposable?.clear()
        personDataSourceFactory.compositeDisposable = null
    }
}