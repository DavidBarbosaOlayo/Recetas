package com.example.recetas.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recetas")
public class Receta {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;
    private String ingredientes;
    private String instrucciones;
    private int tiempo; // Tiempo en minutos
    private String categoria;
    private String imagen; // Ruta local de la imagen

    public Receta(String nombre, String ingredientes, String instrucciones, int tiempo, String categoria, String imagen) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
        this.tiempo = tiempo;
        this.categoria = categoria;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes; }

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) { this.instrucciones = instrucciones; }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) { this.tiempo = tiempo; }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) { this.imagen = imagen; }
}

