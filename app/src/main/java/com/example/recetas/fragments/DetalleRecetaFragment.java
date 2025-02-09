package com.example.recetas.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recetas.R;
import com.example.recetas.models.Receta;
import com.example.recetas.viewmodels.RecetaViewModel;

public class DetalleRecetaFragment extends Fragment {

    private RecetaViewModel recetaViewModel;
    private int recetaId;

    public DetalleRecetaFragment() {
        // Required empty public constructor
    }

    public static DetalleRecetaFragment newInstance() {
        return new DetalleRecetaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate el layout para este fragmento
        return inflater.inflate(R.layout.fragment_detalle_receta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el ID de la receta pasada como argumento
        if (getArguments() != null) {
            recetaId = getArguments().getInt("recetaId", -1);
        }

        if (recetaId == -1) {
            Toast.makeText(getContext(), "Receta no encontrada", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigateUp();
            return;
        }

        TextView textViewNombre = view.findViewById(R.id.text_view_nombre_detalle);
        TextView textViewCategoria = view.findViewById(R.id.text_view_categoria_detalle);
        TextView textViewTiempo = view.findViewById(R.id.text_view_tiempo_detalle);
        TextView textViewIngredientes = view.findViewById(R.id.text_view_ingredientes_detalle);
        TextView textViewInstrucciones = view.findViewById(R.id.text_view_instrucciones_detalle);
        ImageView imageViewReceta = view.findViewById(R.id.image_view_receta_detalle);

        recetaViewModel = new ViewModelProvider(this).get(RecetaViewModel.class);
        recetaViewModel.getRecetaById(recetaId).observe(getViewLifecycleOwner(), new Observer<Receta>() {
            @Override
            public void onChanged(Receta receta) {
                if (receta != null) {
                    textViewNombre.setText(receta.getNombre());
                    textViewCategoria.setText(receta.getCategoria());
                    textViewTiempo.setText(String.valueOf(receta.getTiempo()) + " minutos");
                    textViewIngredientes.setText(receta.getIngredientes());
                    textViewInstrucciones.setText(receta.getInstrucciones());
                    // Cargar la imagen usando Glide
                    Glide.with(getContext()).load(receta.getImagen()).into(imageViewReceta);
                }
            }
        });

        // Botón para editar la receta
        Button buttonEditar = view.findViewById(R.id.button_edit_receta);
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("recetaId", recetaId);
                Navigation.findNavController(v).navigate(
                        R.id.action_detalleRecetaFragment_to_anadirRecetaFragment,
                        bundle);
            }
        });

        // Botón para eliminar la receta
        Button buttonEliminar = view.findViewById(R.id.button_delete_receta);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recetaViewModel.getRecetaById(recetaId).observe(getViewLifecycleOwner(), new Observer<Receta>() {
                    @Override
                    public void onChanged(Receta receta) {
                        if (receta != null) {
                            recetaViewModel.delete(receta);
                            Toast.makeText(getContext(), "Receta eliminada", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(v).navigateUp();
                        }
                    }
                });
            }
        });
    }
}
