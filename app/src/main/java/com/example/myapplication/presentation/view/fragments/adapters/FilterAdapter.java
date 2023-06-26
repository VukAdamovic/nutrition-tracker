package com.example.myapplication.presentation.view.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.models.api.domain.Category;
import com.example.myapplication.data.models.api.domain.Ingredient;
import com.example.myapplication.data.models.api.domain.MealSingle;
import com.example.myapplication.presentation.view.activities.MainActivity;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder>{

    private List<Category> categoryList;

    private  List<String> areaList;

    private List<Ingredient> ingredientList;

    private List<String> tagsList;

    private OnTagClickListener onTagClickListener;


    // Dodavanje objekta interfejsa u konstruktor adaptera

    //Iskoristicu ovaj adapter za 4 situacije

    //U datom trenutku samo jedna lista moze biti aktivna, ove ostale moraju biti null
    public FilterAdapter(List<Category> categoryList, List<String> areaList, List<Ingredient> ingredientList, List<String> tagsList, OnTagClickListener onTagClickListener) {
        this.categoryList = categoryList;
        this.areaList = areaList;
        this.ingredientList = ingredientList;
        this.tagsList = tagsList;
        this.onTagClickListener = onTagClickListener;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_element, parent, false);
        return  new FilterViewHolder(view, onTagClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        if(categoryList != null){
            Category category = categoryList.get(position);
            holder.bindCategory(category);
        }
        else if(areaList != null){
            String area = areaList.get(position);
            holder.bindArea(area);
        }
        else if(ingredientList != null){
            Ingredient ingredient = ingredientList.get(position);
            holder.bindIngredient(ingredient);
        }else {
            //Test samo posle cu promeniti ovde treba za tags da bude
            String tag = tagsList.get(position);
            holder.bindTag(tag);
        }

    }

    @Override
    public int getItemCount() {
        if(categoryList != null){
            return categoryList.size();
        }
        else if(areaList != null){
            return areaList.size();
        }
        else if(ingredientList != null){
            return ingredientList.size();
        }else {
            return tagsList.size();
        }
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder{

        private Category category;

        private String area;

        private Ingredient ingredient;

        private String tag;

        private OnTagClickListener onTagClickListener;


        public FilterViewHolder(@NonNull View itemView, OnTagClickListener onTagClickListener) {
            super(itemView);
            this.onTagClickListener = onTagClickListener;
            if(categoryList != null){
                itemView.setOnClickListener(v->{
                    MainActivity.mainViewModel.getMealsByCategory(category.getName());
                });
            }
            else if(areaList != null){
                itemView.setOnClickListener(v -> {
                    MainActivity.mainViewModel.getMealsByArea(area);
                });
            }
            else if(ingredientList != null){
                itemView.setOnClickListener(v->{
                    MainActivity.mainViewModel.getMealsByIngredient(ingredient.getName());
                });
            } else {
                itemView.setOnClickListener(v -> {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        tag = tagsList.get(position);
                        onTagClickListener.onTagClick(tag);
                    }
                });
            }

        }

        public void bindCategory(Category category){
            this.category = category;
            TextView name = itemView.findViewById(R.id.textView48);
            name.setText(category.getName());
        }

        public void bindArea(String area){
            this.area = area;
            TextView name = itemView.findViewById(R.id.textView48);
            name.setText(area);
        }

        public void bindIngredient(Ingredient ingredient){
            this.ingredient = ingredient;
            TextView name = itemView.findViewById(R.id.textView48);
            name.setText(ingredient.getName());
        }

        public void bindTag(String tag){
            TextView name = itemView.findViewById(R.id.textView48);
            name.setText(tag);
        }
    }

    public interface OnTagClickListener {
        void onTagClick(String tag);
    }


}
