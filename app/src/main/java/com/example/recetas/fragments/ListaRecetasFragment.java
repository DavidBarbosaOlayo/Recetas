package com.example.recetas.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recetas.R;
import com.example.recetas.adapters.RecetasAdapter;
import com.example.recetas.models.Receta;
import com.example.recetas.viewmodels.RecetaViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaRecetasFragment extends Fragment {

    private RecetaViewModel recetaViewModel;

    public ListaRecetasFragment() {
    }

    public static ListaRecetasFragment newInstance() {
        return new ListaRecetasFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_recetas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_recetas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        RecetasAdapter adapter = new RecetasAdapter();
        recyclerView.setAdapter(adapter);

        recetaViewModel = new ViewModelProvider(this).get(RecetaViewModel.class);
        recetaViewModel.getAllRecetas().observe(getViewLifecycleOwner(), new Observer<List<Receta>>() {
            @Override
            public void onChanged(List<Receta> recetas) {
                adapter.setRecetas(recetas);
            }
        });

        // Manejar clic en elementos
        adapter.setOnItemClickListener(new RecetasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Receta receta) {
                Bundle bundle = new Bundle();
                bundle.putInt("recetaId", receta.getId());
                Navigation.findNavController(view).navigate(R.id.action_listaRecetasFragment_to_detalleRecetaFragment, bundle);
            }
        });
    }
}

