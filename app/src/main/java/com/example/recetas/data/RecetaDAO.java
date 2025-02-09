package com.example.recetas.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recetas.models.Receta;

import java.util.List;

@Dao
public interface RecetaDAO {

    @Insert
    void insert(Receta receta);

    @Update
    void update(Receta receta);

    @Delete
    void delete(Receta receta);

    @Query("SELECT * FROM recetas ORDER BY nombre ASC")
    LiveData<List<Receta>> getAllRecetas();

    @Query("SELECT * FROM recetas WHERE id = :id")
    LiveData<Receta> getRecetaById(int id);
}

