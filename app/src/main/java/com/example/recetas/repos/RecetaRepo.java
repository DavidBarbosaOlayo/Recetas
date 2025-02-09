package com.example.recetas.repos;


import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.recetas.data.AppDatabase;
import com.example.recetas.data.RecetaDAO;
import com.example.recetas.models.Receta;

import java.util.List;

public class RecetaRepo {

    private RecetaDAO recetaDao;
    private LiveData<List<Receta>> allRecetas;

    public RecetaRepo(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        recetaDao = db.recetaDao();
        allRecetas = recetaDao.getAllRecetas();
    }

    public LiveData<List<Receta>> getAllRecetas() {
        return allRecetas;
    }

    public LiveData<Receta> getRecetaById(int id) {
        return recetaDao.getRecetaById(id);
    }

    public void insert(Receta receta) {
        new InsertAsyncTask(recetaDao).execute(receta);
    }

    public void update(Receta receta) {
        new UpdateAsyncTask(recetaDao).execute(receta);
    }

    public void delete(Receta receta) {
        new DeleteAsyncTask(recetaDao).execute(receta);
    }

    // AsyncTask para operaciones de inserción
    private static class InsertAsyncTask extends AsyncTask<Receta, Void, Void> {
        private RecetaDAO asyncTaskDao;

        InsertAsyncTask(RecetaDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Receta... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    // AsyncTask para operaciones de actualización
    private static class UpdateAsyncTask extends AsyncTask<Receta, Void, Void> {
        private RecetaDAO asyncTaskDao;

        UpdateAsyncTask(RecetaDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Receta... params) {
            asyncTaskDao.update(params[0]);
            return null;
        }
    }

    // AsyncTask para operaciones de eliminación
    private static class DeleteAsyncTask extends AsyncTask<Receta, Void, Void> {
        private RecetaDAO asyncTaskDao;

        DeleteAsyncTask(RecetaDAO dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Receta... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
