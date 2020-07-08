package com.example.tmdbpeople.viewmodels.viewmodelfactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmdbpeople.networkutils.LoadCallback;
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel;

public class CustomViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    LoadCallback loadCallback;

    public CustomViewModelFactory(LoadCallback loadCallback) {
        this.loadCallback = loadCallback;
    }

    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new PopularPersonsViewModel(loadCallback);
    }
}
