package com.example.recetas.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RecetaViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;

    public RecetaViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RecetaViewModel.class)) {
            return (T) new RecetaViewModel(mApplication);
        }
        throw new IllegalArgumentException("Clase ViewModel no reconocida");
    }
}

