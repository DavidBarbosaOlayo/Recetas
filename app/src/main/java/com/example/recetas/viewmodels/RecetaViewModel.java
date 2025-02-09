package com.example.recetas.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.recetas.models.Receta;
import com.example.recetas.repos.RecetaRepo;

import java.util.List;

public class RecetaViewModel extends AndroidViewModel {

    private RecetaRepo repository;
    private LiveData<List<Receta>> allRecetas;

    public RecetaViewModel(@NonNull Application application) {
        super(application);
        repository = new RecetaRepo(application);
        allRecetas = repository.getAllRecetas();
    }

    public LiveData<List<Receta>> getAllRecetas() {
        return allRecetas;
    }

    public LiveData<Receta> getRecetaById(int id) {
        return repository.getRecetaById(id);
    }

    public void insert(Receta receta) {
        repository.insert(receta);
    }

    public void update(Receta receta) {
        repository.update(receta);
    }

    public void delete(Receta receta) {
        repository.delete(receta);
    }
}

