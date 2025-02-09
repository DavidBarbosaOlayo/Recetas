package com.example.recetas.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.recetas.R;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.recetas.models.Receta;

import java.util.ArrayList;
import java.util.List;

public class RecetasAdapter extends RecyclerView.Adapter<RecetasAdapter.RecetaViewHolder> {

    private List<Receta> recetas = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public RecetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recetas_item, parent, false);
        return new RecetaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecetaViewHolder holder, int position) {
        Receta currentReceta = recetas.get(position);
        holder.textViewNombre.setText(currentReceta.getNombre());
        holder.textViewCategoria.setText(currentReceta.getCategoria());
        Glide.with(holder.imageViewReceta.getContext()).load(currentReceta.getImagen()).into(holder.imageViewReceta);
    }

    @Override
    public int getItemCount() {
        return recetas.size();
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
        notifyDataSetChanged();
    }

    public Receta getRecetaAt(int position) {
        return recetas.get(position);
    }

    class RecetaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private TextView textViewCategoria;
        private ImageView imageViewReceta;

        public RecetaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.text_view_nombre);
            textViewCategoria = itemView.findViewById(R.id.text_view_categoria);
            imageViewReceta = itemView.findViewById(R.id.image_view_receta);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(recetas.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Receta receta);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
