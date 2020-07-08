package com.example.tmdbpeople.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.tmdbpeople.datasource.PersonDataSource;
import com.example.tmdbpeople.datasource.PersonDataSourceFactory;
import com.example.tmdbpeople.datasource.searchdatasource.PersonSearchDataSource;
import com.example.tmdbpeople.datasource.searchdatasource.PersonSearchDataSourceFactory;
import com.example.tmdbpeople.models.PersonModel;
import com.example.tmdbpeople.networkutils.Constants;

public class SearchPersonsViewModel extends ViewModel {

    private LiveData<PagedList<PersonModel>> personPagedList = new MutableLiveData<>();
    private LiveData<PageKeyedDataSource<Integer, PersonModel>> liveDataSource = new MutableLiveData<>();
    PersonSearchDataSourceFactory personDataSource;


    public SearchPersonsViewModel() {
        personDataSource = new PersonSearchDataSourceFactory(null);

        liveDataSource = personDataSource.getItemLiveDataSource();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setPageSize(PersonDataSource.PAGE_SIZE)
                        .setEnablePlaceholders(false).build();

        personPagedList = (new LivePagedListBuilder(personDataSource, pagedListConfig))
                .build();
    }

    public void doSearch(String query) {
        if (!query.isEmpty()) {
            personDataSource.setQuery(query);
            personDataSource.invalidate();
        }
    }

    public LiveData<PagedList<PersonModel>> getPersonPagedList() {
        return personPagedList;
    }
}
