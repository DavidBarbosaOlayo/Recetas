package com.example.recetas.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recetas.R;
import com.example.recetas.models.Receta;
import com.example.recetas.viewmodels.RecetaViewModel;

import java.util.Objects;

public class AñadirRecetaFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 100;

    private RecetaViewModel recetaViewModel;
    private EditText editTextNombre, editTextCategoria, editTextTiempo, editTextIngredientes, editTextInstrucciones;
    private ImageView imageViewReceta;
    private Uri imageUri = null;
    private int recetaId = -1;

    public AñadirRecetaFragment() {
        // Required empty public constructor
    }

    public static AñadirRecetaFragment newInstance() {
        return new AñadirRecetaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate el layout para este fragmento
        return inflater.inflate(R.layout.fragment_anadir_receta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        editTextNombre = view.findViewById(R.id.edit_text_nombre);
        editTextCategoria = view.findViewById(R.id.edit_text_categoria);
        editTextTiempo = view.findViewById(R.id.edit_text_tiempo);
        editTextIngredientes = view.findViewById(R.id.edit_text_ingredientes);
        editTextInstrucciones = view.findViewById(R.id.edit_text_instrucciones);
        imageViewReceta = view.findViewById(R.id.image_view_receta_anadir);

        Button buttonGuardar = view.findViewById(R.id.button_guardar_receta);
        Button buttonSeleccionarImagen = view.findViewById(R.id.button_seleccionar_imagen);

        recetaViewModel = new ViewModelProvider(this).get(RecetaViewModel.class);

        // Manejar edición de receta si se pasa un ID
        if (getArguments() != null) {
            recetaId = getArguments().getInt("recetaId", -1);
            if (recetaId != -1) {
                recetaViewModel.getRecetaById(recetaId).observe(getViewLifecycleOwner(), new Observer<Receta>() {
                    @Override
                    public void onChanged(Receta receta) {

                        if (receta != null) {
                            editTextNombre.setText(receta.getNombre());
                            editTextCategoria.setText(receta.getCategoria());
                            editTextTiempo.setText(String.valueOf(receta.getTiempo()));
                            editTextIngredientes.setText(receta.getIngredientes());
                            editTextInstrucciones.setText(receta.getInstrucciones());
                            // Cargar la imagen usando Glide
                            Glide.with(getContext())
                                    .load(receta.getImagen())
                                    .placeholder(R.drawable.ic_placeholder)
                                    .error(R.drawable.ic_error)
                                    .into(imageViewReceta);
                            String imagenReceta = receta.getImagen();
                            if (imagenReceta != null && !imagenReceta.isEmpty()) {
                                imageUri = Uri.parse(imagenReceta);
                                Glide.with(getContext())
                                        .load(imageUri)
                                        .placeholder(R.drawable.ic_placeholder)
                                        .error(R.drawable.ic_error)
                                        .into(imageViewReceta);
                            } else {
                                // No tenemos imagen válida, usar un placeholder
                                imageUri = null;
                                Glide.with(getContext())
                                        .load(R.drawable.ic_placeholder)
                                        .error(R.drawable.ic_error)
                                        .into(imageViewReceta);
                            }

                        }
                    }
                });
            }
        }

        buttonSeleccionarImagen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // Android 13 en adelante, usa permiso específico para seleccionar imágenes
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION);
                    } else {
                        abrirGaleria();
                    }
                } else {
                    // Versiones anteriores a Android 13
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                    } else {
                        abrirGaleria();
                    }
                }
            }
        });


        buttonGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                guardarReceta();
            }
        });
    }

    private void abrirGaleria(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar Imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null){
            imageUri = data.getData();
            imageViewReceta.setImageURI(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                abrirGaleria();
            } else {
                Toast.makeText(getContext(), "Permiso denegado para acceder a la galería", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void guardarReceta(){
        String nombre = Objects.requireNonNull(editTextNombre.getText()).toString().trim();
        String categoria = Objects.requireNonNull(editTextCategoria.getText()).toString().trim();
        String tiempoStr = Objects.requireNonNull(editTextTiempo.getText()).toString().trim();
        String ingredientes = Objects.requireNonNull(editTextIngredientes.getText()).toString().trim();
        String instrucciones = Objects.requireNonNull(editTextInstrucciones.getText()).toString().trim();
        String imagen = imageUri != null ? imageUri.toString() : null;

        if (nombre.isEmpty() || categoria.isEmpty() || tiempoStr.isEmpty() || ingredientes.isEmpty() || instrucciones.isEmpty()){
            Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int tiempo;
        try {
            tiempo = Integer.parseInt(tiempoStr);
        } catch (NumberFormatException e){
            Toast.makeText(getContext(), "Tiempo debe ser un número", Toast.LENGTH_SHORT).show();
            return;
        }

        if (recetaId == -1){
            // Añadir nueva receta
            Receta nuevaReceta = new Receta(nombre, ingredientes, instrucciones, tiempo, categoria, imagen);
            recetaViewModel.insert(nuevaReceta);
            Toast.makeText(getContext(), "Receta añadida", Toast.LENGTH_SHORT).show();
        } else {
            // Actualizar receta existente
            Receta recetaActualizada = new Receta(nombre, ingredientes, instrucciones, tiempo, categoria, imagen);
            recetaActualizada.setId(recetaId);
            recetaViewModel.update(recetaActualizada);
            Toast.makeText(getContext(), "Receta actualizada", Toast.LENGTH_SHORT).show();
        }

        // Navegar de vuelta a la lista de recetas
        Navigation.findNavController(getView()).navigateUp();
    }
}

